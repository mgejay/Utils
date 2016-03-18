package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import me.adaptified.utils.title.Title;
import java.util.Arrays;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.title")
public class Command_title extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length < 1) {
            return showUsage();
        }

        if (!sender.hasPermission("utils.title")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        Player target = playerSender;
        if (!args[0].equals("set") && !args[0].equals("remove")) {
            target = getPlayer(args[0]);
            if (target == null) {
                msg(ChatColor.RED + "Could not find player " + args[0]);
                return true;
            }

            if (!sender.hasPermission("utils.set.others")) {
                return noPerms();
            }

            args = Arrays.copyOfRange(args, 1, args.length); // Shift 1
        }

        // Remove
        if (args[0].equals("remove")) {
            Title title = plugin.tm.getPlayerTitles().get(target.getUniqueId());
            if (title == null) {
                msg(ChatColor.RED + "No login title is set" + (playerSender.equals(target) ? "." : " for that player."));
                return true;
            }

            plugin.tm.removeTitle(title);
            msg(ChatColor.GREEN + "Login title removed!");

            if (!playerSender.equals(target)) {
                msg(target, ChatColor.GREEN + playerSender.getName() + " removed your login title.");
            }

            return true;
        }

        // Set
        if (!args[0].equals("set") || args.length < 2) {
            return showUsage();
        }

        // Obtain create Title
        Title title = plugin.tm.getPlayerTitles().get(target.getUniqueId());
        if (title == null) {
            title = new Title(plugin.tm, target.getName());
        }

        // Update login
        final String login = StringUtils.join(args, " ", 1, args.length);
        title.setLogin(login);

        // Ensure title is set
        plugin.tm.setTitle(target, title);

        msg(ChatColor.GREEN + "Login title set to: " + title.getFormattedLogin(sender));

        if (!playerSender.equals(target)) {
            msg(target, ChatColor.GREEN + playerSender.getName() + " set your login title to: " + title);
        }
        return true;
    }

}
