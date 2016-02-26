package me.adaptified.utils.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Util {

    private Util() {
        throw new AssertionError();
    }

    public static void adminMessage(CommandSender sender, String message, boolean isRed) {
        adminMessage(sender.getName(), message, isRed);
    }

    public static void adminMessage(String sender, String message, boolean isRed) {
        Bukkit.broadcastMessage((isRed ? ChatColor.RED : ChatColor.BLUE) + sender + " - " + message);
    }

}
