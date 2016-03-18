package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.forums")
public class Command_forums extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("utils.forums")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + plugin.config.getString("server.forum"));
        return true;
    }

}
