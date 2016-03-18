package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import me.adaptified.utils.banning.Ban;
import me.adaptified.utils.banning.BanType;
import me.adaptified.utils.util.Util;
import java.util.Arrays;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import net.pravian.aero.util.Ips;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.ban")
public class Command_ban extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final boolean override = args.length >= 1 && args[0].equals("-o");
        if (override) {
            args = Arrays.copyOfRange(args, 1, args.length); // Shift one
        }

        if (!sender.hasPermission("utils.ban")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "/ban [-o] <player> <reason>");
            return true;
        }

        final OfflinePlayer player = getOfflinePlayer(args[0]);

        if (player == null && !override) {
            msg(ChatColor.RED + "Could not find player. Use -o to override.");
            return true;
        }

        if (player != null && plugin.bm.isBanned(player.getUniqueId())) {
            msg(ChatColor.RED + "That player is already banned!");
            return true;
        }

        final String id = player == null ? args[0] : player.getName();
        final String reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        if (Utils.plugin.config.getBoolean("server.disablepunishbroadcasts")) {
            logger.info("Not broadcasting message, disabled in config!");
        } else {
            Util.adminMessage(sender, "Banning " + id + " for " + reason, true);
        }

        final Ban ban = new Ban(BanType.UUID_BAN, id);
        ban.setBy(sender.getName());
        ban.setReason(reason);
        ban.setUuid(player == null ? Bukkit.getOfflinePlayer(id).getUniqueId() : player.getUniqueId());
        if (player != null && player.isOnline()) {
            ban.addIp(Ips.getIp(player.getPlayer()));
        }

        if (!plugin.bm.addBan(ban)) {
            msg(ChatColor.RED + "Could not ban player! (Is the player already banned?)");
        }

        if (player != null && player.isOnline()) {
            player.getPlayer().kickPlayer(ban.getKickMessage());
        }

        msg(ChatColor.GRAY + "Banned player " + id);
        return true;
    }

}
