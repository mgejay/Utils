package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import me.adaptified.utils.banning.Ban;
import me.adaptified.utils.banning.BanType;
import me.adaptified.utils.util.Util;
import java.util.Arrays;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.IpUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.ANY, permission = "utils.ban")
public class Command_ban extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        final boolean override = args.length >= 1 && args[0].equals("-o");
        if (override) {
            args = Arrays.copyOfRange(args, 1, args.length); // Shift one
        }

        if (args.length < 2) {
            return showUsage();
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

        Util.adminMessage(sender, "Banning " + id + " for " + reason, true);

        final Ban ban = new Ban(BanType.UUID_BAN, id);
        ban.setBy(sender.getName());
        ban.setReason(reason);
        ban.setUuid(player == null ? Bukkit.getOfflinePlayer(id).getUniqueId() : player.getUniqueId());
        if (player != null && player.isOnline()) {
            ban.addIp(IpUtils.getIp(player.getPlayer()));
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
