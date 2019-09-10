package fr.warmadon.dev.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.commands.DJCommand;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class StopCmd extends DJCommand 
{
    public StopCmd(Bot bot)
    {
        super(bot);
        this.name = "stop";
        this.help = "stop le son";
        this.bePlaying = false;
    }

    @Override
    public void doCommand(CommandEvent event) 
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        handler.stopAndClear();
        event.getGuild().getAudioManager().closeAudioConnection();
        event.reply(event.getClient().getSuccess()+" arrête la chanson en cours et efface la file d'attente.");
    }
}
