package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.unmute")
public class Command_unmute extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/unmute <player>");
            return true;
        }

        if (!sender.hasPermission("utils.unmute")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        if (!plugin.mutedusers.contains(player.getName())) {
            sender.sendMessage(ChatColor.RED + "That player is already muted! Try /unmute.");
            return true;
        }

        if (Utils.plugin.config.getBoolean("server.disablepunishbroadcasts")) {
            logger.info("Not broadcasting message, disabled in config!");
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " has just unmuted " + player.getName());
        }

        if (plugin.mutedusers.contains(player.getName())) {
            player.sendMessage(ChatColor.RED + "You were unmuted by " + sender.getName());
            plugin.mutedusers.set(player.getName(), null);
            plugin.mutedusers.save();
        }
        return true;
    }
}
