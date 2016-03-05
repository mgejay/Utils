package me.adaptified.utils.command;

import com.google.common.base.Strings;
import me.adaptified.utils.Utils;
import me.adaptified.utils.banning.Ban;
import me.adaptified.utils.banning.BanType;
import me.adaptified.utils.util.Util;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import net.pravian.bukkitlib.util.IpUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ANY, permission = "utils.unbanip")
public class Command_unbanip extends BukkitCommand<Utils> {

    @Override
    protected boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length < 1) {
            return showUsage();
        }

        String id = args[0];
        String ip = null;

        if (IpUtils.isValidIp(args[0])) {
            ip = args[0];
        } else {
            final OfflinePlayer player = getOfflinePlayer(args[0]);
            if (player != null) {
                ip = IpUtils.toEscapedString(ip);
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

        Util.adminMessage(sender, "Unbanning IP: " + ip + (ip.equals(id) ? "" : " (" + id + ")"), true);

        plugin.bm.unbanId(ip);

        msg(ChatColor.GRAY + "Unbanned IP " + ip + (ip.equals(id) ? "" : " (" + id + ")"));
        return true;
    }

}
