package me.adaptified.utils.title;

import me.adaptified.utils.Utils;
import me.adaptified.utils.util.AbstractService;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.pravian.aero.config.YamlConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class TitleManager extends AbstractService {

    private final YamlConfig config;
    private final Map<UUID, Title> playerTitles;
    private final Set<Title> permissionTitles;

    public TitleManager(Utils plugin) {
        super(plugin);
        this.config = new YamlConfig(plugin, "titles.yml", true);
        this.playerTitles = new HashMap<>();
        this.permissionTitles = new HashSet<>();
    }

    @Override
    protected void onStart() {
        config.load();

        playerTitles.clear();
        final ConfigurationSection uuidSec = config.getConfigurationSection("uuid");
        if (uuidSec != null) {
            for (String id : uuidSec.getKeys(false)) {
                if (!uuidSec.isConfigurationSection(id)) {
                    logger.warning("Ignoring title '" + id + "'. Invalid format!");
                    continue;
                }

                Title title = new Title(this, id);
                title.loadFrom(uuidSec.getConfigurationSection(id));

                if (!title.isValid()) {
                    logger.warning("Ignoring player UUID title '" + id + "'. Missing values!");
                    continue;
                }

                playerTitles.put(title.getUuid(), title);
            }
        }

        permissionTitles.clear();
        final ConfigurationSection permSec = config.getConfigurationSection("permission");
        if (permSec != null) {
            for (String id : permSec.getKeys(false)) {
                if (!permSec.isConfigurationSection(id)) {
                    logger.warning("Ignoring permission title '" + id + "'. Invalid format!");
                    continue;
                }

                Title title = new Title(this, id);
                title.loadFrom(permSec.getConfigurationSection(id));

                if (!title.isValid()) {
                    logger.warning("Ignoring permission title '" + id + "'. Missing values!");
                    continue;
                }

                permissionTitles.add(title);
            }
        }

    }

    @Override
    protected void onStop() {
        final ConfigurationSection uuidSec = config.createSection("uuid");
        for (Title title : playerTitles.values()) {
            title.saveTo(uuidSec.createSection(title.getId()));
        }

        final ConfigurationSection permSec = config.createSection("permission");
        for (Title title : permissionTitles) {
            title.saveTo(permSec.createSection(title.getId()));
        }

        config.save();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        Title title = playerTitles.get(player.getUniqueId());

        if (title == null) {
            for (Title loopTitle : permissionTitles) {
                if (!player.hasPermission(loopTitle.getPermission())) {
                    continue;
                }

                title = loopTitle;
                break;
            }
        }

        if (title == null) {
            return;
        }

        Bukkit.broadcastMessage(title.getFormattedLogin(player));
    }

    public Set<Title> getPermissionTitles() {
        return Collections.unmodifiableSet(permissionTitles);
    }

    public Map<UUID, Title> getPlayerTitles() {
        return Collections.unmodifiableMap(playerTitles);
    }

    public void removeTitle(Title title) {
        permissionTitles.remove(title);
        while (playerTitles.values().remove(title)) {
        }
    }

    public void setTitle(Player player, Title title) {
        playerTitles.put(player.getUniqueId(), title);
    }

}
