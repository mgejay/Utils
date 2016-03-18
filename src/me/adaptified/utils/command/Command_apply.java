package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.apply")
public class Command_apply extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("utils.apply")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        if (Utils.plugin.config.getBoolean("server.canapply")) {
            sender.sendMessage(ChatColor.RED + "So, you wan't to be a staff member, ayy?");
            sender.sendMessage(ChatColor.RED + "Read more information at " + plugin.config.getString("server.forum") + ".");
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "It seems as if information for applying is disabled.");
        }
        return true;
    }
}
