package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.warn")
public class Command_warn extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            return showUsage();
        }

        if (!sender.hasPermission("utils.warn")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.\nPlease review arguments.");
        }

        String warnReason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        if (Utils.plugin.config.getBoolean("server.disablepunishbroadcasts")) {
            logger.info("Not broadcasting message, disabled in config!");
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Warning: " + player.getName() + " Reason: " + warnReason);
        }

        player.sendMessage(ChatColor.RED + "You have been warned!" + ChatColor.RED + "\nReason: " + ChatColor.YELLOW + warnReason + ChatColor.RED + "\nWarned by ~ " + ChatColor.YELLOW + sender.getName());

        sender.sendMessage(ChatColor.GRAY + "Player successfully warned");

        return true;
    }

}
