package fr.warmadon.dev.commands;

import fr.warmadon.dev.Bot;
import fr.warmadon.dev.settings.Settings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public abstract class DJCommand extends MusicCommand
{
    public DJCommand(Bot bot)
    {
        super(bot);
        this.category = new Category("DJ", event -> 
        {
            if(event.getAuthor().getId().equals(event.getClient().getOwnerId()))
                return true;
            if(event.getGuild()==null)
                return true;
            if(event.getMember().hasPermission(Permission.MANAGE_SERVER))
                return true;
            Settings settings = event.getClient().getSettingsFor(event.getGuild());
            Role dj = settings.getRole(event.getGuild());
            return dj!=null && (event.getMember().getRoles().contains(dj) || dj.getIdLong()==event.getGuild().getIdLong());
        });
    }
}
