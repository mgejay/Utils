package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.aero.command.CommandOptions;
import net.pravian.aero.command.SimpleCommand;
import net.pravian.aero.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandOptions(source = SourceType.PLAYER, permission = "utils.notes", aliases = "notes")
public class Command_notesforstaff extends SimpleCommand<Utils> {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("utils.notes")) {
            sender.sendMessage(ChatColor.RED + "Utils -> No permission!");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + "Here are some notes from us, Adaptified and Prozza, the plugin creators, to you.");
        sender.sendMessage(ChatColor.GREEN + "These will help you.");
        sender.sendMessage(ChatColor.GREEN + "TNT and LAVA are blocked for players without the permission utils.itemname, like utils.tnt or utils.lava");
        sender.sendMessage(ChatColor.GREEN + "All the commands and perms will be on the BukkitDev post of this plugin, but pretty much all the perms are utils.<commandname>");
        sender.sendMessage(ChatColor.GREEN + "This plugin supports bans(IP banning supported), kicks, mutes and warns, so no need for essentials local banning or warning plugins!! :D");
        sender.sendMessage(ChatColor.GREEN + "Before using this plugin, please set it up with the config, this plugin is very configurable.");
        sender.sendMessage(ChatColor.GREEN + "I hope you enjoy our plugin and have a great day! See ya later!");
        sender.sendMessage(ChatColor.RESET + "*Leaves*");
        sender.sendMessage(ChatColor.RED + "Now that you read these notes, you should be good!");
        return true;
    }

}
