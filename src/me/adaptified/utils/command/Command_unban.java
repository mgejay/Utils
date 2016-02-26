package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import me.adaptified.utils.util.Util;
import net.md_5.bungee.api.ChatColor;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.ANY, permission = "utils.unban")
public class Command_unban extends BukkitCommand<Utils> {

    @Override
    protected boolean run(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (args.length != 1) {
            return showUsage();
        }

        final String id = args[0];

        if (!plugin.bm.isIdBanned(id)) {
            msg(ChatColor.RED + "Could not find ban: " + id);
            return true;
        }

        Util.adminMessage(sender, "Unbanning player " + id, true);

        plugin.bm.unbanId(id);

        msg(ChatColor.GRAY + "Unbanned player.");

        return true;
    }

}
