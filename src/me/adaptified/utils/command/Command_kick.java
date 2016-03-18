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

@CommandOptions(source = SourceType.PLAYER, permission = "utils.kick")
public class Command_kick extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "/kick <player> <reason>");
            return true;
        }

        if (!sender.hasPermission("utils.kick")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.\nPlease review arguments.");
        }

        String kickReason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        if (Utils.plugin.config.getBoolean("server.disablepunishbroadcasts")) {
            logger.info("Not broadcasting message, disabled in config!");
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Kicking: " + player.getName() + " Reason: " + kickReason);
        }

        player.kickPlayer(ChatColor.RED + "You have been kicked\nReason: " + ChatColor.YELLOW + kickReason + ChatColor.RED + "\nKicked by ~ " + ChatColor.YELLOW + sender.getName());

        sender.sendMessage(ChatColor.GRAY + "Player successfully kicked");

        return true;
    }

}
