package fr.warmadon.dev.commands.admin;

import java.util.List;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import fr.warmadon.dev.commands.AdminCommand;
import fr.warmadon.dev.settings.Settings;
import fr.warmadon.dev.utils.FormatUtil;
import net.dv8tion.jda.core.entities.Role;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SetdjCmd extends AdminCommand
{
    public SetdjCmd()
    {
        this.name = "setdj";
        this.help = "Changer le rôle DJ";
        this.arguments = "<rolename|NONE>";
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        if(event.getArgs().isEmpty())
        {
            event.reply(event.getClient().getError()+" Veuillez inclure un nom de rôle ou NONE");
            return;
        }
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        if(event.getArgs().equalsIgnoreCase("none"))
        {
            s.setDJRole(null);
            event.reply(event.getClient().getSuccess()+" Rôle de DJ effacé; Seuls les administrateurs peuvent utiliser les commandes DJ.");
        }
        else
        {
            List<Role> list = FinderUtil.findRoles(event.getArgs(), event.getGuild());
            if(list.isEmpty())
                event.reply(event.getClient().getWarning()+" Aucun rôle trouvé correspondant \""+event.getArgs()+"\"");
            else if (list.size()>1)
                event.reply(event.getClient().getWarning()+FormatUtil.listOfRoles(list, event.getArgs()));
            else
            {
                s.setDJRole(list.get(0));
                event.reply(event.getClient().getSuccess()+" Les commandes DJ peuvent maintenant être utilisées par les utilisateurs avec le rôle **"+list.get(0).getName()+"**.");
            }
        }
    }
    
}
