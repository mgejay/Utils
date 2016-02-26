package me.adaptified.utils.util;

import me.adaptified.utils.Utils;
import java.util.logging.Logger;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public abstract class AbstractService implements Service, Listener {

    protected final Utils plugin;
    protected final Server server;
    protected final BukkitLogger logger;
    //
    protected boolean started;

    protected AbstractService(Utils plugin) {
        this.plugin = plugin;
        this.server = plugin.getServer();
        this.logger = plugin.logger;
        //
        this.started = false;
    }

    @Override
    public final void start() {
        if (started) {
            logger.warning("Tried to start service: " + getClass().getSimpleName() + " twice!");
            return;
        }
        started = true;

        // Start
        onStart();

        // Register events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public final void stop() {
        if (!started) {
            logger.warning("Tried to stop service: " + getClass().getSimpleName() + " twice!");
            return;
        }
        started = false;

        // Unregister events
        HandlerList.unregisterAll(this);

        // Stop
        onStop();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    protected abstract void onStart();

    protected abstract void onStop();
}
