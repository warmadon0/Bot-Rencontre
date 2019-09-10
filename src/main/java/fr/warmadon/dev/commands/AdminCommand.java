package fr.warmadon.dev.commands;

import com.jagrosh.jdautilities.command.Command;
import net.dv8tion.jda.core.Permission;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public abstract class AdminCommand extends Command
{
    public AdminCommand()
    {
        this.category = new Category("Admin", event -> 
        {
            if(event.getAuthor().getId().equals(event.getClient().getOwnerId()))
                return true;
            if(event.getGuild()==null)
                return true;
            return event.getMember().hasPermission(Permission.MANAGE_SERVER);
        });
        this.guildOnly = true;
    }
}
