package fr.warmadon.dev;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
	    name = {"help", "aide"},
	    description = ""
	)
public class helpEmbed extends Command {

    public helpEmbed()
    {
        this.name = "help";
        this.help = "";
        this.guildOnly = false;
        this.aliases = new String[]{"aide"};
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
        
        EmbedBuilder ebuilder = new EmbedBuilder()
        		.setTitle(" Pages des commandes #1 ", event.getGuild().getIconUrl())
        		.addBlankField(true)
        		.addField(":mag: •» **__Informations__**", "", false)
        		
        		.addField("**Préfix :**", "!", false)
        		.addField("**Serveur :**", "Rencontre - FR 『💕』", false)
        		.addField("**Fondateur :**", "BubulleDeSavons#0666", false)
        		.addField("**Créateur du robot :**", "Warmadon#0666", false)
        		.addBlankField(true)
        		.addField(":gear: •» **__Général__**", "", false)
        		
        		.addField("**!ping**", "→ Permet de voir la latence du robot", false)
        		.addField("**!settings**", "→ Permet de montrer les paramètres du robot", false ) 
        		.addField("**!user <membre>**", "→ Permet d'avoir des informations sur le membre mentionné", false)
        		.addField("**!server**", "→ Permet d'avoir des informations sur le serveur", false)
        		.addField("**!boost**", "Tutoriel pour boost le serveur ", false)
        		.setColor(Color.magenta)
                .setFooter("Requête faite à" + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
        
        EmbedBuilder ebuilder2 = new EmbedBuilder()
        		.setTitle(" Pages des commandes #2 ", event.getGuild().getIconUrl())

        		.addBlankField(true)
        		.addField(":notes: •» **__Musique__**", "", false)

        		.addField("**!lyrics <titre>**", "→ Permet d'afficher les paroles de la chanson demandé", false)
        		.addField("**!nowplaying**", "→ Permet de voir la musique qui est en cour de lecture", false)
        		.addField("**!play <titre / url>**", "→ Permet de jouer la chanson demandé", false)
        		.addField("**!playlists**", "→ Permet de montrer les playlist disponibles", false)
        		.addField("**!queue [page]**", "→ Permet d'afficher la file d'écoute actuelle", false)
        	    .addField("**!remove <position / all>**", "→ Permet de supprimer une musique de la file d'attente", false)
        		.addField("**!search <question>**", "→ Permet de rechercher sur YouTube une requête fournie", false)
        		.addField("**!scsearch <question>**", "→ Permet de rechercher sur SoundCloud une requête fournie", false)
        	    .addField("**!shuffle**", "→ Permet de mélanger aléatoirement les musiques dans a file d'attente", false)
        		.addField("**!skip**", "→ Permet de voter pour ignorer la musique en cour de lecture", false)
        		.addBlankField(true)
        		.addField(":headphones: •» **__DJ__**", "", false)

        		.addField("**!forceskip**", "→ Permet d'ignorer la musique en cour de lecture sans vote", false)
        		.addField("**!movetrack <depuis> <vers>**", "→ Permet de déplacer une musique de la file d'attente vers une position différente ", false)
        		.addField("**!pause**", "→ Permet de mettre en pause la musique en cour de lecture", false)
        		.addField("**!playnext <titre / url>**", "→ Permet de jouer une musique après celle en cour de lecture", false)
        		.addField("**!repeat <on / off>**", "→ Permet de rejouer la musique en cour de lecture lorsque qu'elle est finie", false)
        		.addField("**!skipto <position>**", "→ Permet de passer à la musique spécifié", false)
        		.addField("**!stop**", "→ Permet d'arreter la musique", false)
        		.addField("**!volume [0-150]**", "→ Permet de changer le volume", false)


                .setColor(Color.magenta)
                .setFooter("Requête faite à" + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
        

        EmbedBuilder ebuilder3 = new EmbedBuilder()
        		.setTitle(" Pages des commandes #3 ", event.getGuild().getIconUrl())
        .addBlankField(true)
        .addField(":game_die:  •» **__Divertissement & Autres__**", "", false)
		.addField("**!cat**", "→ Permet d'avoir une image de chat mignon", false)
		.addField("**!dog**", "→ Permet d'avoir une image de chien mignon", false)
		.addField("**!avatar**", "→ Permet d'avoir l'image de l'avatar demandé", false)
		.addField("**!hug**", "→ Permet d'avoir un gif d'un câlin", false)
		.addField("**!kiss**", "→ Permet d'avoir un gif d'un bisou", false)
		.addField("**!bang**", "→ Permet d'avoir un gif d'un coup de feu", false)
		.addField("**!slap**", "→ Permet d'avoir un gif d'une claque", false)
		.addField("**!punch**", "→ Permet d'avoir un gif d'un coup de poing", false)
		.addBlankField(true)
		.addField(" :underage: •» **__NSFW__**", "", false)
		.addField(" **catégorie**", ":warning: Les commandes de cette catégorie sont désactivé temporairement ! :warning:", false)
		.addField(" **!fuck**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!nude**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!dick**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!suck**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!pussy**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!squirt**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!ass**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!porngif**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!hentaigif**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!anal**", "→ Permet d'avoir une image / gif pornographique", false)
		.addField(" **!lick**", "→ Permet d'avoir une image / gif pornographique", false)
		 
		
		.setColor(Color.magenta)
		.setFooter("Requête faite à" + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());

        
        EmbedBuilder ebuilder4 = new EmbedBuilder()
        		.setTitle(" Pages des commandes #4 ", event.getGuild().getIconUrl())
        		.addBlankField(true)
        		.addField(":tools: •» **__Modération__**", "", false)
        		.addField("**!mute <membre>**", "→ Permet de mute le membre mentionné", false)
        		.addField("**!unmute <membre>**", "→ Permet d'unmute le membre mentionné", false)
        		.addField("**!kick <membre>**", "→ Permet d'expluser le membre mentionné", false)
        		.addField("**!ban <membre>**", "→ Permet de bannir le membre mentionné", false)
        		.addField("**!clear <nombre / all>**", "→ Permet de supprimer les messages oû la commande à été exécuté", false)
        		.addField("**!setdj <rôle / none>**", "→ Permet de définir le rôle DJ", false)
        		.addField("**!settc <channel / none>**", "→ Permet de définir le channel oû les commandes musiques peuvent s'exécuté", false)
        		.addField("**!setvc <channel / none>**", "→ Permet de définir le channel vocal oû la musique peut s'exécuté",  false)
        	
       		 .setColor(Color.magenta)
       		.setFooter("Requête faite à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());
        
        
        EmbedBuilder ebuilder5 = new EmbedBuilder()
        		.setTitle(" Pages des commandes #5 ", event.getGuild().getIconUrl())
        		.addBlankField(true)
        		.addField(":link: •» **__Liens utiles__**", "", false)
        		.addField("**Invitation**", "→ https://discord.gg/fYyUWky", false)
        		.addField("**Instagram**", "→ https://www.instagram.com/rencontrefr_/?hl=fr", false)
        		.addField("**Twitter**", "→ https://twitter.com/FrRencontre", false)
        		.addField("**Snapchat**", "→ https://cdn.discordapp.com/attachments/615219661428097055/620046281062088735/snapcode.png", false)
        		.addField("**Site**", "→ https://rencontre.fr", false)
        		.addField("**Paypal**", "→ :warning: - Erreur", false)
        	
       		 .setColor(Color.magenta)
       		.setFooter("Requête faite à " + formatter.format(date) + " le " + formatter2.format(date) + " par " + event.getMember().getUser().getName(), event.getGuild().getIconUrl());

        event.reply( event.getAuthor().getAsMention() + " ● Les pages de commandes t'ont été envoyer en message privé! " );
        event.getGuild().getMemberById(event.getAuthor().getId()).getUser().openPrivateChannel().queue((channel) ->
        {
        	
        	 channel.sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
        	 channel.sendMessage(builder.setEmbed(ebuilder2.build()).build()).queue();
        	 channel.sendMessage(builder.setEmbed(ebuilder3.build()).build()).queue();
        	 channel.sendMessage(builder.setEmbed(ebuilder4.build()).build()).queue();
        	 channel.sendMessage(builder.setEmbed(ebuilder5.build()).build()).queue();
                     });
        
        
        
       
	}

}
