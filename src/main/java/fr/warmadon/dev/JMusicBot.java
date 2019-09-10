package fr.warmadon.dev;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.Command.Category;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;

import fr.warmadon.dev.commands.PingCommand;
import fr.warmadon.dev.commands.admin.*;
import fr.warmadon.dev.commands.dj.*;
import fr.warmadon.dev.commands.general.*;
import fr.warmadon.dev.commands.music.*;
import fr.warmadon.dev.commands.nsfw.*;
import fr.warmadon.dev.commands.owner.*;
import fr.warmadon.dev.entities.Prompt;
import fr.warmadon.dev.gui.GUI;
import fr.warmadon.dev.other.CatCommand;
import fr.warmadon.dev.other.CatEvent;
import fr.warmadon.dev.other.DogCommand;
import fr.warmadon.dev.other.DogEvent;
import fr.warmadon.dev.other.PingEvent;
import fr.warmadon.dev.other.ServerCommand;
import fr.warmadon.dev.other.TutoNitroCmd;
import fr.warmadon.dev.other.UserCommand;
import fr.warmadon.dev.settings.SettingsManager;
import fr.warmadon.dev.utils.OtherUtil;
import java.awt.Color;
import java.util.Objects;

import javax.security.auth.login.LoginException;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */

public class JMusicBot 
{
    public final static String PLAY_EMOJI  = ":play_pause:"; // ▶
    public final static String PAUSE_EMOJI = ":pause_button:"; // ⏸
    public final static String STOP_EMOJI  = ":stop_button:"; // ⏹
    public final static Permission[] RECOMMENDED_PERMS = new Permission[]{Permission.MESSAGE_READ, Permission.MESSAGE_WRITE, Permission.MESSAGE_HISTORY, Permission.MESSAGE_ADD_REACTION,
                                Permission.MESSAGE_EMBED_LINKS, Permission.MESSAGE_ATTACH_FILES, Permission.MESSAGE_MANAGE, Permission.MESSAGE_EXT_EMOJI,
                                Permission.MANAGE_CHANNEL, Permission.VOICE_CONNECT, Permission.VOICE_SPEAK, Permission.NICKNAME_CHANGE};
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // startup log
        Logger log = LoggerFactory.getLogger("Startup");
        
        // create prompt to handle startup
        Prompt prompt = new Prompt("Bot", "Switching to nogui mode. You can manually start in nogui mode by including the -Dnogui=true flag.", 
                "true".equalsIgnoreCase(System.getProperty("nogui", "false")));
        
        // check deprecated nogui mode (new way of setting it is -Dnogui=true)
        for(String arg: args)
            if("-nogui".equalsIgnoreCase(arg))
            {
                prompt.alert(Prompt.Level.WARNING, "GUI", "The -nogui flag has been deprecated. "
                        + "Please use the -Dnogui=true flag before the name of the jar. Example: java -jar -Dnogui=true Bot.jar");
                break;
            }
        
        // get and check latest version
       
        
        // load config
        BotConfig config = new BotConfig(prompt);
        config.load();
        if(!config.isValid())
            return;
        
        // set up the listener
        EventWaiter waiter = new EventWaiter();
        SettingsManager settings = new SettingsManager();
        Bot bot = new Bot(waiter, config, settings);
        
