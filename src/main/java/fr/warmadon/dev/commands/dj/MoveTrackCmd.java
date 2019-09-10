package fr.warmadon.dev.commands.dj;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.audio.QueuedTrack;
import fr.warmadon.dev.commands.DJCommand;
import fr.warmadon.dev.queue.FairQueue;

/**
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class MoveTrackCmd extends DJCommand
{

    public MoveTrackCmd(Bot bot)
    {
        super(bot);
        this.name = "movetrack";
        this.help = "déplacer une piste dans la file actuelle vers une position différente";
        this.arguments = "<depuis> <vers>";
        this.aliases = new String[]{"move"};
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event)
    {
        int from;
        int to;

        String[] parts = event.getArgs().split("\\s+", 2);
        if(parts.length < 2)
        {
            event.replyError("Veuillez inclure deux index valides.");
            return;
        }

        try
        {
            // Validate the args
            from = Integer.parseInt(parts[0]);
            to = Integer.parseInt(parts[1]);
        }
        catch (NumberFormatException e)
        {
            event.replyError("Veuillez inclure deux index valides.");
            return;
        }

        if (from == to)
        {
            event.replyError("Impossible de bouger un son à sa position Actuel.");
            return;
        }

        // Validate that from and to are available
        AudioHandler handler = (AudioHandler) event.getGuild().getAudioManager().getSendingHandler();
        FairQueue<QueuedTrack> queue = handler.getQueue();
        if (isUnavailablePosition(queue, from))
        {
            String reply = String.format("`%d` n'est pas une position valide!", from);
            event.replyError(reply);
            return;
        }
        if (isUnavailablePosition(queue, to))
        {
            String reply = String.format("`%d` n'est pas une position valide!", to);
            event.replyError(reply);
            return;
        }

        // Move the track
        QueuedTrack track = queue.moveItem(from - 1, to - 1);
        String trackTitle = track.getTrack().getInfo().title;
        String reply = String.format("Moove **%s** depuis `%d` vers `%d`.", trackTitle, from, to);
        event.replySuccess(reply);
    }

    private static boolean isUnavailablePosition(FairQueue<QueuedTrack> queue, int position)
    {
        return (position < 1 || position > queue.size());
    }
}