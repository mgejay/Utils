package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.info", aliases = "info")
public class Command_information extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("utils.info")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }
        sender.sendMessage(ChatColor.GREEN + "This plugin was made by Adaptified and Prozza, made to set the core of a server!");
        sender.sendMessage(ChatColor.RED + "This plugin's version is " + plugin.getVersion() + "!");
        sender.sendMessage(ChatColor.AQUA + "This plugin is running on " + plugin.config.getString("server.name") + "!");
        sender.sendMessage(ChatColor.RED + "The server's current forums are " + plugin.config.getString("server.website"));
        sender.sendMessage(ChatColor.BLUE + "The server's owner is " + plugin.config.getString("server.owner") + "!");
        return true;
    }

}
