package me.adaptified.utils.title;

import me.adaptified.utils.util.ConfigLoadable;
import me.adaptified.utils.util.ConfigSaveable;
import me.adaptified.utils.util.Validatable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import net.pravian.aero.util.ChatUtils;
import net.pravian.aero.util.Loggers;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

public class Title implements ConfigLoadable, ConfigSaveable, Validatable {

    @Getter
    public final String id;
    public final Loggers logger;
    //
    @Getter
    @Setter
    public String login;
    @Getter
    @Setter
    public UUID uuid; // Either uuid
    @Getter
    @Setter
    public String permission; // Or permission

    public Title(TitleManager tm, String id) {
        this.id = (id == null ? null : id.toLowerCase());
        this.logger = tm.getLogger();
    }

    public boolean hasPermission() {
        return permission != null;
    }

    public boolean hasUniqueId() {
        return uuid != null;
    }

    public String getFormattedLogin(CommandSender sender) {
        return ChatUtils.colorize(login.replace("<player>", sender.getName()));
    }

    @Override
    public void loadFrom(ConfigurationSection config) {
        // Login
        login = config.getString("login", null);
        if (login == null) {
            logger.warning("Ignoring title '" + id + "'. Missing login!");
            return;
        }

        // UUID
        final String uuidString = config.getString("uuid", null);
        if (uuidString != null) {

            try {
                uuid = UUID.fromString(uuidString);
            } catch (IllegalArgumentException ex) {
                logger.warning("Ignoring title '" + id + "'. Invalid UUID format!");
                return;
            }
        }

        // Permission
        permission = config.getString("permission", null);
        if (uuid == null && permission == null) {
            logger.warning("Ignoring title '" + id + "'. Missing UUID or permission!");
        }
    }

    @Override
    public void saveTo(ConfigurationSection config) {
        config.set("login", login);
        config.set("uuid", uuid == null ? null : uuid.toString());
        config.set("permission", permission);
    }

    @Override
    public boolean isValid() {
        return id != null
                && login != null
                && !login.isEmpty()
                && (hasPermission() || hasUniqueId())
                && !(hasPermission() && hasUniqueId());
    }

}
