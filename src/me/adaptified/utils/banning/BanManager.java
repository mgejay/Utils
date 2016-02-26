package me.adaptified.utils.banning;

import me.adaptified.utils.util.AbstractService;
import me.adaptified.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.util.FileUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanManager extends AbstractService {

    private final YamlConfig config;
    private final Set<Ban> rawBans;
    private final Map<UUID, Ban> uuidBans;
    private final Map<String, Ban> ipBans;

    public BanManager(Utils plugin) {
        super(plugin);

        this.config = new YamlConfig(plugin, FileUtils.getPluginFile(plugin, "bans.yml"), false);
        this.rawBans = new HashSet<>();
        this.uuidBans = new HashMap<>();
        this.ipBans = new HashMap<>();
    }

    @Override
    protected void onStart() {
        loadAll();

        logger.info("Loaded " + rawBans.size() + " player bans (" + uuidBans.size() + " UUIDs, " + ipBans.size() + " IPs)");
    }

    @Override
    protected void onStop() {
        saveAll();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Ban ban = uuidBans.get(event.getPlayer().getUniqueId());
        if (ban != null) {
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, ban.getKickMessage());
            return;
        }

        ban = ipBans.get(event.getRealAddress().getHostAddress().trim());
        if (ban != null) {
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, ban.getKickMessage());
        }
    }

    public boolean isBanned(UUID uuid) {
        return uuidBans.containsKey(uuid);
    }

    public boolean isBanned(String ip) {
        return ipBans.containsKey(ip);
    }

    public Ban getBan(String id) {
        id = id.toLowerCase();
        for (Ban ban : rawBans) {
            if (ban.getId().equals(id)) {
                return ban;
            }
        }
        return null;
    }

    public boolean isIdBanned(String id) {
        return getBan(id) != null;
    }

    public boolean unbanId(String id) {

        if (getBan(id) == null) {
            return false;
        }

        Set<Ban> bans = new HashSet<>(rawBans);
        for (Ban ban : bans) {
            if (!ban.getId().equals(id)) {
                continue;
            }

            final List<String> ips = new ArrayList<>(ban.getIps());
            for (String ip : ips) {
                unban(ip, false);
            }

            if (ban.getType() == BanType.UUID_BAN) {
                unban(ban.getUuid(), false);
            }
        }

        reparseBans();
        saveAll();
        return true;
    }

    public boolean addBan(Ban ban) {
        return addBan(ban, true);
    }

    private boolean addBan(Ban ban, boolean save) {
        if (!ban.isValid()) {
            logger.warning("Not banning UUID for ban '" + ban.getId() + "' Ban is not valid!");
            return false;
        }

        if (!rawBans.add(ban)) {
            return false;
        }

        if (save) {
            parseBan(ban);
            saveAll();
        }
        return true;
    }

    public boolean unban(UUID uuid) {
        return unban(uuid, true);
    }

    public boolean unban(UUID uuid, boolean save) {
        Iterator<Ban> bans = rawBans.iterator();
        while (bans.hasNext()) {
            final Ban ban = bans.next();
            if (ban.getType() != BanType.UUID_BAN) {
                continue;
            }

            if (!ban.getUuid().equals(uuid)) {
                continue;
            }

            // Unban uuid ban
            removeBan(ban, false);

            // Unban all IP_BAN bans that may match
            final List<String> ips = new ArrayList<>(ban.getIps());
            for (String ip : ips) {
                unban(ip, false);
            }

            if (save) {
                reparseBans();
                saveAll();
            }
            return true;
        }

        return false;
    }

    public boolean unban(String ip) {
        return unban(ip, true);
    }

    private boolean unban(String ip, boolean save) {
        Iterator<Ban> bans = rawBans.iterator();
        while (bans.hasNext()) {
            final Ban ban = bans.next();

            if (!ban.getIps().contains(ip)) {
                continue;
            }

            ban.clearIps();
            removeBan(ban, false);
        }

        if (save) {
            reparseBans();
            saveAll();
        }
        return false;
    }

    public boolean removeBan(Ban ban) {
        return removeBan(ban, true);
    }

    private boolean removeBan(Ban ban, boolean save) {
        if (!rawBans.remove(ban)) {
            return false;
        }

        if (save) {
            reparseBans();
            saveAll();
        }
        return true;
    }

    private void loadAll() {
        if (!config.exists()) {
            return;
        }

        config.load();
        rawBans.clear();

        // UUID_BAN bans
        ConfigurationSection uuidSection = config.getConfigurationSection("uuids");
        if (uuidSection != null) {

            for (String id : uuidSection.getKeys(false)) {
                if (!uuidSection.isConfigurationSection(id)) {
                    logger.warning("Not loading UUID ban '" + id + "'. Section is not a configuration section!");
                    continue;
                }

                final Ban ban = new Ban(BanType.UUID_BAN, id.toLowerCase());
                ban.loadFrom(uuidSection.getConfigurationSection(id));

                if (!ban.isValid()) {
                    logger.warning("Not loading UUID ban '" + id + "'. Ban is not valid!");
                    continue;
                }

                rawBans.add(ban);
            }
        }

        // UUID_BAN bans
        ConfigurationSection section = config.getConfigurationSection("ips");
        if (section != null) {

            for (String id : section.getKeys(false)) {
                if (!section.isConfigurationSection(id)) {
                    logger.warning("Not loading IP ban '" + id + "'. Section is not a configuration section!");
                    continue;
                }

                final Ban ban = new Ban(BanType.IP_BAN, id.toLowerCase());
                ban.loadFrom(section.getConfigurationSection(id));

                if (!ban.isValid()) {
                    logger.warning("Not loading IP ban '" + id + "'. Ban is not valid!");
                    continue;
                }

                rawBans.add(ban);
            }
        }

        // Parses rawBans into ipBans and uuidBans
        reparseBans();
    }

    private void saveAll() {
        ConfigurationSection uuidSection = config.createSection("uuids");
        ConfigurationSection ipSection = config.createSection("ips");

        for (Ban ban : rawBans) {
            if (!ban.isValid()) {
                continue;
            }

            if (ban.getType() == BanType.UUID_BAN) {
                ban.saveTo(uuidSection.createSection(ban.getId()));
            } else if (ban.getType() == BanType.IP_BAN) {
                ban.saveTo(ipSection.createSection(ban.getId()));
            }
        }

        config.save();
    }

    private void reparseBans() {
        ipBans.clear();
        uuidBans.clear();

        Iterator<Ban> bans = rawBans.iterator();
        while (bans.hasNext()) {
            parseBan(bans.next());
        }
    }

    private void parseBan(Ban ban) {
        if (!ban.isValid()) {
            return;
        }

        // All bans may include IPs
        for (String ip : ban.getIps()) {
            ipBans.put(ip, ban);
        }

        if (ban.getType() == BanType.UUID_BAN) {
            uuidBans.put(ban.getUuid(), ban);
        }
    }

}
