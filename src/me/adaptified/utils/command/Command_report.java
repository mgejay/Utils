package me.adaptified.utils.command;

import me.adaptified.utils.Reports;
import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission = "utils.report")
public class Command_report extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length < 2) {
            return false;
        }

        Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "That player does not exist!");
            return true;
        }

        if (sender instanceof Player) {
            if (player.equals(sender)) {
                sender.sendMessage(ChatColor.RED + "Do not try to report yourself!");
                return true;
            }
        }

        if (player.hasPermission("utils.noreport")) {
            sender.sendMessage(ChatColor.RED + "You can not report this player.");
            return true;
        }

        String report = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        Reports.Reporting((Player) sender, player, report);

        sender.sendMessage(ChatColor.GREEN + "Thanks for the report, it should be reviewed shortly.");

        return true;
    }
}
