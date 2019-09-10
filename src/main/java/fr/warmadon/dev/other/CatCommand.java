package fr.warmadon.dev.other;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import fr.warmadon.dev.BotConfig;


public class CatCommand extends ListenerAdapter{
	
BotConfig config; 
public void onGuildMessageReceived(GuildMessageReceivedEvent e){
    String[] args = e.getMessage().getContentRaw().split(" ");
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    if(args[0].equalsIgnoreCase(config.getAltPrefix() + "cat")){
		URL url = null;
		
		try {
			url = new URL("https://api.thecatapi.com/api/images/get?format=json&results_per_page=1");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str= null;
		try {
			while (null != (str = br.readLine())) {
				System.out.println(str);
				JSONObject json = new JSONObject(str.replace("[", "").replace("]", ""));
				String finalstring = json.getString("url");
				System.out.println(finalstring);
			    EmbedBuilder avatarEmbed = new EmbedBuilder(); //Creates the embed.
			    //Sets the contents of the embed
			    avatarEmbed.setTitle("Chat", "https://github.com/warmadon0/");
			    avatarEmbed.setImage(finalstring);
			    avatarEmbed.setColor(Color.GREEN);
			    avatarEmbed.setFooter("Requète Fait à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + e.getMember().getUser().getName(), e.getGuild().getIconUrl());
			    e.getChannel().sendMessage(avatarEmbed.build()).queue(); //Send the embed as a message
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			String finalstring = "https://s3.us-west-2.amazonaws.com/cdn2.thecatapi.com/images/yYqvGaUsn.jpg";
			System.out.println(finalstring);
		    EmbedBuilder avatarEmbed = new EmbedBuilder(); //Creates the embed.
		    //Sets the contents of the embed
		    avatarEmbed.setTitle("Chat", "https://github.com/warmadon0/");
		    avatarEmbed.setImage(finalstring);
		    avatarEmbed.setFooter("Requète Fait à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + e.getMember().getUser().getName(), e.getGuild().getIconUrl());
		    e.getChannel().sendMessage(avatarEmbed.build()).queue(); //Send the embed as a message
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
	
    	
}
    if(args[0].equalsIgnoreCase(config.getPrefix() + "cat")){
		URL url = null;
		
		try {
			url = new URL("https://api.thecatapi.com/api/images/get?format=json&results_per_page=1");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str= null;
		try {
			while (null != (str = br.readLine())) {
				System.out.println(str);
				JSONObject json = new JSONObject(str.replace("[", "").replace("]", ""));
				String finalstring = json.getString("url");
				System.out.println(finalstring);
			    EmbedBuilder avatarEmbed = new EmbedBuilder(); //Creates the embed.
			    //Sets the contents of the embed
			    avatarEmbed.setTitle("Chat", "https://github.com/warmadon0/");
			    avatarEmbed.setImage(finalstring);
			    avatarEmbed.setColor(Color.GREEN);
			    avatarEmbed.setFooter("Requète Fait à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + e.getMember().getUser().getName(), e.getGuild().getIconUrl());
			    e.getChannel().sendMessage(avatarEmbed.build()).queue(); //Send the embed as a message
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			String finalstring = "https://s3.us-west-2.amazonaws.com/cdn2.thecatapi.com/images/yYqvGaUsn.jpg";
			System.out.println(finalstring);
		    EmbedBuilder avatarEmbed = new EmbedBuilder(); //Creates the embed.
		    //Sets the contents of the embed
		    avatarEmbed.setTitle("Chat", "https://github.com/warmadon0/");
		    avatarEmbed.setImage(finalstring);
		    avatarEmbed.setFooter("Requète Fait à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + e.getMember().getUser().getName(), e.getGuild().getIconUrl());
		    e.getChannel().sendMessage(avatarEmbed.build()).queue(); //Send the embed as a message
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		
	
    	
}   }}