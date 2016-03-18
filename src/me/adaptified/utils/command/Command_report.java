package me.adaptified.utils.command;

import me.adaptified.utils.Reports;
import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.report", aliases = "modreq")
public class Command_report extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "/report <player> <reason>\nBe sure to provide some detail and if there are no staff online, use the forums!");
        }

        if (!sender.hasPermission("utils.report")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Can't find player!");
            return true;
        }

        if (sender instanceof Player) {
            sender.sendMessage(ChatColor.RED + "Do not try to report yourself!");
            return true;
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
