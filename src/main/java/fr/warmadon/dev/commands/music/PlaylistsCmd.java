package fr.warmadon.dev.commands.music;

import java.util.List;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.commands.MusicCommand;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class PlaylistsCmd extends MusicCommand 
{
    public PlaylistsCmd(Bot bot)
    {
        super(bot);
        this.name = "playlists";
        this.help = "montre les playlists disponibles";
        this.aliases = new String[]{"pls"};
        this.guildOnly = true;
        this.beListening = false;
        this.beListening = false;
    }
    
    @Override
    public void doCommand(CommandEvent event) 
    {
        if(!bot.getPlaylistLoader().folderExists())
            bot.getPlaylistLoader().createFolder();
        if(!bot.getPlaylistLoader().folderExists())
        {
            event.reply(event.getClient().getWarning()+" Le dossier Playlists n'existe pas et n'a pas pu être créé.!");
            return;
        }
        List<String> list = bot.getPlaylistLoader().getPlaylistNames();
        if(list==null)
            event.reply(event.getClient().getError()+" Échec du chargement des listes de lecture disponibles!");
        else if(list.isEmpty())
            event.reply(event.getClient().getWarning()+" Il n'y a pas de playlists dans le dossier Playlists!");
        else
        {
            StringBuilder builder = new StringBuilder(event.getClient().getSuccess()+" Playlists disponibles:\n");
            list.forEach(str -> builder.append("`").append(str).append("` "));
            builder.append("\nTape `").append(event.getClient().getTextualPrefix()).append("play playlist <name>` pour jouer une playlist");
            event.reply(builder.toString());
        }
    }
}
