package fr.warmadon.dev.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.commands.DJCommand;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class PauseCmd extends DJCommand 
{
    public PauseCmd(Bot bot)
    {
        super(bot);
        this.name = "pause";
        this.help = "Mettre en pause";
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) 
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        if(handler.getPlayer().isPaused())
        {
            event.replyWarning("Le Bot est en pause Café! Utilise `"+event.getClient().getPrefix()+"play` pour le faire revenir!");
            return;
        }
        handler.getPlayer().setPaused(true);
        event.replySuccess("Le Bot est en pause Café! **"+handler.getPlayer().getPlayingTrack().getInfo().title+"**. Tape `"+event.getClient().getPrefix()+"play` pour le faire revenir!");
    }
}
