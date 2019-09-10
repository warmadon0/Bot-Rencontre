package fr.warmadon.dev.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import fr.warmadon.dev.commands.OwnerCommand;
import net.dv8tion.jda.core.entities.Game;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SetgameCmd extends OwnerCommand
{
    public SetgameCmd()
    {
        this.name = "setgame";
        this.help = "définit le jeu auquel le bot joue";
        this.arguments = "[action] [game]";
        this.guildOnly = false;
        this.children = new OwnerCommand[]{
            new SetlistenCmd(),
            new SetstreamCmd(),
            new SetwatchCmd()
        };
    }
    
    @Override
    protected void execute(CommandEvent event) 
    {
        String title = event.getArgs().toLowerCase().startsWith("playing") ? event.getArgs().substring(7).trim() : event.getArgs();
        try
        {
            event.getJDA().getPresence().setGame(title.isEmpty() ? null : Game.playing(title));
            event.reply(event.getClient().getSuccess()+" **"+event.getSelfUser().getName()
                    +"** is "+(title.isEmpty() ? "ne joue plus rien." : "Lecture en cours `"+title+"`"));
        }
        catch(Exception e)
        {
            event.reply(event.getClient().getError()+" Le jeu n'a pas pu être réglé!");
        }
    }
    
    private class SetstreamCmd extends OwnerCommand
    {
        private SetstreamCmd()
        {
            this.name = "stream";
            this.aliases = new String[]{"twitch","streaming"};
            this.help = "définit le jeu que le bot joue à un stream";
            this.arguments = "<username> <game>";
            this.guildOnly = false;
        }

        @Override
        protected void execute(CommandEvent event)
        {
            String[] parts = event.getArgs().split("\\s+", 2);
            if(parts.length<2)
            {
                event.replyError("S'il vous plaît inclure un nom d'utilisateur Twitch et le nom du jeu à 'stream'");
                return;
            }
            try
            {
                event.getJDA().getPresence().setGame(Game.streaming(parts[1], "https://twitch.tv/"+parts[0]));
                event.replySuccess("**"+event.getSelfUser().getName()
                        +"** est maintenant en streaming `"+parts[1]+"`");
            }
            catch(Exception e)
            {
                event.reply(event.getClient().getError()+" Le jeu n'a pas pu être réglé!");
            }
        }
    }
    
    private class SetlistenCmd extends OwnerCommand
    {
        private SetlistenCmd()
        {
            this.name = "listen";
            this.aliases = new String[]{"listening"};
            this.help = "définit le jeu que le bot écoute";
            this.arguments = "<title>";
            this.guildOnly = false;
        }

        @Override
        protected void execute(CommandEvent event)
        {
            if(event.getArgs().isEmpty())
            {
                event.replyError("S'il vous plaît inclure un titre à écouter!");
                return;
            }
            String title = event.getArgs().toLowerCase().startsWith("to") ? event.getArgs().substring(2).trim() : event.getArgs();
            try
            {
                event.getJDA().getPresence().setGame(Game.listening(title));
                event.replySuccess("**"+event.getSelfUser().getName()+"** écoute maintenant `"+title+"`");
            } catch(Exception e) {
                event.reply(event.getClient().getError()+" Le jeu n'a pas pu être réglé!");
            }
        }
    }
    
    private class SetwatchCmd extends OwnerCommand
    {
        private SetwatchCmd()
        {
            this.name = "watch";
            this.aliases = new String[]{"watching"};
            this.help = "définit le jeu que le bot regarde";
            this.arguments = "<title>";
            this.guildOnly = false;
        }

        @Override
        protected void execute(CommandEvent event)
        {
            if(event.getArgs().isEmpty())
            {
                event.replyError("S'il vous plaît inclure un titre à regarder!");
                return;
            }
            String title = event.getArgs();
            try
            {
                event.getJDA().getPresence().setGame(Game.watching(title));
                event.replySuccess("**"+event.getSelfUser().getName()+"** regarde maintenant `"+title+"`");
            } catch(Exception e) {
                event.reply(event.getClient().getError()+" Le jeu n'a pas pu être réglé!");
            }
        }
    }
}