        // set up the command client
        CommandClientBuilder cb = new CommandClientBuilder()
                .setPrefix(config.getPrefix())
                .setAlternativePrefix(config.getAltPrefix())
                .setOwnerId(Long.toString(config.getOwnerId()))
                .setEmojis(config.getSuccess(), config.getWarning(), config.getError())
                .setHelpWord("none")
                //config.getHelp()
                .setLinkedCacheSize(200)
                .setGuildSettingsManager(settings)
                .addCommands(
                		new LicksEvent(),
                		new AnalEvent(),
                		new HentaigifEvent(),
                		new PorngifEvent(),
                		new SquirtEvent(),
                		new PussyEvent(),
                		new SuckEvent(),
                		new DickEvent(),
                		new NudeEvent(),
                		new FuckEvent(),
                		new AssEvent(),
                		new BoobsEvent(),
                		
                		new CatEvent(),
                		new DogEvent(),
                		new helpEmbed(),
                        new PingCommand(),
                        new SettingsCmd(),
                        new ServerCommand(),
                        
                        new UserCommand(),
                        new TutoNitroCmd(),
                        
                        new LyricsCmd(bot),
                        new NowplayingCmd(bot),
                        new PlayCmd(bot, config.getLoading()),
                        new PlaylistsCmd(bot),
                        new QueueCmd(bot),
                        new RemoveCmd(bot),
                        new SearchCmd(bot, config.getSearching()),
                        new SCSearchCmd(bot, config.getSearching()),
                        new ShuffleCmd(bot),
                        new SkipCmd(bot),
                        
                        new ForceskipCmd(bot),
                        new MoveTrackCmd(bot),
                        new PauseCmd(bot),
                        new PlaynextCmd(bot, config.getLoading()),
                        new RepeatCmd(bot),
                        new SkiptoCmd(bot),
                        new StopCmd(bot),
                        new VolumeCmd(bot),
                        
                        new MuteCmd(),
                        new UnMuteCmd(),
                        new ClearCmd(),
                        new KickCmd(),
                        new BanCmd(),
                        new SetdjCmd(),
                        new SettcCmd(),
                        new SetvcCmd(),
                        
                        new AutoplaylistCmd(bot),
                        new PlaylistCmd(bot),
                        new SetavatarCmd(),
                        new SetgameCmd(),
                        new SetnameCmd(),
                        new SetstatusCmd(),
                        new ShutdownCmd(bot)
                );
        if(config.useEval())
            cb.addCommand(new EvalCmd(bot));
        boolean nogame = false;
        if(config.getStatus()!=OnlineStatus.UNKNOWN)
            cb.setStatus(config.getStatus());
        if(config.getGame()==null)
            cb.useDefaultGame();
        else if(config.getGame().getName().equalsIgnoreCase("none"))
        {
            cb.setGame(null);
            nogame = true;
        }
        else
            cb.setGame(config.getGame());
        CommandClient client = cb.build();
        
        if(!prompt.isNoGUI())
        {
            try 
            {
                GUI gui = new GUI(bot);
                bot.setGUI(gui);
                gui.init();
            } 
            catch(Exception e) 
            {
                log.error("Could not start GUI. If you are "
                        + "running on a server or in a location where you cannot display a "
                        + "window, please run in nogui mode using the -Dnogui=true flag.");
            }
        }
        
        log.info("Loaded config from "+config.getConfigLocation());
        
        // attempt to log in and start
        try
        {
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(config.getToken())
                    .setAudioEnabled(true)
                    .setGame(nogame ? null : Game.listening("Chargement..."))
                    .setStatus(config.getStatus()==OnlineStatus.INVISIBLE||config.getStatus()==OnlineStatus.OFFLINE ? OnlineStatus.INVISIBLE : OnlineStatus.DO_NOT_DISTURB)
                    .addEventListener(client, waiter, new Listener(bot))
                    .addEventListener(new EventListenet())
                    .setBulkDeleteSplittingEnabled(true)
                    .build();
            bot.setJDA(jda);
        }
        catch (LoginException ex)
        {
            prompt.alert(Prompt.Level.ERROR, "Bot", ex + "\nPlease make sure you are "
                    + "editing the correct config.txt file, and that you have used the "
                    + "correct token (not the 'secret'!)\nConfig Location: " + config.getConfigLocation());
            System.exit(1);
        }
        catch(IllegalArgumentException ex)
        {
            prompt.alert(Prompt.Level.ERROR, "Bot", "Some aspect of the configuration is "
                    + "invalid: " + ex + "\nConfig Location: " + config.getConfigLocation());
            System.exit(1);
        }
    }
}
