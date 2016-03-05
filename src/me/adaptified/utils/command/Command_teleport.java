package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ANY, permission = "utils.teleport")
public class Command_teleport extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = Bukkit.getPlayer(args[0]);
        Player teleporter = (Player) sender;
        if (player == null) {
            teleporter.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        if (args.length < 1) {
            teleporter.sendMessage(ChatColor.RED + "Please provide a player!");
            return true;
        }

        teleporter.teleport(player);
        teleporter.sendMessage(ChatColor.YELLOW + "Teleported to " + player.getName());
        return true;
    }

}
