package me.adaptified.utils.command;

import java.util.UUID;
import me.adaptified.utils.Utils;
import me.adaptified.utils.util.Util;
import net.md_5.bungee.api.ChatColor;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.unban")
public class Command_unban extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1) {
            return showUsage();
        }

        if (!sender.hasPermission("utils.unban")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        final OfflinePlayer player = getOfflinePlayer(args[0]);
        final UUID UUID = player.getUniqueId();

        if (!plugin.bm.isIdBanned(player.getName())) {
            msg(ChatColor.RED + "Could not find ban: " + player.getName());
            return true;
        }

        if (Utils.plugin.config.getBoolean("server.disablepunishbroadcasts")) {
            logger.info("Not broadcasting message, disabled in config!");
        } else {
            Util.adminMessage(sender, "Unbanning player " + player.getName(), true);
        }

        plugin.bm.unbanId(player.getName());
        plugin.bm.unban(UUID);

        msg(ChatColor.GRAY + "Unbanned player.");

        return true;
    }

}
