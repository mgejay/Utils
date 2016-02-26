package me.adaptified.utils.listener;

import static me.adaptified.utils.Utils.plugin;
import me.adaptified.utils.command.Command_mute;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class UtilsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        switch (event.getBlockPlaced().getType()) {

            case TNT: {
                if (!player.hasPermission("utils.tnt")) {
                    event.setCancelled(false);
                } else {
                    player.sendMessage(ChatColor.RED + "I'm sorry, TNT is disabled for players without permission");
                    event.setCancelled(true);
                }

            }
            case STATIONARY_LAVA:
            case LAVA_BUCKET:
            case LAVA: {
                if (!player.hasPermission("utils.lava")) {
                    event.setCancelled(false);
                } else {
                    player.sendMessage(ChatColor.RED + "I'm sorry, This is disabled");
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
            final Player player = event.getPlayer();
            String message = event.getMessage();
            
            // Check for swearing
             // Check for swearing
             String[] arrayOfString;
             int j = (arrayOfString = event.getMessage().split(" ")).length;
             for (int i = 0; i <j; i++)
             {
            //This will allow words to be in any casing as long as the letters remain the same.
            //So for example, "test" would be the same as "TeST"..
            String word = arrayOfString[i];
            if (plugin.getConfig().getStringList("censored_words").contains(word)) {
                event.setCancelled(true);
                //Cancels the message 
                event.getPlayer().sendMessage(ChatColor.RED + "The word " + word + " is not allowed here!");
                //This would not only send a warning to the player, but also point out what word caused this event to occur - so if it was on accident, the player would know what he typed wrong.
            }
        }
    
            
            
            
            // Check for caps
            if (message.length() >= 6)
            {
                int caps = 0;
                for (char c : message.toCharArray())
                {
                    if (Character.isUpperCase(c))
                    {
                        caps++;
                    }
                }
                if (((float) caps / (float) message.length()) > 0.65) //Compute a ratio so that longer sentences can have more caps.
                {
                    player.sendMessage(ChatColor.RED + "Don't use so many caps!");
                    message = message.toLowerCase();
                }
            }


            // Finally, set message
            event.setMessage(message);

    }
  }

    
    

