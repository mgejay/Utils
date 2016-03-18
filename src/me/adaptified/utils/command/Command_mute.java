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

@CommandOptions(source = SourceType.PLAYER, permission = "utils.mute")
public class Command_mute extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/mute <player> [reason-optional]");
            return true;
        }

        if (!sender.hasPermission("utils.mute")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        String reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        final Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        if (plugin.mutedusers.contains(player.getName())) {
            sender.sendMessage(ChatColor.RED + "That player is already muted! Try /unmute.");
            return true;
        }

        if (Utils.plugin.config.getBoolean("server.disablepunishbroadcasts")) {
            logger.info("Not broadcasting message, disabled in config!");
        } else {
            if (args.length == 1) {
                Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " has just muted " + player.getName());
            }
            if (args.length > 1) {
                Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " has just muted " + player.getName() + "for " + reason);
            }
        }
        if (!plugin.mutedusers.contains(player.getName())) {
            if (args.length == 1) {
                player.sendMessage(ChatColor.RED + "You were muted by " + sender.getName());
            }
            if (args.length > 1) {
                player.sendMessage(ChatColor.RED + "You were muted by " + sender.getName() + " for " + reason);
            }

            plugin.mutedusers.set(player.getName(), player.getName());
            plugin.mutedusers.set(player.getName() + ".muted-by", sender.getName());
            if (args.length > 1) {
                plugin.mutedusers.set(player.getName() + ".reason", reason);
            }
            plugin.mutedusers.save();
        }
        return true;
    }
}
