package fr.warmadon.dev.commands.admin;

import java.util.List;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import fr.warmadon.dev.commands.AdminCommand;
import fr.warmadon.dev.settings.Settings;
import fr.warmadon.dev.utils.FormatUtil;
import net.dv8tion.jda.core.entities.TextChannel;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SettcCmd extends AdminCommand 
{
    public SettcCmd()
    {
        this.name = "settc";
        this.help = "définit le canal de texte pour les commandes de musique";
        this.arguments = "<channel|NONE>";
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        if(event.getArgs().isEmpty())
        {
            event.reply(event.getClient().getError()+" S'il vous plaît inclure un canal de texte ou NONE");
            return;
        }
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        if(event.getArgs().equalsIgnoreCase("none"))
        {
            s.setTextChannel(null);
            event.reply(event.getClient().getSuccess()+" Les commandes de musique peuvent maintenant être utilisées dans n'importe quel canal");
        }
        else
        {
            List<TextChannel> list = FinderUtil.findTextChannels(event.getArgs(), event.getGuild());
            if(list.isEmpty())
                event.reply(event.getClient().getWarning()+" Aucune chaîne de texte trouvée correspondant \""+event.getArgs()+"\"");
            else if (list.size()>1)
                event.reply(event.getClient().getWarning()+FormatUtil.listOfTChannels(list, event.getArgs()));
            else
            {
                s.setTextChannel(list.get(0));
                event.reply(event.getClient().getSuccess()+" Les commandes de musique ne peuvent désormais être utilisées que dans <#"+list.get(0).getId()+">");
            }
        }
    }
    
}
