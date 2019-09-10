package fr.warmadon.dev.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.Bot;
import fr.warmadon.dev.commands.OwnerCommand;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class ShutdownCmd extends OwnerCommand
{
    private final Bot bot;
    
    public ShutdownCmd(Bot bot)
    {
        this.bot = bot;
        this.name = "shutdown";
        this.help = "éteindre le bot";
        this.guildOnly = false;
    }
    
    @Override
    protected void execute(CommandEvent event)
    {
        event.replyWarning("Shutting down...");
        bot.shutdown();
    }
}
