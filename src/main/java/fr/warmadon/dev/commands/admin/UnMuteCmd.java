package fr.warmadon.dev.commands.admin;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import fr.warmadon.dev.commands.AdminCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class UnMuteCmd extends AdminCommand{

	
    public UnMuteCmd()
    {
        this.name = "unmute";
        this.help = "unmute La personne mentionner.";
        this.arguments = "<user>";
    }
	
	@Override
	protected void execute(CommandEvent event) {
		MessageBuilder builder = new MessageBuilder();
		MessageBuilder builders = new MessageBuilder();
		Emote warn = event.getGuild().getEmotesByName("warn", true).get(0);
		Emote unmuted = event.getGuild().getEmotesByName("unmuted", true).get(0);
		Config config = ConfigFactory.load();
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        event.getMessage().delete().complete();
    	if(event.getArgs().isEmpty() && event.getMessage().getAttachments().isEmpty())
        {
	        MessageBuilder builder1 = new MessageBuilder()
	                .append(" **")
	                .append(warn.getAsMention() + " • " + event.getAuthor().getAsMention() +  " vous n'avez pas rentrer les informations demandé pour exécuté cette commande")
	                .append("**");
	        event.getChannel().sendMessage(builder1.build()).queue();
        }else {
        	List<User> userName = event.getMessage().getMentionedUsers();
	        String user = userName.get(0).getId();
	        String name = userName.get(0).getName();
	      
            TextChannel ltc = event.getGuild().getTextChannelById(config.getString("logschannel"));

            
            
            EmbedBuilder ebuilder = new EmbedBuilder()
            		.setTitle(" Unmute d'un membre ", event.getGuild().getIconUrl())
            		.addField("Commande : Unmute","", false )
            		.addField("Membre : " + name, "", false)
            		.addField("Executé par : " + event.getAuthor().getName(), "", false )
                    .setColor(Color.GREEN)
                    .setFooter("Requête Faite à " + formatter.format(date) + " le " + formatter2.format(date), event.getGuild().getIconUrl());

            
        	ltc.sendMessage(ebuilder.build()).complete();
	        
	        if(!event.getGuild().getRolesByName("¤ 🔇 | Muet", true).isEmpty()) {
	        
	        	
	        	EmbedBuilder ebuilders = new EmbedBuilder()
	            		.setTitle(" Unmute d'un serveur ", event.getGuild().getIconUrl())
	            		.addField("Vous avez été unmute sur le serveur : " + event.getGuild().getName() + 
		            		"\n Je vous invite à relire le règlement pour ne pas refaire une infraction"
		            		+ "\n Sur ce bon blabla dans notre serveur discord !"
		            		, "Auteur :"+event.getAuthor().getAsTag() +
		    	            		"\n Date officielle de la sanction" + " : " + formatter.format(date) + " le " + formatter2.format(date) + " .", false)
	        	        .setColor(Color.GREEN)
	        			.setFooter("Requête faite à" + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
	        	        
		        
		        event.getGuild().getMemberById(user).getUser().openPrivateChannel().queue((channel) ->
		        {
		        	

		        	channel.sendMessage(builder.setEmbed(ebuilders.build()).build()).queue();
		        	
		        	
		        });
	        }else {
	        	event.getGuild().getController().createRole().setColor(Color.DARK_GRAY).setName("¤ 🔇 | Muet").queue(success -> 
            {
            	event.reply("Rôle Mute Créer avec succés veuillez relancer votre commande.");
            	return;
            }, failure -> 
            {
            	event.reply("je ne peux pas créer le rôle Mute ! j'ai besoin de la permission : MANAGE_ROLES");
            	return;
            });
	        }
	        
	        
	        Role mute = event.getGuild().getRolesByName("¤ 🔇 | Muet", true).get(0);
	        
	        event.getGuild().getController().removeRolesFromMember(event.getGuild().getMemberById(user), mute).queue(success -> 
            {
            	event.reply(unmuted.getAsMention()  + name + " ➤ à été unmute avec succés.");
            }, failure -> 
            {
            	event.reply(name + " ne peux pas être unmute par moi ou n'existe pas.");
            });
	        
	        
        }
	}

}
