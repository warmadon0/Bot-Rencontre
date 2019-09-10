package fr.warmadon.dev.commands;

import java.time.temporal.ChronoUnit;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;

/**
*
* @author Paul Petitnicolas <warmalol@gmail.com>
*/
@CommandInfo(
    name = {"Ping", "Pong"},
    description = "Checks the bot's latency"
)
public class PingCommand extends Command {

    public PingCommand()
    {
        this.name = "ping";
        this.help = "Vérifier la Latence Du Bot";
        this.guildOnly = false;
        this.aliases = new String[]{"pong"};
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Ping: ...", m -> {
            long ping = event.getMessage().getCreationTime().until(m.getCreationTime(), ChronoUnit.MILLIS);
            m.editMessage("Ping: " + ping  + "ms | Côté Site: " + event.getJDA().getPing() + "ms").queue();
        });
    }

}

