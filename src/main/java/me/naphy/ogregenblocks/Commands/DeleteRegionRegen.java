package me.naphy.ogregenblocks.Commands;

import me.naphy.ogregenblocks.OGRegenBlocks;
import me.naphy.ogregenblocks.RegionLoader;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteRegionRegen implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7Invalid use of the command. Please use: &c/deleteregionregen <codename>&7!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("deleteregionregen")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (args.length != 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Regions").getKeys(false)) {
                if (args[0].equals(i)) {
                    OGRegenBlocks.plugin.getConfig().set("Regions." + i, null);
                    OGRegenBlocks.plugin.saveConfig();
                    for (RegionLoader.RegionInfo region : RegionLoader.regions) {
                        if (region.regionName.equals(i)) {
                            RegionLoader.regions.remove(region);
                            break;
                        }
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Region removed &asuccessfully&7!"));
                    return true;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7We couldn't find a region with that codename!"));
            return true;
        }
        return true;
    }
}
