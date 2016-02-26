package me.adaptified.utils.command;

import me.adaptified.utils.ConfigEntries;
import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.PLAYER, permission = "utils.apply")
public class Command_apply extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        final String canapply = Utils.plugin.config.getString("canapply");

        if (canapply.equalsIgnoreCase("no")) {
            sender.sendMessage(ChatColor.RED + "I apologize, it seems that this server has applying disabled!");
        }

        sender.sendMessage(ChatColor.RED + "So, you wan't to be a staff member, ayy?");
        sender.sendMessage(ChatColor.RED + "Read more information at " + plugin.getConfig().getString("website") + ".");
        return true;
    }

}
