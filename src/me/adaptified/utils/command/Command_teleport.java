package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.teleport", aliases = "tp")
public class Command_teleport extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!sender.hasPermission("utils.teleport")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Please provide a player!");
            return true;
        }

        Player teleporter = (Player) sender;

        Player player = getPlayer(args[0]);

        if (player == null) {
            teleporter.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        teleporter.teleport(player);
        teleporter.sendMessage(ChatColor.YELLOW + "Teleported to " + player.getName());
        return true;
    }

}
