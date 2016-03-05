package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.PLAYER, permission = "utils.warn")
public class Command_warn extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length < 1) {
            return showUsage();
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.\nPlease review arguments.");
        }

        String warnReason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Warning: " + player.getName() + " Reason: " + warnReason);

        player.sendMessage(ChatColor.RED + "You have been warned!" + ChatColor.RED + "\nReason: " + ChatColor.YELLOW + warnReason + ChatColor.RED + "\nWarned by ~ " + ChatColor.YELLOW + sender.getName());

        sender.sendMessage(ChatColor.GRAY + "Player successfully warned");

        return true;
    }

}
