package fr.warmadon.dev.other;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.warmadon.dev.BotConfig;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PingEvent extends ListenerAdapter{
    //Ping Listener
	BotConfig config; 
    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] args = e.getMessage().getContentRaw().split(" ");
        String name = e.getMember().getUser().getName(); //Get the name of the user who sent the message.
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        if(args[0].equalsIgnoreCase(config.getAltPrefix().toString() + "ping")){
            if(!e.getMember().getUser().isBot()){ //Checks to see if the user who triggered the event is a bot or not.
                String[] message = e.getMessage().getContentRaw().split(" ");
                String userName = e.getAuthor().getName();
                EmbedBuilder avatarEmbed = new EmbedBuilder(); //Creates the embed.
                //Sets the contents of the embed
                avatarEmbed.setTitle("Ping Du Bot", "https://github.com/warmadon0/");
                avatarEmbed.setColor(Color.GREEN);
                avatarEmbed.addField("Ping",e.getJDA().getPing() + " Ms",true);//Say Ping
                avatarEmbed.setFooter("Requète Fait à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + e.getMember().getUser().getName(), e.getGuild().getIconUrl());
                //
                e.getChannel().sendMessage(avatarEmbed.build()).queue(); //Send the embed as a message
                
            }
        }
        if(args[0].equalsIgnoreCase(config.getPrefix().toString() + "ping")){
            if(!e.getMember().getUser().isBot()){ //Checks to see if the user who triggered the event is a bot or not.
                String[] message = e.getMessage().getContentRaw().split(" ");
                String userName = e.getAuthor().getName();
                EmbedBuilder avatarEmbed = new EmbedBuilder(); //Creates the embed.
                //Sets the contents of the embed
                avatarEmbed.setTitle("Ping Du Bot", "https://github.com/warmadon0/");
                avatarEmbed.setColor(Color.GREEN);
                avatarEmbed.addField("Ping",e.getJDA().getPing() + " Ms",true);//Say Ping
                avatarEmbed.setFooter("Requète Fait à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + e.getMember().getUser().getName(), e.getGuild().getIconUrl());
                //
                e.getChannel().sendMessage(avatarEmbed.build()).queue(); //Send the embed as a message
                
            }
        }
    }
    
}
