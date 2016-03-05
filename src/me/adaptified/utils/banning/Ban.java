package me.adaptified.utils.banning;

import me.adaptified.utils.ConfigEntries;
import me.adaptified.utils.Utils;
import me.adaptified.utils.util.ConfigLoadable;
import me.adaptified.utils.util.ConfigSaveable;
import me.adaptified.utils.util.Validatable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import net.pravian.bukkitlib.util.TimeUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Ban implements ConfigLoadable, ConfigSaveable, Validatable {

    @Getter
    private final BanType type;
    @Getter
    private final String id;
    //
    private List<String> ips;
    @Getter
    @Setter
    private UUID uuid = null;
    @Getter
    @Setter
    private String reason = null;
    @Getter
    @Setter
    private String by = null;
    @Getter
    @Setter
    private Date expiryDate = null;

    public Ban(BanType type, String id) {
        this.type = type;
        this.id = id.toLowerCase();
        this.ips = new ArrayList<>();
    }

    public boolean hasIps() {
        return !ips.isEmpty();
    }

    public void addIp(String ip) {
        if (!ips.contains(ip.trim())) {
            this.ips.add(ip.trim());
        }
    }

    public void addIps(List<String> ips) {
        for (String ip : ips) {
            addIp(ip);
        }
    }

    public List<String> getIps() {
        return Collections.unmodifiableList(ips);
    }

    public void clearIps() {
        ips.clear();
    }

    public boolean containsIp(String ip) {
        return ips.contains(ip.trim());
    }

    public void removeIp(String ip) {
        if (containsIp(ip)) {
            ips.remove(ip.trim());
        }
    }

    public String getIp() {
        return (ips.size() > 0 ? ips.get(0) : null);
    }

    public String getTarget() {
        if (type == BanType.IP_BAN && hasIps()) {
            return getIp();
        }
        return id;
    }

    public String getKickMessage() {
        final String appealUrl = Utils.plugin.config.getString(ConfigEntries.BANNING_APPEAL_URL);

        return ChatColor.RED
                + "You" + (type == BanType.UUID_BAN ? " are" : "r IP-Address is") + " banned from this server.\n"
                + (reason == null ? "" : "Reason: " + reason + "\n")
                + (expiryDate == null ? "" : "Expires: " + TimeUtils.parseDate(expiryDate) + "\n")
                + (by == null ? "" : "Banned by: " + by + "\n")
                + (appealUrl.equals("none") ? "" : "You may appeal your ban at " + appealUrl);
    }

    public boolean hasExpiryDate() {
        return expiryDate != null;
    }

    public boolean isExpired() {
        if (expiryDate == null) {
            return false;
        }

        return !expiryDate.after(new Date());
    }

    @Override
    public void loadFrom(ConfigurationSection config) {

        try {
            uuid = UUID.fromString(config.getString("uuid", null));
        } catch (Exception ex) {
            uuid = null;
        }

        ips.clear();
        ips.addAll(config.getStringList("ips"));

        by = config.getString("by", null);
        reason = config.getString("reason", null);
        expiryDate = TimeUtils.parseString(config.getString("expires", null));
    }

    @Override
    public void saveTo(ConfigurationSection config) {
        config.set("uuid", (uuid == null ? null : uuid.toString()));
        config.set("ips", (ips.isEmpty() ? null : ips));
        config.set("by", by);
        config.set("reason", reason);
        config.set("expires", TimeUtils.parseDate(expiryDate));
    }

    @Override
    public boolean isValid() {
        return id != null
                && getTarget() != null
                // See BanType.java
                && ((type == BanType.UUID_BAN && uuid != null)
                || (type == BanType.IP_BAN && uuid == null && hasIps()));
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.ips);
        hash = 61 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ban other = (Ban) obj;
        if (!Objects.equals(this.ips, other.ips)) {
            return false;
        }
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        return true;
    }

}
