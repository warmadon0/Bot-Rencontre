package fr.warmadon.dev.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.commands.DJCommand;
import net.dv8tion.jda.core.entities.User;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class ForceskipCmd extends DJCommand 
{
    public ForceskipCmd(Bot bot)
    {
        super(bot);
        this.name = "forceskip";
        this.help = "Skip le son actuellement à l'écoute";
        this.aliases = new String[]{"modskip"};
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) 
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        User u = event.getJDA().getUserById(handler.getRequester());
        event.reply(event.getClient().getSuccess()+" Skip **"+handler.getPlayer().getPlayingTrack().getInfo().title
                +"** (requète par "+(u==null ? "Quelqu'un" : "**"+u.getName()+"**")+")");
        handler.getPlayer().stopTrack();
    }
}
