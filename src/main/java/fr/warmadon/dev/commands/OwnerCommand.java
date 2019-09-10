package fr.warmadon.dev.commands;

import com.jagrosh.jdautilities.command.Command;

/**
 *
 * @author Paul Petitnicolas <warmalol@gmail.com>
 */
public abstract class OwnerCommand extends Command
{
    public OwnerCommand()
    {
        this.category = new Category("Créateur");
        this.ownerCommand = true;
    }
}
