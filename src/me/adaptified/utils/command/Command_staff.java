package me.adaptified.utils.command;

import me.adaptified.utils.ConfigEntries;
import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.staff")
public class Command_staff extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final String jrmods = Utils.plugin.config.getString(ConfigEntries.SERVER_JRMODS);
        final String moderators = Utils.plugin.config.getString(ConfigEntries.SERVER_MODERATORS);
        final String headmod = Utils.plugin.config.getString(ConfigEntries.SERVER_HEADMOD);
        final String jradmin = Utils.plugin.config.getString(ConfigEntries.SERVER_JRADMDINS);
        final String admins = Utils.plugin.config.getString(ConfigEntries.SERVER_ADMINS);
        final String headadmin = Utils.plugin.config.getString(ConfigEntries.SERVER_HEADADMIN);
        final String builders = Utils.plugin.config.getString(ConfigEntries.SERVER_BUILDERS);
        final String coowner = Utils.plugin.config.getString(ConfigEntries.SERVER_COOWNER);

        if (!sender.hasPermission("utils.staff")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        if (!jrmods.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "Junior Mods: " + jrmods);
        }
        if (!moderators.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "Moderators: " + moderators);    
        }
        if (!headmod.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "Head Mod: " + moderators);    
        }
        if (!jradmin.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "Junior Admins: " + jradmin);
        }
        if (!admins.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "Admins: " + jradmin);    
        }
        if (!headadmin.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "Head Admin: " + headadmin);
        }
        if (!builders.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "Builders: " + builders);
        }
        if (!coowner.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "Co Owner: " + coowner);
        }
        if (jrmods.equals("[]") && 
           moderators.equals("[]") && 
           headmod.equals("[]") &&
           jradmin.equals("[]") &&
           admins.equals("[]") &&
           headadmin.equals("[]") &&
           builders.equals("[]") &&
           coowner.equals("[]"))
        {
            sender.sendMessage(ChatColor.BLUE + "There are no entries in the config.yml");
        }
        return true;
    }
}
