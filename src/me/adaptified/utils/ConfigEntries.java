package me.adaptified.utils;

import net.pravian.bukkitlib.config.PathContainer;

public enum ConfigEntries implements PathContainer {

    SERVER_NAME("server.name"),
    SERVER_OWNER("server.owner"),
    SERVER_WEBSITE("server.website"),
    //
    BANNING_APPEAL_URL("banning.appeal_url"),
    //
    ADMINS("admins");
    //
    private final String key;

    //
    private ConfigEntries(String key) {
        this.key = key;
    }

    @Override
    public String getPath() {
        return key;
    }

}
