package me.adaptified.utils.command;

import me.adaptified.utils.StaffChatListen;
import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = net.pravian.aero.command.SourceType.PLAYER, permission = "utils.staffchat", aliases = "sc")
public class Command_staffchat extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Please provide a message!");
            return true;
        }

        if (!sender.hasPermission("utils.staffchat")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        StaffChatListen.Chat(sender, StringUtils.join(args, " "));
        return true;
    }
}
