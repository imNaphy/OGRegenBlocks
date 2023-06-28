package me.naphy.ogregenblocks;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    static Runnable runnable = new Runnable() {

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
                                for (int x = region.minPos.getX(); x <= region.maxPos.getX(); ++x) {
                                    for (int y = region.minPos.getY(); y <= region.maxPos.getY(); ++y) {
                                        for (int z = region.minPos.getZ(); z <= region.maxPos.getZ(); ++z) {
                                            int finalX = x;
                                            int finalY = y;
                                            int finalZ = z;
                                            Bukkit.getScheduler().runTask(OGRegenBlocks.plugin, () -> CustomBlock.getInstance(region.block).place(new Location(region.regionWorld, finalX, finalY, finalZ)));
                                            Bukkit.getScheduler().runTask(OGRegenBlocks.plugin, () -> CustomBlock.getInstance(region.block).place(new Location(region.regionWorld, finalX, finalY, finalZ)));
                                        }
                                    }
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

    static ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

    public static void start() {
        service.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public static void stop() {
        service.shutdownNow();
    }
}
