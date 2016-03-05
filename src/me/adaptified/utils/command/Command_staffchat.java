package me.adaptified.utils.command;

import me.adaptified.utils.StaffChatListen;
import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.PLAYER, permission = "utils.staffchat")
public class Command_staffchat extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Please provide a message!");
            return true;
        }

        StaffChatListen.Chat(sender, StringUtils.join(args, " "));
        return true;
    }
}
