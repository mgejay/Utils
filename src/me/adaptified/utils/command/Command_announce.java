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

@CommandPermissions(source = SourceType.ANY, permission = "utils.announce")
public class Command_announce extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        final String message = StringUtils.join(ArrayUtils.subarray(args, 0, args.length), " ");

        if (args[1].equals("silent")) {
            Bukkit.broadcastMessage(plugin.getConfig().getString("prefix").replaceAll("&", "§") + ChatColor.WHITE + message);
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "We have an announcement from " + sender.getName() + "!");
        }

        Bukkit.broadcastMessage(plugin.getConfig().getString("prefix").replaceAll("&", "§") + ChatColor.WHITE + message);
        return true;
    }

}
