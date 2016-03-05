package me.adaptified.utils.command;

import me.adaptified.utils.PlayerData;
import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(source = SourceType.ANY, permission = "utils.unmute")
public class Command_unmute extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "/unmute <player>");
            return true;
        }

        Player player = getPlayer(args[0]);
        if (player == null) {
            sender.sendMessage(ChatColor.RED + "That player is not online!");
            return true;
        }

        PlayerData stats;
        stats = PlayerData.getInfo(player);
        PlayerData.gatherInfo(player);
        PlayerData.SyncInfo(player);

        if (!plugin.MutedPlayers.contains(player)) {
            sender.sendMessage(ChatColor.RED + "That player is not muted! Try /mute.");
            return true;
        }

        if (plugin.MutedPlayers.contains(player)) {

            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " has just unmuted " + player.getName());

            player.sendMessage(ChatColor.RED + "You were unmuted by " + sender.getName());
            plugin.MutedPlayers.remove(player);
            plugin.MutedPlayers.save();
        }
        return true;
    }
}
