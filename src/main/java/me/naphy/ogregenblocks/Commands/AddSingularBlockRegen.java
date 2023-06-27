package me.naphy.ogregenblocks.Commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import dev.lone.itemsadder.api.CustomBlock;
import me.naphy.ogregenblocks.OGRegenBlocks;
import me.naphy.ogregenblocks.RegionLoader;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddSingularBlockRegen implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7Invalid use of the command. Please use: &c/addsingularblockregen <codename> <time> <block>&7!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("addsingularblockregen")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (args.length != 3) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            if (CustomBlock.getInstance(args[2]) == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7That block doesn't exist or isn't registered in &cItemsAdder&7!"));
                return true;
            }
            for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Singular blocks").getKeys(false)) {
                if (args[0].equals(i)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Codename already in use! Please pick another one!"));
                    return true;
                }
            }
            Region region;
            try {
                region = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt((Player) sender)).getSelection(BukkitAdapter.adapt((Player) sender).getWorld());
            } catch (IncompleteRegionException e) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You must first select a region! (use &c//wand&7 or &c//pos1 & //pos2&7)"));
                return true;
            }
            RegionLoader.BlockInfo temp = new RegionLoader.BlockInfo();
            temp.blockName = args[0];
            temp.blockTimer = Integer.parseInt(args[1]);
            temp.blockEnabled = true;
            temp.Pos = BlockVector3.at(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ());
            temp.blockWorld = ((Player) sender).getWorld();
            temp.block = args[2];
            OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".Enabled", true);
            OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".Timer", temp.blockTimer);
            OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".World", temp.blockWorld.getName());
            OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".PosX", temp.Pos.getX());
            OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".PosY", temp.Pos.getY());
            OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".PosZ", temp.Pos.getZ());
            OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".ItemsAdderBlock", temp.block);
            OGRegenBlocks.plugin.saveConfig();
            RegionLoader.blocks.add(temp);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Block added &asuccessfully&7!"));
            return true;
        }
        return true;
    }
}
