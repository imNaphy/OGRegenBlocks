package me.naphy.ogregenblocks.Commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.internal.annotation.Selection;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import dev.lone.itemsadder.api.CustomBlock;
import me.naphy.ogregenblocks.OGRegenBlocks;
import me.naphy.ogregenblocks.RegionLoader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class test implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            Region region = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt((Player) sender)).getSelection(BukkitAdapter.adapt((Player) sender).getWorld());
            RegionLoader.RegionInfo temp = new RegionLoader.RegionInfo();
            String tempS = "wad";
            boolean test = true;
            boolean test2 = false;
            while (!test2) {
                test = true;
                for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Regions").getKeys(false)) {
                    if (tempS.equals(i)) {
                        test = false;
                        break;
                    }
                }
                if (test) {
                    test2 = true;
                }
                else {
                    tempS = "wad";
                }
            }
            temp.regionName = tempS;
            temp.regionTimer = 20;
            temp.regionEnabled = true;
            temp.minPos = BlockVector3.at(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ());
            temp.maxPos = BlockVector3.at(region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ());
            temp.regionWorld = ((Player) sender).getWorld();
            temp.block = "alexandrite_ore";
            System.out.println(temp);
            OGRegenBlocks.plugin.getConfig().set("Regions." + temp.regionName + ".Enabled", true);
            OGRegenBlocks.plugin.saveConfig();
        } catch (IncompleteRegionException e) {
            throw new RuntimeException(e);
        }
        /*
        CustomBlock customBlock = CustomBlock.getInstance("alexandrite_ore");
        WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        BlockVector3 pos1 = BlockVector3.at(-231, 69, -245);
        BlockVector3 pos2 = BlockVector3.at(-228, 66, -248);
        CuboidRegion region = new CuboidRegion(pos1, pos2);
        World world = ((Player) sender).getWorld();
        for (BlockVector3 point : region) {
            Location loc = new Location(world, point.getX(), point.getY(), point.getZ());
            customBlock.place(loc);
            customBlock.place(loc);
        }
        */
        return true;
    }

    public final Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

}
