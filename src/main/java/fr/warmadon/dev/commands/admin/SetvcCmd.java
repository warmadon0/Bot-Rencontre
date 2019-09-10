package fr.warmadon.dev.commands.admin;

import java.util.List;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import fr.warmadon.dev.commands.AdminCommand;
import fr.warmadon.dev.settings.Settings;
import fr.warmadon.dev.utils.FormatUtil;
import net.dv8tion.jda.core.entities.VoiceChannel;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SetvcCmd extends AdminCommand 
{
    public SetvcCmd()
    {
        this.name = "setvc";
        this.help = "définit le canal vocal pour jouer de la musique";
        this.arguments = "<channel|NONE>";
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        if(event.getArgs().isEmpty())
        {
            event.reply(event.getClient().getError()+" Veuillez inclure un canal vocal ou NONE");
            return;
        }
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        if(event.getArgs().equalsIgnoreCase("none"))
        {
            s.setVoiceChannel(null);
            event.reply(event.getClient().getSuccess()+" La musique peut maintenant être jouée sur n'importe quel canal");
        }
        else
        {
            List<VoiceChannel> list = FinderUtil.findVoiceChannels(event.getArgs(), event.getGuild());
            if(list.isEmpty())
                event.reply(event.getClient().getWarning()+" Aucun canal vocal trouvé correspondant \""+event.getArgs()+"\"");
            else if (list.size()>1)
                event.reply(event.getClient().getWarning()+FormatUtil.listOfVChannels(list, event.getArgs()));
            else
            {
                s.setVoiceChannel(list.get(0));
                event.reply(event.getClient().getSuccess()+" La musique ne peut maintenant être jouée que dans **"+list.get(0).getName()+"**");
            }
        }
    }
}
