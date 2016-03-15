package me.adaptified.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatListen {

    public static void Chat(CommandSender sender, String message) {
        String name = sender.getName();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("utils.staffchat.see")) {
                player.sendMessage(ChatColor.RED + "[" + ChatColor.WHITE + "Staff Chat" + ChatColor.RED + "] " + ChatColor.WHITE + name + ": " + ChatColor.RED + message);
            }
        }
    }
}
