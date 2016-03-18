package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import me.adaptified.utils.banning.Ban;
import me.adaptified.utils.banning.BanType;
import me.adaptified.utils.util.Util;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import net.pravian.aero.util.Ips;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.banip")
public class Command_banip extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            return showUsage();
        }

        if (!sender.hasPermission("utils.banip")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        String id = args[0];
        String ip = null;

        if (Ips.isValidIp(args[0])) {
            ip = args[0];
        } else {
            final Player player = getPlayer(args[0]);
            if (player != null) {
                ip = Ips.getIp(player);
                id = player.getName();
            }
        }
        // TODO: Persistent player IP_BAN matching?

        if (ip == null) {
            msg(ChatColor.RED + "Could not find IP for player: " + id);
            return true;
        }

        if (plugin.bm.isBanned(ip)) {
            msg(ChatColor.RED + "That player is already banned!");
            return true;
        }

        final String reason = StringUtils.join(args, " ", 1, args.length);

        if (Utils.plugin.config.getBoolean("server.disablepunishbroadcasts")) {
            logger.info("Not broadcasting message, disabled in config!");
        } else {
            Util.adminMessage(sender, "Banning IP: " + ip + (ip.equals(id) ? "" : " (" + id + ")") + " for " + reason, true);
        }

        final Ban ban = new Ban(BanType.IP_BAN, id);
        ban.setReason(reason);
        ban.setBy(sender.getName());
        ban.addIp(ip);

        if (!plugin.bm.addBan(ban)) {
            msg(ChatColor.RED + "Could not ban player! (Is the player already banned?)");
        }

        for (Player player : server.getOnlinePlayers()) {
            if (Ips.getIp(player).equals(ip)) {
                player.kickPlayer(ban.getKickMessage());
            }
        }

        msg(ChatColor.GRAY + "Banned player IP " + ip + (ip.equals(id) ? "" : " (" + id + ")"));
        return true;
    }

}
