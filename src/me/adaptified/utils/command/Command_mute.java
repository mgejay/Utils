package me.adaptified.utils.command;

import java.util.ArrayList;
import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_mute extends BukkitCommand<Utils> {

    public static ArrayList<String> muted = new ArrayList<String>();

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        Player player = getPlayer(args[0]);


        if (!muted.contains(player.getName())) {

            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " has just muted " + player.getName());
            player.sendMessage(ChatColor.AQUA + "You were muted by " + sender.getName() + ".");

            muted.add(player.getName());
            return true;
        }

        if (muted.contains(player.getName())) {
            muted.remove(player.getName());
            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + " has just unmuted " + player.getName());
        }
        return true;
    }
}
