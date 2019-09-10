package fr.warmadon.dev.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.commands.OwnerCommand;
import net.dv8tion.jda.core.OnlineStatus;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SetstatusCmd extends OwnerCommand
{
    public SetstatusCmd()
    {
        this.name = "setstatus";
        this.help = "changer le statut du bot";
        this.arguments = "<status>";
        this.guildOnly = false;
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        try {
            OnlineStatus status = OnlineStatus.fromKey(event.getArgs());
            if(status==OnlineStatus.UNKNOWN)
            {
                event.replyError("Veuillez inclure l'un des statuts suivants: `ONLINE`, `IDLE`, `DND`, `INVISIBLE`");
            }
            else
            {
                event.getJDA().getPresence().setStatus(status);
                event.replySuccess("Définir le statut sur `"+status.getKey().toUpperCase()+"`");
            }
        } catch(Exception e) {
            event.reply(event.getClient().getError()+" Le statut n'a pas pu être défini!");
        }
    }
}
