package fr.warmadon.dev.commands.music;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.audio.AudioHandler;
import fr.warmadon.dev.audio.QueuedTrack;
import fr.warmadon.dev.commands.MusicCommand;
import fr.warmadon.dev.settings.Settings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.User;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class RemoveCmd extends MusicCommand 
{
    public RemoveCmd(Bot bot)
    {
        super(bot);
        this.name = "remove";
        this.help = "supprime une chanson de la file d'attente";
        this.arguments = "<position|ALL>";
        this.aliases = new String[]{"delete"};
        this.beListening = true;
        this.bePlaying = true;
    }

    @Override
    public void doCommand(CommandEvent event) 
    {
        AudioHandler handler = (AudioHandler)event.getGuild().getAudioManager().getSendingHandler();
        if(handler.getQueue().isEmpty())
        {
            event.replyError("Il n'y a rien dans la file d'attente!");
            return;
        }
        if(event.getArgs().equalsIgnoreCase("all"))
        {
            int count = handler.getQueue().removeAll(event.getAuthor().getIdLong());
            if(count==0)
                event.replyWarning("Vous n'avez pas de chansons dans la file d'attente!");
            else
                event.replySuccess("Enlevé avec succès votre "+count+" entrées.");
            return;
        }
        int pos;
        try {
            pos = Integer.parseInt(event.getArgs());
        } catch(NumberFormatException e) {
            pos = 0;
        }
        if(pos<1 || pos>handler.getQueue().size())
        {
            event.replyError("La position doit être un entier valide compris entre 1 et "+handler.getQueue().size()+"!");
            return;
        }
        Settings settings = event.getClient().getSettingsFor(event.getGuild());
        boolean isDJ = event.getMember().hasPermission(Permission.MANAGE_SERVER);
        if(!isDJ)
            isDJ = event.getMember().getRoles().contains(settings.getRole(event.getGuild()));
        QueuedTrack qt = handler.getQueue().get(pos-1);
        if(qt.getIdentifier()==event.getAuthor().getIdLong())
        {
            handler.getQueue().remove(pos-1);
            event.replySuccess("Supprimer **"+qt.getTrack().getInfo().title+"** de la file d'attente");
        }
        else if(isDJ)
        {
            handler.getQueue().remove(pos-1);
            User u;
            try {
                u = event.getJDA().getUserById(qt.getIdentifier());
            } catch(Exception e) {
                u = null;
            }
            event.replySuccess("Supprimer **"+qt.getTrack().getInfo().title
                    +"** de la file d'attente (requète par "+(u==null ? "Quelqu'un" : "**"+u.getName()+"**")+")");
        }
        else
        {
            event.replyError("Tu ne peux pas supprimer **"+qt.getTrack().getInfo().title+"** parce que vous ne l'avez pas ajouté!");
        }
    }
}
