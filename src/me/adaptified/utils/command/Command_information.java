package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.ANY, permission = "utils.info")
public class Command_information extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage(ChatColor.GREEN + "This plugin was made by Adaptified and Prozza, made to set the core of a server!");
        sender.sendMessage(ChatColor.RED + "This plugin's version is " + plugin.getVersion() + "!");
        sender.sendMessage(ChatColor.AQUA + "This plugin is running on " + plugin.getConfig().getString("server-name") + "!");
        sender.sendMessage(ChatColor.RED + "The server's current forums are " + plugin.getConfig().getString("website"));
        sender.sendMessage(ChatColor.BLUE + "The server's owner is " + plugin.getConfig().getString("owner") + "!");
        return true;
    }

}
