package fr.warmadon.dev.commands.owner;

import java.io.IOException;
import java.io.InputStream;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.commands.OwnerCommand;
import fr.warmadon.dev.utils.OtherUtil;
import net.dv8tion.jda.core.entities.Icon;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SetavatarCmd extends OwnerCommand 
{
    public SetavatarCmd()
    {
        this.name = "setavatar";
        this.help = "changer l'avatar du bot";
        this.arguments = "<url>";
        this.guildOnly = false;
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        String url;
        if(event.getArgs().isEmpty())
            if(!event.getMessage().getAttachments().isEmpty() && event.getMessage().getAttachments().get(0).isImage())
                url = event.getMessage().getAttachments().get(0).getUrl();
            else
                url = null;
        else
            url = event.getArgs();
        InputStream s = OtherUtil.imageFromUrl(url);
        if(s==null)
        {
            event.reply(event.getClient().getError()+" URL invalide ou manquante");
        }
        else
        {
            try {
            event.getSelfUser().getManager().setAvatar(Icon.from(s)).queue(
                    v -> event.reply(event.getClient().getSuccess()+" Avatar modifié avec succès."), 
                    t -> event.reply(event.getClient().getError()+" Impossible de définir l'avatar."));
            } catch(IOException e) {
                event.reply(event.getClient().getError()+" Impossible de charger l'URL fournie.");
            }
        }
    }
}
