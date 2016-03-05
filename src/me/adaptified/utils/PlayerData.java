package me.adaptified.utils;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class PlayerData {

    public static final Map<String, PlayerData> UserInfo = new HashMap<String, PlayerData>();

    private static Player player;
    public static boolean isMuted;

    private PlayerData(Player player) {
        this.player = player;

    }

    public static boolean gatherInfo(Player player) {
        return UserInfo.containsKey(player);
    }

    public static PlayerData SyncInfo(Player player) {
        synchronized (UserInfo) {
            return getInfo(player);
        }
    }

    public static void save() {
        PlayerData.gatherInfo(player);
        PlayerData.getInfo(player);
        PlayerData.SyncInfo(player);
    }

    public static PlayerData getInfo(Player player) {
        final String name = player.getName();

        PlayerData info = PlayerData.UserInfo.get(player.getName());

        if (info != null) {
            return info;
        }

        for (PlayerData infoTest : UserInfo.values()) {
            if (infoTest.player.getName().equalsIgnoreCase(player.getName())) {
                info = infoTest;
                break;
            }
        }

        info = new PlayerData(player);
        PlayerData.UserInfo.put(name, info);

        return info;
    }
}
