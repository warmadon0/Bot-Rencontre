package fr.warmadon.dev.other;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.doc.standard.CommandInfo;

import fr.warmadon.dev.settings.Settings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;

@CommandInfo(
	    name = {"boost", "tutoboost"},
	    description = "Tutoriel pour boost le serveur !"
	)
public class TutoNitroCmd  extends Command {
	
    public TutoNitroCmd()
    {
        this.name = "boost";
        this.help = "Tutoriel pour boost le serveur !";
        this.guildOnly = false;
        this.aliases = new String[]{"tutoboost"};
    }

    @Override
    protected void execute(CommandEvent event) {
    	Emote booster = event.getGuild().getEmotesByName("booster", true).get(0);
    	Emote boost = event.getGuild().getEmotesByName("boost", true).get(0);
    	Emote blobboost = event.getGuild().getEmotesByName("blobboost", true).get(0);
    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Settings s = event.getClient().getSettingsFor(event.getGuild());
        TextChannel tchan = s.getTextChannel(event.getGuild());
       
        VoiceChannel vchan = s.getVoiceChannel(event.getGuild());
        Role role = s.getRole(event.getGuild());
        MessageBuilder builder = new MessageBuilder();
        
        EmbedBuilder ebuilder = new EmbedBuilder()
        		.setTitle(booster.getAsMention() + " • Explications du boost • " + booster.getAsMention(), event.getGuild().getIconUrl())
        		.addBlankField(true)
        		.addField(boost.getAsMention() + " •» étape #1\r\n" ,
        				" ➤ Cliquez sur les `trois petit points du serveur`", false)
        		.addBlankField(true)
        		.addField(boost.getAsMention() + " •» étape #2\r\n" , 
        				" ➤ Cliquez sur `Nitro Server Boost`",false)
        		.addBlankField(true)
        		.addField(boost.getAsMention() + " •» étape #3\r\n" , 
        				" ➤ Cliquez sur `Boost Server`",false)
        		.addBlankField(true)
        		.addField(boost.getAsMention() + " •» Fin\r\n" , 
        				" ➤ "+ blobboost.getAsMention()+" • Et voilà nous vous remercions d’avoir potentiellement booster le serveur et vous êtes remercié d'un grade avec quelques avantages visible en haut du tableau des membres ^^", false)
  
        		.setColor(Color.MAGENTA)
                .setFooter("Requête faite à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());

        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
        
    }
    
}
