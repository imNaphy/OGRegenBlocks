package me.naphy.ogregenblocks.Commands;

import me.naphy.ogregenblocks.RegionLoader;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FindBlockId implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7Invalid use of the command. Please use: &c/findblockid <X> <Y> <Z>&7!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("findblockid")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (args.length != 3) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            for (RegionLoader.BlockInfo block : RegionLoader.blocks) {
                if (block.Pos.getX() == Integer.parseInt(args[0]) && block.Pos.getY() == Integer.parseInt(args[1]) && block.Pos.getZ() == Integer.parseInt(args[2])) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Found a block with the ID: &c" + block.blockName + "&7!"));
                    return true;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7We couldn't find a block with those coords!"));
            return true;
        }
        return true;
    }
}
