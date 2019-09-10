package fr.warmadon.dev.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jlyrics.Lyrics;
import com.jagrosh.jlyrics.LyricsClient;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.commands.MusicCommand;
import java.util.concurrent.ExecutionException;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class LyricsCmd extends MusicCommand
{
    private final LyricsClient client = new LyricsClient();
    
    public LyricsCmd(Bot bot)
    {
        super(bot);
        this.name = "lyrics";
        this.arguments = "[song name]";
        this.help = "affiche les paroles de la chanson en cours de lecture";
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event)
    {
        event.getChannel().sendTyping().queue();
        String title;
        if(event.getArgs().isEmpty())
            title = ((AudioHandler)event.getGuild().getAudioManager().getSendingHandler()).getPlayer().getPlayingTrack().getInfo().title;
        else
            title = event.getArgs();
        client.getLyrics(title).thenAccept(lyrics -> 
        {
            if(lyrics == null)
            {
                event.replyError("Paroles pour `" + title + "` Ne peut être trouvé!");
                return;
            }

            EmbedBuilder eb = new EmbedBuilder()
                    .setAuthor(lyrics.getAuthor())
                    .setColor(event.getSelfMember().getColor())
                    .setTitle(lyrics.getTitle(), lyrics.getURL());
            if(lyrics.getContent().length()>15000)
            {
                event.replyWarning("Ne peut être trouvé `" + title + "`trouvé mais probablement pas correct: " + lyrics.getURL());
            }
            else if(lyrics.getContent().length()>2000)
            {
                String content = lyrics.getContent().trim();
                while(content.length() > 2000)
                {
                    int index = content.lastIndexOf("\n\n", 2000);
                    if(index == -1)
                        index = content.lastIndexOf("\n", 2000);
                    if(index == -1)
                        index = content.lastIndexOf(" ", 2000);
                    if(index == -1)
                        index = 2000;
                    event.reply(eb.setDescription(content.substring(0, index).trim()).build());
                    content = content.substring(index).trim();
                    eb.setAuthor(null).setTitle(null, null);
                }
                event.reply(eb.setDescription(content).build());
            }
            else
                event.reply(eb.setDescription(lyrics.getContent()).build());
        });
    }
}
