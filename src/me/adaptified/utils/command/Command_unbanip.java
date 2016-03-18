package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import me.adaptified.utils.util.Util;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import net.pravian.aero.util.Ips;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.unbanip")
public class Command_unbanip extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1) {
            return showUsage();
        }

        if (!sender.hasPermission("utils.unbanip")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        String id = args[0];
        String ip = null;

        if (Ips.isValidIp(args[0])) {
            ip = args[0];
        } else {
            final OfflinePlayer player = getOfflinePlayer(args[0]);
            if (player != null) {
                ip = Ips.toEscapedString(ip);
                id = player.getName();
            }
        }
        // TODO: Persistent player IP_BAN matching?

        if (ip == null) {
            msg(ChatColor.RED + "Could not find IP for player: " + id);
            return true;
        }

        if (!plugin.bm.isBanned(ip)) {
            msg(ChatColor.RED + "That IP is not banned!");
            return true;
        }
        if (Utils.plugin.config.getBoolean("server.disablepunishbroadcasts")) {
            logger.info("Not broadcasting message, disabled in config!");
        } else {
            Util.adminMessage(sender, "Unbanning IP: " + ip + (ip.equals(id) ? "" : " (" + id + ")"), true);
        }

        plugin.bm.unbanId(ip);

        msg(ChatColor.GRAY + "Unbanned IP " + ip + (ip.equals(id) ? "" : " (" + id + ")"));
        return true;
    }

}
