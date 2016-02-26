package me.adaptified.utils.banning;

public enum BanType {

    // UUID_BAN - MUST include UUID, MAY include IPs
    // IP_BAN - MUST NOT include UUID, MUST include IPs
    UUID_BAN("Player ban"),
    IP_BAN("IP ban");

    private final String name;

    private BanType(String type) {
        this.name = type;
    }

    @Override
    public String toString() {
        return name;
    }
}
