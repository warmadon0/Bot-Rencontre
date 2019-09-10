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
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

	@CommandInfo(
	    name = {"user", "user"},
	    description = "Avoir des informations sur un joueur."
	)
	public class UserCommand extends Command {

	    public UserCommand()
	    {
	        this.name = "user";
	        this.help = "Avoir des informations sur un joueur.";
	        this.guildOnly = false;
	        this.aliases = new String[]{"info"};
	    }

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
	            String finallist = null;
	            int a = 0, b = list.size();
	            
	            
	            event.getGuild().getMember(userName).getJoinDate();
	            if(list.size() != 0) {
	            while (a < b)
	            {
	            	Role add = list.get(a);
	            	if(finallist != null) {
	            		finallist = finallist + (add.getName()) + "   ๑      ";
	            	}else {
	            		finallist = (add.getName()) + "  ๑    ";
	            	}
	            	
	              System.out.println("Analyse de " +(a+1)+ " rôles.");
	              a++;
	            }}else {
	            	finallist = "Aucuns Rôles";
	            }
		        EmbedBuilder ebuilder = new EmbedBuilder()
		        		.setTitle(" Informations sur "+user+  ":", event.getGuild().getIconUrl())
		                .setColor(Color.cyan)
		                .addField("pseudo :", user, false)
		                .addField("rôles : ", finallist ,false)
		                .addField("surnom : ", userName.getAsMention(), false)
		                .addField("date d'arrivé", event.getGuild().getMember(userName).getJoinDate().format(formatterjoin)+ "", false)
	            		.addField("avatar : ", "Voici l'avatar de " + user, true)
	            		.setImage(avatar)
		                .setFooter("Requête faite à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
		        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
	        }else {
	        Settings s = event.getClient().getSettingsFor(event.getGuild());
	        TextChannel tchan = s.getTextChannel(event.getGuild());
	        List<User> userName = event.getMessage().getMentionedUsers();
	        String user = userName.get(0).getName();
	        VoiceChannel vchan = s.getVoiceChannel(event.getGuild());
	        String avatar = userName.get(0).getAvatarUrl();
	        Role role = s.getRole(event.getGuild());
	        MessageBuilder builder = new MessageBuilder();
            List<Role> list = event.getGuild().getMember(userName.get(0)).getRoles();
            String finallist = null;
            int a = 0, b = list.size();
            
            
            event.getGuild().getMember(userName.get(0)).getJoinDate();
            if(list.size() != 0) {
            while (a < b)
            {
            	Role add = list.get(a);
            	if(finallist != null) {
            		finallist = finallist + (add.getName()) + "   ๑      ";
            	}else {
            		finallist = (add.getName()) + "  ๑    ";
            	}
            	
              System.out.println("Analyse de " +(a+1)+ " rôles.");
              a++;
            }}else {
            	finallist = "Aucuns Rôles";
            }
	        EmbedBuilder ebuilder = new EmbedBuilder()
	        		.setTitle(" Informations sur "+user+  ":", event.getGuild().getIconUrl())
	                .setColor(Color.cyan)
	                .addField("pseudo :", user, false)
	                .addField("rôles : ", finallist ,false)
	                .addField("surnom : ", userName.get(0).getAsMention(), false)
	                .addField("date de rejoins", event.getGuild().getMember(userName.get(0)).getJoinDate().format(formatterjoin)+ "", false)
            		.addField("avatar : ", "Voici l'avatar de " + user, true)
            		.setImage(avatar)
	                .setFooter("Requête faite à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
	        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
	        }
	    }

	}

