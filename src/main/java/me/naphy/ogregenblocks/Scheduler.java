package me.naphy.ogregenblocks;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    public static void start() {

        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try {
                    if (OGRegenBlocks.plugin.pluginSwitch) {
                        for (RegionLoader.RegionInfo region : RegionLoader.regions) {
                            if (region.regionEnabled) {
                                if (region.regionTimer <= 0) {
                                    region.regionTimer = OGRegenBlocks.plugin.getConfig().getInt("Regions." + region.regionName + ".Timer");
                                    CuboidRegion tempRegion = new CuboidRegion(region.minPos, region.maxPos);
                                    for (BlockVector3 point : tempRegion) {
                                        Bukkit.getScheduler().runTask(OGRegenBlocks.plugin, () -> CustomBlock.getInstance(region.block).place(new Location(region.regionWorld, point.getX(), point.getY(), point.getZ())));
                                        Bukkit.getScheduler().runTask(OGRegenBlocks.plugin, () -> CustomBlock.getInstance(region.block).place(new Location(region.regionWorld, point.getX(), point.getY(), point.getZ())));
                                    }
                                }
                                else {
                                    region.regionTimer--;
                                }
                            }
                        }
                        for (RegionLoader.BlockInfo block : RegionLoader.blocks) {
                            if (block.blockEnabled) {
                                if (block.blockTimer <= 0) {
                                    block.blockTimer = OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + block.blockName + ".Timer");
                                    Bukkit.getScheduler().runTask(OGRegenBlocks.plugin, () -> CustomBlock.getInstance(block.block).place(new Location(block.blockWorld, block.Pos.getX(), block.Pos.getY(), block.Pos.getZ())));
                                    Bukkit.getScheduler().runTask(OGRegenBlocks.plugin, () -> CustomBlock.getInstance(block.block).place(new Location(block.blockWorld, block.Pos.getX(), block.Pos.getY(), block.Pos.getZ())));
                                }
                                else {
                                    block.blockTimer--;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        };
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }
}
