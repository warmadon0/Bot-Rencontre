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
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class BanCmd extends AdminCommand
{
    private MessageBuilder ebuilder;

	public BanCmd()
    {
        this.name = "ban";
        this.help = "Ban La personne mentionner.";
        this.arguments = "<user>";
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
    	MessageBuilder builder = new MessageBuilder();
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        event.getMessage().delete().complete();
        Emote ban = event.getGuild().getEmotesByName("ban", true).get(0);
        Emote warn = event.getGuild().getEmotesByName("warn", true).get(0);
        Config config = ConfigFactory.load();
    	if(event.getArgs().isEmpty() && event.getMessage().getAttachments().isEmpty())
        {
	        MessageBuilder builders = new MessageBuilder()
	                .append(" **")
	                .append(warn.getAsMention() + " • " + event.getAuthor().getAsMention() +  " vous n'avez pas rentrer les informations demandé pour exécuté cette commande ")
	                .append("**");
	        event.getChannel().sendMessage(builders.build()).queue();
        }else {
        	List<User> userName = event.getMessage().getMentionedUsers();
	        String user = userName.get(0).getId();
	        String name = userName.get(0).getName();
	        
	        
            TextChannel ltc = event.getGuild().getTextChannelById(config.getString("logschannel"));

            
            
            EmbedBuilder ebuilder = new EmbedBuilder()
            		.setTitle(" Bannissement du serveur ", event.getGuild().getIconUrl())
            		.addField("Commande : Ban","", false )
            		.addField("Membre : " + name, "", false)
            		.addField("Executé par : " + event.getAuthor().getName(), "", false )
                    .setColor(Color.RED)
                    .setFooter("Requête Faite à " + formatter.format(date) + " le " + formatter2.format(date), event.getGuild().getIconUrl());

            
        	ltc.sendMessage(ebuilder.build()).complete();
	        
        	EmbedBuilder ebuilders = new EmbedBuilder()
            		.setTitle(" Bannissement d'un serveur ", event.getGuild().getIconUrl())
            		.addField("Vous avez été ban du serveur : " + event.getGuild().getName() + 
	            		"\n si vous ne connaissez pas la raison de ce kick, je vous conseil d'aller"
	            		+ " voir un administrateur ou de contacté la personne à l'origine de cette sanction"
	            		, "Auteur :"+event.getAuthor().getAsTag() +
	    	            		"\n Date officielle de la sanction" + " : " + formatter.format(date) + " le " + formatter2.format(date) + " .", false)
        	        .setColor(Color.RED)
        			.setFooter("Requête faite à" + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
        	        
	        
	        event.getGuild().getMemberById(user).getUser().openPrivateChannel().queue((channel) ->
	        {
	        	

				channel.sendMessage(builder.setEmbed(ebuilders.build()).build()).queue();
	        	 
	        	 
	        });
	        
	        
	        event.getGuild().getController().ban(user, 0, "").queue(success -> 
            {
            	
            		event.reply(ban.getAsMention()  + name + " ➤ à été ban avec succés.");
            }, failure -> 
            {
            	event.reply(name + " ne peux pas être ban par moi ou n'existe pas.");
            });
	        
        }	
    }

}
