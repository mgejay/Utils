package me.adaptified.utils.command;

import java.util.UUID;
import me.adaptified.utils.Utils;
import me.adaptified.utils.util.Util;
import net.md_5.bungee.api.ChatColor;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.ANY, permission = "utils.unban")
public class Command_unban extends BukkitCommand<Utils> {

    @Override
    protected boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length != 1) {
            return showUsage();
        }

        final OfflinePlayer player = getOfflinePlayer(args[0]);
        final UUID UUID = player.getUniqueId();

        if (!plugin.bm.isIdBanned(player.getName())) {
            msg(ChatColor.RED + "Could not find ban: " + player.getName());
            return true;
        }

        Util.adminMessage(sender, "Unbanning player " + player.getName(), true);

        plugin.bm.unbanId(player.getName());
        plugin.bm.unban(UUID);

        msg(ChatColor.GRAY + "Unbanned player.");

        return true;
    }

}
