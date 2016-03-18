package me.adaptified.utils;

import me.adaptified.utils.banning.BanManager;
import me.adaptified.utils.command.Command_ban;
import me.adaptified.utils.listener.SwearListener;
import me.adaptified.utils.listener.UtilsListener;
import me.adaptified.utils.title.TitleManager;
import net.pravian.aero.command.handler.AeroCommandHandler;
import net.pravian.aero.command.handler.SimpleCommandHandler;
import net.pravian.aero.config.YamlConfig;
import net.pravian.aero.plugin.AeroPlugin;
import net.pravian.aero.util.Loggers;
import org.bukkit.plugin.PluginManager;

public class Utils extends AeroPlugin<Utils> {

    public static Utils plugin;

    public static AeroCommandHandler handler;
    public Loggers logger;
    public YamlConfig config;
    public YamlConfig mutedusers;

    public BanManager bm;
    public TitleManager tm;

    @Override
    public void load() {
        Utils.plugin = this;

        Loggers.info("[Utils] Loading!");
        this.config = new YamlConfig(plugin, "config.yml", true);
        this.mutedusers = new YamlConfig(plugin, "muted-users.yml", true);
        this.bm = new BanManager(plugin);
        this.tm = new TitleManager(plugin);
    }

    @Override
    public void enable() {

        Utils.plugin = this;

        // Load Configs
        config.load();
        mutedusers.load();

        // Register command class 
        handler = new SimpleCommandHandler(plugin);
        handler.setCommandClassPrefix("Command_");
        handler.loadFrom(Command_ban.class.getPackage());
        handler.registerAll(handler.getCommandClassPrefix(), true);

        // Register Listeners
        final PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new UtilsListener(), plugin);
        pm.registerEvents(new SwearListener(), plugin);

        Loggers.info("[Utils] Enabling v1.1 by Adaptified and Prozza!");
    }

    @Override
    public void disable() {

        Utils.plugin = this;

        // Stop services
        bm.stop();
        tm.stop();

        // Save
        Loggers.info("[Utils] Disabling!");
    }

}
