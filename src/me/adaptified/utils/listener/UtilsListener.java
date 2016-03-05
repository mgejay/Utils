package me.adaptified.utils.listener;

import static me.adaptified.utils.Utils.plugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class UtilsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        switch (event.getBlockPlaced().getType()) {
            case TNT:
                if (!player.hasPermission("utils.tnt")) {
                    event.setCancelled(false);
                } else {
                    player.sendMessage(ChatColor.RED + "I'm sorry, TNT is disabled for players without permission");
                    event.setCancelled(true);
                }
            case STATIONARY_LAVA:
            case LAVA_BUCKET:
            case LAVA:
                if (!player.hasPermission("utils.lava")) {
                    event.setCancelled(false);
                } else {
                    player.sendMessage(ChatColor.RED + "I'm sorry, This is disabled");
                    event.setCancelled(true);
                }
                break;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage().trim();

        if (plugin.MutedPlayers.contains(player)) {
            player.sendMessage(ChatColor.RED + "You are muted! You cannot speak!");
            event.setCancelled(true);
        }

        if (!plugin.MutedPlayers.contains(player)) {
            event.setCancelled(false);
        }

        if (message.length() >= 6) {
            int caps = 0;
            for (char c : message.toCharArray()) {
                if (Character.isUpperCase(c)) {
                    caps++;
                }
            }
            if (caps / message.length() > 0.65D) {
                player.sendMessage(ChatColor.RED + "Don't use so many caps!");
                message = message.toLowerCase();
            }
        }
        event.setMessage(message);
    }
}
