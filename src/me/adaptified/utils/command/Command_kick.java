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

@CommandPermissions(source = SourceType.PLAYER, permission = "utils.kick")
public class Command_kick extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length < 3) {
            return showUsage();
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            sender.sendMessage(ChatColor.RED + "Player not found.\nPlease review arguments.");
        }

        String kickReason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");

        Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " - Kicking: " + player.getName() + " Reason: " + kickReason);

        player.kickPlayer(ChatColor.RED + "You have been kicked\nReason: " + ChatColor.YELLOW + kickReason + ChatColor.RED + "\nKicked by ~ " + ChatColor.YELLOW + sender.getName());

        sender.sendMessage(ChatColor.GRAY + "Player successfully kicked");

        return true;
    }

}
