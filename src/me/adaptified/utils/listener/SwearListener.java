package me.adaptified.utils.listener;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static me.adaptified.utils.Utils.plugin;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SwearListener implements Listener {

    private List<String> censored_words = plugin.getConfig().getStringList("censored_words");

    @EventHandler(priority = EventPriority.NORMAL)
    public void onSwear(AsyncPlayerChatEvent event) {
        String[] arrayOfString;
        int j = (arrayOfString = event.getMessage().split(" ")).length;
        for (int i = 0; i < j; i++) {
            String word = arrayOfString[i];
            if (plugin.getConfig().getBoolean("server.filter")) {
                for (String regex : censored_words) {
                    Pattern pattern = Pattern.compile("(?i)" + regex);
                    Matcher matcher = pattern.matcher(event.getMessage());
                    if (matcher.find()) {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage(ChatColor.RED + "That message contains a forbidden word!");
                        return;
                    }
                }
            } else {
                return;
            }
        }
        //This would not only send a warning to the player, but also point out what word caused this event to occur - so if it was on accident, the player would know what he typed wrong.
    }

}
