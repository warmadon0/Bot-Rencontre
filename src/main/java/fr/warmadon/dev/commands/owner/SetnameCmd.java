package fr.warmadon.dev.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.commands.OwnerCommand;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SetnameCmd extends OwnerCommand
{
    public SetnameCmd()
    {
        this.name = "setname";
        this.help = "changer le nom du bot";
        this.arguments = "<name>";
        this.guildOnly = false;
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        try 
        {
            String oldname = event.getSelfUser().getName();
            event.getSelfUser().getManager().setName(event.getArgs()).complete(false);
            event.reply(event.getClient().getSuccess()+" Nom changer de `"+oldname+"` pour `"+event.getArgs()+"`");
        } 
        catch(RateLimitedException e) 
        {
            event.reply(event.getClient().getError()+" Le Nom Peux être changer que deux fois par heure!");
        }
        catch(Exception e) 
        {
            event.reply(event.getClient().getError()+" Ce nom N'est pas valide!");
        }
    }
}
