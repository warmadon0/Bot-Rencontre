package fr.warmadon.dev;

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

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

import fr.warmadon.dev.settings.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

public class HentaiGif extends Command {

    public HentaiGif()
    {
        this.name = "hentaigif";
        this.help = "";
        this.guildOnly = false;
        this.aliases = new String[]{""};
    }
	
	@Override
	protected void execute(CommandEvent event) {
	   	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        TextChannel tchan = s.getTextChannel(event.getGuild());
       
        VoiceChannel vchan = s.getVoiceChannel(event.getGuild());
        Role role = s.getRole(event.getGuild());
        MessageBuilder builder = new MessageBuilder();
        
		URL url = null;
		
		try {
			url = new URL("https://nekos.life/api/v2/img/hentai");
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
					System.out.println(str);
					JSONObject json = new JSONObject(str);
					String finalstring = json.getString("url");
					System.out.println(finalstring);
					
			        EmbedBuilder ebuilder = new EmbedBuilder()
			        		.setTitle(" ", event.getGuild().getIconUrl())
			                .setColor(Color.green)
			        		.setImage(finalstring)
			                .setFooter("Requète Fait à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
			        if(finalstring.equalsIgnoreCase(".mp4")) {
			        	ebuilder.addField("Vidéo !",finalstring, true);
			        }
			        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
