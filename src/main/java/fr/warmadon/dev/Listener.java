package fr.warmadon.dev;

import fr.warmadon.dev.utils.OtherUtil;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class Listener extends ListenerAdapter
{
    private final Bot bot;
    
    public Listener(Bot bot)
    {
        this.bot = bot;
    }
    
	Config config = ConfigFactory.load();
    EmbedBuilder ebuilders = new EmbedBuilder();
    EmbedBuilder ebuilders1 = new EmbedBuilder();
	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
	    Member member = event.getMember();
	    Guild guild = event.getGuild();
	    Emote welcome = event.getGuild().getEmotesByName("welcome", true).get(0);
	    ebuilders.setTitle(" Arrivé d'un membre ", event.getGuild().getIconUrl());
	    ebuilders.addField(welcome.getAsMention() + "● Bienvenue à toi " +
            event.getMember().getEffectiveName() + ", sur notre serveur discord officiel de rencontre.",   
             "Membre : #" + guild.getMembers().size() + " / Pseudo #" + event.getMember().getEffectiveName() +  " !", false);
	    ebuilders.setColor(Color.CYAN);
	    ebuilders.setFooter("Requête faite par Rencontre - FR 『💕』『BOT』", event.getGuild().getIconUrl());

	    
	    if (config.getString("welcomechannel") != null) {

	        TextChannel ltc = guild.getTextChannelById(config.getString("welcomechannel"));


	                        ltc.sendMessage(ebuilders.build()).complete();
	  
	        
                    
	        	System.out.printf("Join " + member.getEffectiveName() );
	    }else {
	    	System.out.println(config.getString("welcomechannel"));
	    }
	}
    
	@Override
		public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		Member member = event.getMember();
	    Guild guild = event.getGuild();
	    Emote leave = event.getGuild().getEmotesByName("leave", true).get(0);
	    ebuilders1.setTitle(" Départ d'un membre ", event.getGuild().getIconUrl());
	    ebuilders1.addField(leave.getAsMention() + "● Au revoir " +
            event.getMember().getEffectiveName() + ", de notre serveur discord officiel de rencontre.",
            "Membre : #" + guild.getMembers().size() + " / Pseudo #" + event.getMember().getEffectiveName() +  " !", false);
	    ebuilders1.setColor(Color.BLUE);
	    ebuilders1.setFooter("Requête faite par Rencontre - FR 『💕』『BOT』", event.getGuild().getIconUrl());

	    
	    if (config.getString("welcomechannel") != null) {

	        TextChannel ltc = guild.getTextChannelById(config.getString("welcomechannel"));


	                        ltc.sendMessage(ebuilders1.build()).complete();
	                      
	                      
	                      
	                System.out.printf("Leave " + member.getEffectiveName() );
	    }else {
	    	System.out.println(config.getString("welcomechannel"));
	    }
		}
	
    @Override
    public void onReady(ReadyEvent event) 
    {
        if(event.getJDA().getGuilds().isEmpty())
        {
            Logger log = LoggerFactory.getLogger("MusicBot");
            log.warn("Ce bot ne fait partie d'aucune guilde! Utilisez le lien suivant pour ajouter le bot à vos guildes!");
            log.warn(event.getJDA().asBot().getInviteUrl(JMusicBot.RECOMMENDED_PERMS));
        }
        credit(event.getJDA());
        event.getJDA().getGuilds().forEach((guild) -> 
        {
            try
            {
                String defpl = bot.getSettingsManager().getSettings(guild).getDefaultPlaylist();
                VoiceChannel vc = bot.getSettingsManager().getSettings(guild).getVoiceChannel(guild);
                if(defpl!=null && vc!=null && bot.getPlayerManager().setUpHandler(guild).playFromDefault())
                {
                    guild.getAudioManager().openAudioConnection(vc);
                }
            }
            catch(Exception ignore) {}
        });
        if(bot.getConfig().useUpdateAlerts())
        {
            bot.getThreadpool().scheduleWithFixedDelay(() -> 
            {
                User owner = bot.getJDA().getUserById(bot.getConfig().getOwnerId());
                if(owner!=null)
                {
                    String currentVersion = OtherUtil.getCurrentVersion();
                }
            }, 0, 24, TimeUnit.HOURS);
        }
    }
    
    @Override
    public void onGuildMessageDelete(GuildMessageDeleteEvent event) 
    {
        bot.getNowplayingHandler().onMessageDelete(event.getGuild(), event.getMessageIdLong());
    }
    
    @Override
    public void onShutdown(ShutdownEvent event) 
    {
        bot.shutdown();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) 
    {
        credit(event.getJDA());
    }
    
    // make sure people aren't adding clones to dbots
    private void credit(JDA jda)
    {
        return;
    }
}
