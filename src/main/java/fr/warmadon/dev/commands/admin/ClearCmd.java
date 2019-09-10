package fr.warmadon.dev.commands.admin;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import fr.warmadon.dev.commands.AdminCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;

public class ClearCmd extends AdminCommand
{
    public ClearCmd()
    {
        this.name = "clear";
        this.help = "Clear Chat.";
        this.arguments = "<nombre/all>";
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
		
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
    	
        Emote warn = event.getGuild().getEmotesByName("warn", true).get(0);
    	Config config = ConfigFactory.load();
    	
    	if(event.getArgs().isEmpty() && event.getMessage().getAttachments().isEmpty())
        {
	        MessageBuilder builder = new MessageBuilder()
	                .append(" **")
	                .append(warn.getAsMention() + " • " + event.getAuthor().getAsMention() +  " vous n'avez pas rentrer les informations demandé pour exécuté cette commande ")
	                .append("**");
	        event.getChannel().sendMessage(builder.build()).queue();
        }else {
            TextChannel channel = event.getTextChannel();
            
            boolean isClearCommand = event.getMember().getPermissions(channel).contains(Permission.ADMINISTRATOR);
            
            
            
            
            if(event.getArgs().equalsIgnoreCase("all")) {
                
                TextChannel ltc = channel.getGuild().getTextChannelById(config.getString("logschannel"));

                
                
                EmbedBuilder ebuilder = new EmbedBuilder()
                		.setTitle(" Nettoyage d'un salon textuel ", event.getGuild().getIconUrl())
                		.addField("Commande : Clear","", false )
                		.addField("Membre : " + name, "", false)
                		.addField("Executé par : " + event.getAuthor().getName(), "", false )
                        .setColor(Color.BLUE)
                        .setFooter("Requête Faite à " + formatter.format(date) + " le " + formatter2.format(date), event.getGuild().getIconUrl());

                
            	ltc.sendMessage(ebuilder.build()).complete();
            	
            	clearAll(channel);
            	return;
            }
           
        	String number = event.getArgs();
        	int result = Integer.parseInt(number);			
        	System.out.println(result);
            
            
            if (isClearCommand)
            {
            	if(result < 2) {
            		event.reply("`Le minimum de messages à supprimer est de 2 messages.`");
            		return;
            	}
            	
                TextChannel ltc = channel.getGuild().getTextChannelById(config.getString("logschannel"));

                
                
                EmbedBuilder ebuilder = new EmbedBuilder()
                		.setTitle(" Nettoyage d'un channel textuel ", event.getGuild().getIconUrl())
                		.addField("Commande : Clear","", false )
                		.addField("Salon textuel : " + event.getChannel(), "", false)
                		.addField("Nombre de messages supprimé : " + number, "", false)
                		.addField("Executé par : " + event.getAuthor().getName(), "", false )
                        .setColor(Color.LIGHT_GRAY)
                        .setFooter("Requête Faite à " + formatter.format(date) + " le " + formatter2.format(date), event.getGuild().getIconUrl());


                
            	ltc.sendMessage(ebuilder.build()).complete();
            	
                clear(channel, result);
            }else {
            	event.reply(warn.getAsMention() + " • " + event.getAuthor().getAsMention() +  "Tu n'as pas la permission de faire celà ! " + event.getAuthor().getAsMention());
            }
        	
        	
        }

    	
    }
    public void clearAll(TextChannel channel)
    {
        OffsetDateTime twoWeeksAgo = OffsetDateTime.now().minus(2, ChronoUnit.WEEKS);

        
        System.out.println("Suppression de messages dans le chanel: " + channel);
        
                List<Message> messages = channel.getHistory().retrievePast(100).complete();
                
                messages.removeIf(m -> m.getCreationTime().isBefore(twoWeeksAgo));
        
                if (messages.isEmpty())
                {
                   
                    System.out.println("c " + channel);

                    channel.sendMessage("`Tu as supprimer le maximum de messages du channel !`").queue( message -> message.delete().queueAfter(5, TimeUnit.SECONDS) );
                   
                    
                    

                    return;
                }
        
                channel.sendMessage("`Tu as supprimer le maximum de messages du channel !`").queue( message -> message.delete().queueAfter(5, TimeUnit.SECONDS) );
                
                messages.forEach(m -> System.out.println("Terminé la suppression: " + m));
                channel.deleteMessages(messages).complete();
            }
    
    public void clear(TextChannel channel, int nombre)
    {
        
    	Emote clearemoji = channel.getGuild().getEmotesByName("clear", true).get(0);
        OffsetDateTime twoWeeksAgo = OffsetDateTime.now().minus(2, ChronoUnit.WEEKS);
        
        System.out.println("Suppression de messages dans le chanel: " + channel);
        
                List<Message> messages = channel.getHistory().retrievePast(nombre).complete();
                
                messages.removeIf(m -> m.getCreationTime().isBefore(twoWeeksAgo));
        
                if (messages.isEmpty())
                {
                   
                    System.out.println("c " + channel);

                    channel.sendMessage("`Tu as supprimer " + (nombre) + " messages !`").queue( message -> message.delete().queueAfter(5, TimeUnit.SECONDS) );
                   

                    return;
                }
        
				channel.sendMessage(clearemoji.getAsMention()  + " ➤ Tu as supprimer " + nombre + " messages !").queue( message -> message.delete().queueAfter(5, TimeUnit.SECONDS) );
                
                messages.forEach(m -> System.out.println("Terminé la suppression: " + m));
                channel.deleteMessages(messages).complete();
            }
    }   
