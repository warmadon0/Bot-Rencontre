package fr.warmadon.dev.commands.general;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.settings.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SettingsCmd extends Command 
{
    private final static String EMOJI = ":musical_score:"; // 🎧
    
    public SettingsCmd()
    {
        this.name = "settings";
        this.help = "montre les paramètres du bot";
        this.aliases = new String[]{"status"};
        this.guildOnly = true;
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        MessageBuilder builder = new MessageBuilder()
                .append(EMOJI + " **")
                .append("** configuration musical:");
        TextChannel tchan = s.getTextChannel(event.getGuild());
        VoiceChannel vchan = s.getVoiceChannel(event.getGuild());
        Role role = s.getRole(event.getGuild());
        EmbedBuilder ebuilder = new EmbedBuilder()
                .setColor(event.getSelfMember().getColor())
                .setDescription("Channel écrit: "+(tchan==null ? "Tous" : "**#"+tchan.getName()+"**")
                        + "\nChannel vocal: "+(vchan==null ? "Tous" : "**"+vchan.getName()+"**")
                        + "\nRôle DJ: "+(role==null ? "Aucun" : "**"+role.getName()+"**")
                        + "\nMode répétition: **"+(s.getRepeatMode() ? "On" : "Off")+"**"
                        + "\nPlaylist par défaut: "+(s.getDefaultPlaylist()==null ? "Aucune" : "**"+s.getDefaultPlaylist()+"**")
                        )
                .setFooter(event.getJDA().getGuilds().size()+" Serveurs | "
                        +event.getJDA().getGuilds().stream().filter(g -> g.getSelfMember().getVoiceState().inVoiceChannel()).count()
                        +" audio connections", null);
        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
    }
    
}
