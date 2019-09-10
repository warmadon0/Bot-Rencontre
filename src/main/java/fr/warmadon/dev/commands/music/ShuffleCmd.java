package fr.warmadon.dev.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.commands.MusicCommand;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class ShuffleCmd extends MusicCommand 
{
    public ShuffleCmd(Bot bot)
    {
        super(bot);
        this.name = "shuffle";
        this.help = "mélange les chansons que vous avez ajoutées";
        this.beListening = true;
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) 
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        int s = handler.getQueue().shuffle(event.getAuthor().getIdLong());
        switch (s) 
        {
            case 0:
                event.replyError("Vous n'avez pas de musique dans la file d'attente à mélanger!");
                break;
            case 1:
                event.replyWarning("Vous n'avez qu'une chanson dans la file d'attente!");
                break;
            default:
                event.replySuccess("Vous avez réussi à mélanger votre "+s+" liste.");
                break;
        }
    }
    
}
