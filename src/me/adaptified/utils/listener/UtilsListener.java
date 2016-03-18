package me.adaptified.utils.listener;

import static me.adaptified.utils.Utils.plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class UtilsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        switch (event.getBlockPlaced().getType()) {
            case TNT:
                if (!player.hasPermission("utils.tnt")) {
                    player.sendMessage(ChatColor.RED + "I'm sorry, TNT is disabled for players without permission");
                    event.setCancelled(true);
                } else {
                    event.setCancelled(false);
                }
            case STATIONARY_LAVA:
            case LAVA_BUCKET:
            case LAVA:
                if (!player.hasPermission("utils.lava")) {
                    player.sendMessage(ChatColor.RED + "I'm sorry, This is disabled");
                    event.setCancelled(true);
                } else {
                    event.setCancelled(false);
                }
                break;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) {
            player.sendMessage(ChatColor.BLUE + "Welcome back, " + player.getName() + "!");
        }

        if (!player.hasPlayedBefore()) {
            Bukkit.broadcastMessage(ChatColor.BLUE + "Welcome to the server, " + player.getName() + "!");
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().trim();

        if (plugin.mutedusers.contains(player.getName())) {

            if (plugin.mutedusers.contains(player.getName() + ".reason")) {
                player.sendMessage(ChatColor.RED + "You cannot speak, you were muted by " + plugin.mutedusers.getString(player.getName() + ".muted-by") + "\nReason: " + ChatColor.YELLOW + plugin.mutedusers.getString(player.getName() + ".reason"));
            }
            if (!plugin.mutedusers.contains(player.getName() + ".reason")) {
                player.sendMessage(ChatColor.RED + "You cannot speak, you were muted by " + plugin.mutedusers.getString(player.getName() + ".muted-by"));
            }

            event.setCancelled(true);
        }

        if (!plugin.mutedusers.contains(player.getName())) {
            event.setCancelled(false);
        }

        if (message.length() >= 6) {
            int caps = 0;
            for (char c : message.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    caps++;
                }
            }
            if (caps / message.length() > 0.65) {
                player.sendMessage(ChatColor.RED + "Don't use so many caps!");
                message = message.toLowerCase();
            }
        }
        event.setMessage(message);
    }
}
