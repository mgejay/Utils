package me.adaptified.utils.command;

import me.adaptified.utils.Utils;
import net.pravian.bukkitlib.command.BukkitCommand;
import net.pravian.bukkitlib.command.CommandPermissions;
import net.pravian.bukkitlib.command.SourceType;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@CommandPermissions(source = SourceType.ANY, permission = "utils.notesforstaff")
public class Command_notesforstaff extends BukkitCommand<Utils> {

    @Override
    public boolean run(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage(ChatColor.GREEN + "Here are some notes from me, ScuphGamingUK, the plugin creator, to you.");
        sender.sendMessage(ChatColor.GREEN + "These will help you.");
        sender.sendMessage(ChatColor.GREEN + "TNT and LAVA are blocked for players without the permission utils.itemname, like utils.tnt or utils.lava");
        sender.sendMessage(ChatColor.GREEN + "All the commands and perms will be on the BukkitDev post of this plugin, but pretty much all the perms are utils.<commandname>");
        sender.sendMessage(ChatColor.GREEN + "This plugin supports bans, kicks and warns, so no need for essentials local banning or warning plugins!! :D");
        sender.sendMessage(ChatColor.GREEN + "Before using this plugin, please set it up with the config, this plugin is very configurable.");
        sender.sendMessage(ChatColor.GREEN + "I hope you enjoy my plugin and have a great day! Chaosiity, out!");
        sender.sendMessage(ChatColor.RESET + "*Leaves*");
        sender.sendMessage(ChatColor.RED + "Now that you read these notes, you should be good!");
        return true;
    }

}
