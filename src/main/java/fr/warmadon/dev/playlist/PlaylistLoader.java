package fr.warmadon.dev.playlist;

import fr.warmadon.dev.BotConfig;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class PlaylistLoader
{
    private final BotConfig config;
    
    public PlaylistLoader(BotConfig config)
    {
        this.config = config;
    }
    
    public List<String> getPlaylistNames()
    {
        if(folderExists())
        {
            File folder = new File(config.getPlaylistsFolder());
            return Arrays.asList(folder.listFiles((pathname) -> pathname.getName().endsWith(".txt")))
                    .stream().map(f -> f.getName().substring(0,f.getName().length()-4)).collect(Collectors.toList());
        }
        else
        {
            createFolder();
            return Collections.EMPTY_LIST;
        }
    }
    
    public void createFolder()
    {
        try
        {
            Files.createDirectory(Paths.get(config.getPlaylistsFolder()));
        } 
        catch (IOException ignore) {}
    }
    
    public boolean folderExists()
    {
        return Files.exists(Paths.get(config.getPlaylistsFolder()));
    }
    
    public void createPlaylist(String name) throws IOException
    {
        Files.createFile(Paths.get(config.getPlaylistsFolder()+File.separator+name+".txt"));
    }
    
    public void deletePlaylist(String name) throws IOException
    {
        Files.delete(Paths.get(config.getPlaylistsFolder()+File.separator+name+".txt"));
    }
    
    public void writePlaylist(String name, String text) throws IOException
    {
        Files.write(Paths.get(config.getPlaylistsFolder()+File.separator+name+".txt"), text.trim().getBytes());
    }
    
    public Playlist getPlaylist(String name)
    {
        if(!getPlaylistNames().contains(name))
            return null;
        try
        {
            if(folderExists())
            {
                boolean[] shuffle = {false};
                List<String> list = new ArrayList<>();
                Files.readAllLines(Paths.get(config.getPlaylistsFolder()+File.separator+name+".txt")).forEach(str -> 
                {
                    String s = str.trim();
                    if(s.isEmpty())
                        return;
                    if(s.startsWith("#") || s.startsWith("//"))
                    {
                        s = s.replaceAll("\\s+", "");
                        if(s.equalsIgnoreCase("#shuffle") || s.equalsIgnoreCase("//shuffle"))
                            shuffle[0]=true;
                    }
                    else
                        list.add(s);
                });
                if(shuffle[0])
                    shuffle(list);
                return new Playlist(name, list, shuffle[0]);
            }
            else
            {
                createFolder();
                return null;
            }
        }
        catch(IOException e)
        {
            return null;
        }
    }
    
    
    private static <T> void shuffle(List<T> list)
    {
        for(int first =0; first<list.size(); first++)
        {
            int second = (int)(Math.random()*list.size());
            T tmp = list.get(first);
            list.set(first, list.get(second));
            list.set(second, tmp);
        }
    }
    
    
    public class Playlist
    {
        private final String name;
        private final List<String> items;
        private final boolean shuffle;
        private final List<AudioTrack> tracks = new LinkedList<>();
        private final List<PlaylistLoadError> errors = new LinkedList<>();
        private boolean loaded = false;
        
        private Playlist(String name, List<String> items, boolean shuffle)
        {
            this.name = name;
            this.items = items;
            this.shuffle = shuffle;
        }
        
        public void loadTracks(AudioPlayerManager manager, Consumer<AudioTrack> consumer, Runnable callback)
        {
            if(!loaded)
            {
                loaded = true;
                for(int i=0; i<items.size(); i++)
                {
                    boolean last = i+1==items.size();
                    int index = i;
                    manager.loadItemOrdered(name, items.get(i), new AudioLoadResultHandler() 
                    {
                        @Override
                        public void trackLoaded(AudioTrack at) 
                        {
                            if(config.isTooLong(at))
                                errors.add(new PlaylistLoadError(index, items.get(index), "Cette piste est plus longue que le maximum autorisé"));
                            else
                            {
                                at.setUserData(0L);
                                tracks.add(at);
                                consumer.accept(at);
                            }
                            if(last && callback!=null)
                                callback.run();
                        }
                        
                        @Override
                        public void playlistLoaded(AudioPlaylist ap) 
                        {
                            if(ap.isSearchResult())
                            {
                                if(config.isTooLong(ap.getTracks().get(0)))
                                    errors.add(new PlaylistLoadError(index, items.get(index), "Cette piste est plus longue que le maximum autorisé"));
                                else
                                {
                                    ap.getTracks().get(0).setUserData(0L);
                                    tracks.add(ap.getTracks().get(0));
                                    consumer.accept(ap.getTracks().get(0));
                                }
                            }
                            else if(ap.getSelectedTrack()!=null)
                            {
                                if(config.isTooLong(ap.getSelectedTrack()))
                                    errors.add(new PlaylistLoadError(index, items.get(index), "Cette piste est plus longue que le maximum autorisé"));
                                else
                                {
                                    ap.getSelectedTrack().setUserData(0L);
                                    tracks.add(ap.getSelectedTrack());
                                    consumer.accept(ap.getSelectedTrack());
                                }
                            }
                            else
                            {
                                List<AudioTrack> loaded = new ArrayList<>(ap.getTracks());
                                if(shuffle)
                                    for(int first =0; first<loaded.size(); first++)
                                    {
                                        int second = (int)(Math.random()*loaded.size());
                                        AudioTrack tmp = loaded.get(first);
                                        loaded.set(first, loaded.get(second));
                                        loaded.set(second, tmp);
                                    }
                                loaded.removeIf(track -> config.isTooLong(track));
                                loaded.forEach(at -> at.setUserData(0L));
                                tracks.addAll(loaded);
                                loaded.forEach(at -> consumer.accept(at));
                            }
                            if(last && callback!=null)
                                callback.run();
                        }

                        @Override
                        public void noMatches() 
                        {
                            errors.add(new PlaylistLoadError(index, items.get(index), "Aucun résultat."));
                            if(last && callback!=null)
                                callback.run();
                        }

                        @Override
                        public void loadFailed(FriendlyException fe) 
                        {
                            errors.add(new PlaylistLoadError(index, items.get(index), "Impossible de charger la piste: "+fe.getLocalizedMessage()));
                            if(last && callback!=null)
                                callback.run();
                        }
                    });
                }
            }
            if(shuffle)
                shuffleTracks();
        }
        
        public void shuffleTracks()
        {
            if(tracks!=null)
                shuffle(tracks);
        }
        
        public String getName()
        {
            return name;
        }

        public List<String> getItems()
        {
            return items;
        }

        public List<AudioTrack> getTracks()
        {
            return tracks;
        }
        
        public List<PlaylistLoadError> getErrors()
        {
            return errors;
        }
    }
    
    public class PlaylistLoadError
    {
        private final int number;
        private final String item;
        private final String reason;
        
        private PlaylistLoadError(int number, String item, String reason)
        {
            this.number = number;
            this.item = item;
            this.reason = reason;
        }
        
        public int getIndex()
        {
            return number;
        }
        
        public String getItem()
        {
            return item;
        }
        
        public String getReason()
        {
            return reason;
        }
    }
}
