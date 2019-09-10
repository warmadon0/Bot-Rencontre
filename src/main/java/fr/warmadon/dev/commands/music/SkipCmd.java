package fr.warmadon.dev.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.commands.MusicCommand;
import net.dv8tion.jda.core.entities.User;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SkipCmd extends MusicCommand 
{
    public SkipCmd(Bot bot)
    {
        super(bot);
        this.name = "skip";
        this.help = "vote pour ignorer la chanson en cours";
        this.aliases = new String[]{"voteskip", "s"};
        this.beListening = true;
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) 
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        if(event.getAuthor().getIdLong()==handler.getRequester())
        {
            event.reply(event.getClient().getSuccess()+" Skip **"+handler.getPlayer().getPlayingTrack().getInfo().title+"**");
            handler.getPlayer().stopTrack();
        }
        else
        {
            int listeners = (int)event.getSelfMember().getVoiceState().getChannel().getMembers().stream()
                    .filter(m -> !m.getUser().isBot() && !m.getVoiceState().isDeafened()).count();
            String msg;
            if(handler.getVotes().contains(event.getAuthor().getId()))
                msg = event.getClient().getWarning()+" Vous avez déjà voté pour skip cette chanson `[";
            else
            {
                msg = event.getClient().getSuccess()+" Vous avez voté pour skip la chanson `[";
                handler.getVotes().add(event.getAuthor().getId());
            }
            int skippers = (int)event.getSelfMember().getVoiceState().getChannel().getMembers().stream()
                    .filter(m -> handler.getVotes().contains(m.getUser().getId())).count();
            int required = (int)Math.ceil(listeners * .55);
            msg+= skippers+" votes, "+required+"/"+listeners+" requis]`";
            if(skippers>=required)
            {
                User u = event.getJDA().getUserById(handler.getRequester());
                msg+="\n"+event.getClient().getSuccess()+" Skip **"+handler.getPlayer().getPlayingTrack().getInfo().title
                    +"**"+(handler.getRequester()==0 ? "" : " (requète par "+(u==null ? "Quelqu'un" : "**"+u.getName()+"**")+")");
                handler.getPlayer().stopTrack();
            }
            event.reply(msg);
        }
    }
    
}
