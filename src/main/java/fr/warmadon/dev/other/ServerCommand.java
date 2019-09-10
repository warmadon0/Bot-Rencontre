package fr.warmadon.dev.other;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;
import com.jagrosh.jdautilities.examples.doc.Author;

import fr.warmadon.dev.BotConfig;
import fr.warmadon.dev.settings.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

@CommandInfo(
    name = {"server"},
    description = "Avoir des informations sur le serveur."
)
public class ServerCommand extends Command {

    public ServerCommand()
    {
        this.name = "server";
        this.help = "Avoir des informations sur le server.";
        this.guildOnly = false;
    }
    long userCount = 0;
    long botCount = 0;
    long online = 0;
    long offline = 0;
    long dnd = 0;
    long idle = 0;
    @Override
    protected void execute(CommandEvent event) {
    	Emote warn = event.getGuild().getEmotesByName("warn", true).get(0);
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        DateTimeFormatter formatterjoin = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM);
        Date date = new Date();
    	if(event.getArgs().isEmpty() && event.getMessage().getAttachments().isEmpty())
        {
	        Settings s = event.getClient().getSettingsFor(event.getGuild());
	        TextChannel tchan = s.getTextChannel(event.getGuild());
	        User userName = event.getAuthor();
	        String user = userName.getName();
	        VoiceChannel vchan = s.getVoiceChannel(event.getGuild());
	        String avatar = userName.getAvatarUrl();
	        Role role = s.getRole(event.getGuild());
	        MessageBuilder builder = new MessageBuilder();
            List<Role> list = event.getGuild().getMember(userName).getRoles();
            

            event.getGuild().getMembers().forEach(member -> {
            	User userutiles = member.getUser();
            if (userutiles.isBot()) {
                botCount++;
            } else {
                userCount++;
            }
            switch (member.getOnlineStatus()) {
                case ONLINE:
                    online++;
                    break;
                case OFFLINE:
                case INVISIBLE:
                    offline++;
                    break;
                case IDLE:
                    idle++;
                    break;
                case DO_NOT_DISTURB:
                    dnd++;
                    break;
                default:
                	break;
            }	
            });
	        EmbedBuilder ebuilder = new EmbedBuilder()
	        		.setTitle(" Informations sur Rencontre - FR 『:two_hearts:』:", event.getGuild().getIconUrl())
	                .setColor(Color.cyan)
            		.addField("Membres: ", "• membres total - " + event.getGuild().getMembers().size(), false)
            		.addField("• membres en ligne - " + (dnd+idle+online), "", false)
            		.addBlankField(true)
            		.addField("Infos:", " • fondateur - " + event.getGuild().getOwner().getEffectiveName(), false)
            		.addField( "• date de création - " + event.getGuild().getCreationTime().format(formatterjoin), "", false)
            		.addBlankField(true)
            		.addField("Serveur:", " • Salons textuel - " + event.getGuild().getTextChannels().size(), false)
            		.addField(" • Salons vocaux - " + event.getGuild().getVoiceChannels().size(), " • Rôles - " + event.getGuild().getRoles().size(), false)
            		.setImage(event.getGuild().getIconUrl())
	                .setFooter("Requête faite à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
	        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
        }
    }

}

