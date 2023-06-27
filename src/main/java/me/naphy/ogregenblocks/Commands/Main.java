package me.naphy.ogregenblocks.Commands;

import me.naphy.ogregenblocks.OGRegenBlocks;
import me.naphy.ogregenblocks.RegionLoader;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Main implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7List of available commands: \n\n\n &b/ogregenblocks help &7- Displays the available commands provided by this plugin. \n &b/ogregenblocks toggle <start/stop> &7- Turns the plugin on/off. \n &b/ogregenblocks reload &7- Reloads the plugin, events and configuration.";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ogregenblocks")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            else if (args[0].equalsIgnoreCase("toggle")) {
                if (args.length == 1) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Please provide a valid argument (&con&7/&coff&7)!"));
                    return true;
                }
                if (args[1].equalsIgnoreCase("on")) {
                    if (OGRegenBlocks.plugin.pluginSwitch) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7The plugin is already &aenabled&7!"));
                        return true;
                    }
                    else {
                        OGRegenBlocks.plugin.pluginSwitch = true;
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7The plugin has been &aenabled &7successfully!"));
                        return true;
                    }
                }
                else if (args[1].equalsIgnoreCase("off")) {
                    if (!OGRegenBlocks.plugin.pluginSwitch) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7The plugin is already &cdisabled&7!"));
                        return true;
                    }
                    else {
                        OGRegenBlocks.plugin.pluginSwitch = false;
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7The plugin has been &cdisabled &7successfully!"));
                        return true;
                    }
                }
                else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Please provide a valid argument (&con&7/&coff&7)!"));
                    return true;
                }
            }
            else if (args[0].equalsIgnoreCase("reload")) {
                OGRegenBlocks.plugin.saveDefaultConfig();
                OGRegenBlocks.plugin.reloadConfig();
                OGRegenBlocks.plugin.pluginSwitch = OGRegenBlocks.plugin.getConfig().getBoolean("Enabled");
                RegionLoader.init();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7The plugin has been reloaded &asuccessfully&7!"));
                return true;
            }
            else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
        }
        return true;
    }
}
