package me.adaptified.utils;

import java.io.File;
import java.util.ArrayList;
import me.adaptified.utils.banning.BanManager;
import me.adaptified.utils.command.Command_kick;
import me.adaptified.utils.listener.SwearListener;
import me.adaptified.utils.listener.UtilsListener;
import me.adaptified.utils.title.TitleManager;
import net.pravian.bukkitlib.BukkitLib;
import net.pravian.bukkitlib.command.BukkitCommandHandler;
import net.pravian.bukkitlib.config.YamlConfig;
import net.pravian.bukkitlib.implementation.BukkitLogger;
import net.pravian.bukkitlib.implementation.BukkitPlugin;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class Utils extends BukkitPlugin {

    public Storage MutedPlayers;

    public static Utils plugin;
    //
    public YamlConfig config;
    public BukkitLogger logger;
    public BukkitCommandHandler<Utils> handler;
    //
    public BanManager bm;
    public TitleManager tm;

    @Override
    public void onLoad() {
        plugin = this;
        //
        this.config = new YamlConfig(plugin, "config.yml", true);
        this.logger = new BukkitLogger(plugin);
        this.handler = new BukkitCommandHandler<>(plugin);
        //
        this.bm = new BanManager(plugin);
        this.tm = new TitleManager(plugin);
    }

    @Override
    public void onEnable() {
        plugin = this;
        BukkitLib.init(plugin);
        //
        String pluginFolder = this.getDataFolder().getAbsolutePath();
        (new File(pluginFolder)).mkdirs();
        this.MutedPlayers = new Storage(new File(pluginFolder + File.separator + "muted-players.yml"));
        this.MutedPlayers.load();
        // Load config
        config.load();

        plugin.getConfig().getString("server.prefix").replaceAll("&", "§");

        //
        // Start services
        bm.start();
        tm.start();

        // Register events
        register(new UtilsListener());
        register(new SwearListener());

        // Setup command handler
        handler.setCommandLocation(Command_kick.class.getPackage());

        logger.info(plugin.getName() + " v" + plugin.getVersion() + " by " + StringUtils.join(getAuthors(), ", ") + " is enabled");
    }

    @Override
    public void onDisable() {

        plugin = this;

        // Unregister events
        HandlerList.unregisterAll(plugin);

        // Something
        this.MutedPlayers.save();

        // Stop services
        bm.stop();
        tm.stop();

        logger.info(plugin.getName() + " v" + plugin.getVersion() + " disabled");
    }

    public static ArrayList<Player> cooldown = new ArrayList<Player>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    return handler.handleCommand (sender, cmd, commandLabel, args);
}
}
