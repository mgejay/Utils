package me.adaptified.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Reports {

    public static void Reporting(Player reporter, Player reported, String report) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("utils.reports")) {
                player.sendMessage(ChatColor.RED + "[Report Center] " + ChatColor.WHITE + reporter.getName() + " has reported " + reported.getName() + " for " + report);

                if (player == null) {
                    reporter.sendMessage(ChatColor.RED + "There are no staff online!");
                }
            }
        }
    }

}
