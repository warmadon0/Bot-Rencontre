package fr.warmadon.dev.commands.music;

import fr.warmadon.dev.Bot;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public class SCSearchCmd extends SearchCmd 
{
    public SCSearchCmd(Bot bot, String searchingEmoji)
    {
        super(bot, searchingEmoji);
        this.searchPrefix = "scsearch:";
        this.name = "scsearch";
        this.help = "recherche dans Soundcloud une requête fournie";
        this.aliases = new String[]{};
    }
}
