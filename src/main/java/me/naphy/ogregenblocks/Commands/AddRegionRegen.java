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

public class AddRegionRegen implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7Invalid use of the command. Please use: &c/addregionregen <codename> <time> <block>&7!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("addregionregen")) {
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
            for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Regions").getKeys(false)) {
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
            RegionLoader.RegionInfo temp = new RegionLoader.RegionInfo();
            temp.regionName = args[0];
            temp.regionTimer = Integer.parseInt(args[1]);
            temp.regionEnabled = true;
            temp.minPos = BlockVector3.at(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ());
            temp.maxPos = BlockVector3.at(region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ());
            temp.regionWorld = ((Player) sender).getWorld();
            temp.block = args[2];
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".Enabled", true);
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".Timer", temp.regionTimer);
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".World", temp.regionWorld.getName());
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".MinPosX", temp.minPos.getX());
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".MinPosY", temp.minPos.getY());
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".MinPosZ", temp.minPos.getZ());
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".MaxPosX", temp.maxPos.getX());
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".MaxPosY", temp.maxPos.getY());
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".MaxPosZ", temp.maxPos.getZ());
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".ItemsAdderBlock", temp.block);
            OGRegenBlocks.plugin.saveConfig();
            RegionLoader.regions.add(temp);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Region added &asuccessfully&7!"));
            return true;
        }
        return true;
    }
}
