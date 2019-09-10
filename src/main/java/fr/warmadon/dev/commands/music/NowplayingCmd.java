package fr.warmadon.dev.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.commands.MusicCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class NowplayingCmd extends MusicCommand 
{
    public NowplayingCmd(Bot bot)
    {
        super(bot);
        this.name = "nowplaying";
        this.help = "voir le son en cours de lecture";
        this.aliases = new String[]{"np","current"};
        this.botPermissions = new Permission[]{Permission.MESSAGE_EMBED_LINKS};
    }

    @Override
    public void doCommand(CommandEvent event) 
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        Message m = handler.getNowPlaying(event.getJDA());
        if(m==null)
        {
            event.reply(handler.getNoMusicPlaying(event.getJDA()));
            bot.getNowplayingHandler().clearLastNPMessage(event.getGuild());
        }
        else
        {
            event.reply(m, msg -> bot.getNowplayingHandler().setLastNPMessage(msg));
        }
    }
}
