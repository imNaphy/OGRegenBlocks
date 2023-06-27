package me.naphy.ogregenblocks.Commands;

import me.naphy.ogregenblocks.OGRegenBlocks;
import me.naphy.ogregenblocks.RegionLoader;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteBlockRegen implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7Invalid use of the command. Please use: &c/deleteblockregen <codename>&7!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("deleteblockregen")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (args.length != 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Singular blocks").getKeys(false)) {
                if (args[0].equals(i)) {
                    OGRegenBlocks.plugin.getConfig().set("Singular blocks." + i, null);
                    OGRegenBlocks.plugin.saveConfig();
                    for (RegionLoader.BlockInfo block : RegionLoader.blocks) {
                        if (block.blockName.equals(i)) {
                            RegionLoader.blocks.remove(block);
                            break;
                        }
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Block removed &asuccessfully&7!"));
                    return true;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7We couldn't find a block with that codename!"));
            return true;
        }
        return true;
    }
}
