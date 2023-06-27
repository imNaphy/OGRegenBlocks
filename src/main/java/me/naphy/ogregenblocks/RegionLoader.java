package me.naphy.ogregenblocks;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class RegionLoader {

    public static class RegionInfo {
        public String regionName;
        public boolean regionEnabled;
        public int regionTimer;
        public World regionWorld;
        public BlockVector3 minPos;
        public BlockVector3 maxPos;
        public String block;
    }
    public static class BlockInfo {
        public String blockName;
        public boolean blockEnabled;
        public int blockTimer;
        public World blockWorld;
        public BlockVector3 Pos;
        public String block;
    }
    public static List<RegionInfo> regions = new ArrayList<>();
    public static List<BlockInfo> blocks = new ArrayList<>();

    public static void init() {
        regions = new ArrayList<>();
        for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Regions").getKeys(false)) {
            RegionInfo temp = new RegionInfo();
            temp.regionName = i;
            temp.regionEnabled = OGRegenBlocks.plugin.getConfig().getBoolean("Regions." + i + ".Enabled");
            temp.regionTimer = OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".Timer");
            temp.regionWorld = Bukkit.getWorld(OGRegenBlocks.plugin.getConfig().getString("Regions." + i + ".World"));
            temp.minPos = BlockVector3.at(OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MinPosX"), OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MinPosY"), OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MinPosZ"));
            temp.maxPos = BlockVector3.at(OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MaxPosX"), OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MaxPosY"), OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MaxPosZ"));
            temp.block = OGRegenBlocks.plugin.getConfig().getString("Regions." + i + ".ItemsAdderBlock");
            regions.add(temp);
        }
        blocks = new ArrayList<>();
        for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Singular blocks").getKeys(false)) {
            BlockInfo temp = new BlockInfo();
            temp.blockName = i;
            temp.blockEnabled = OGRegenBlocks.plugin.getConfig().getBoolean("Singular blocks." + i + ".Enabled");
            temp.blockTimer = OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + i + ".Timer");
            temp.blockWorld = Bukkit.getWorld(OGRegenBlocks.plugin.getConfig().getString("Singular blocks." + i + ".World"));
            temp.Pos = BlockVector3.at(OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + i + ".PosX"), OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + i + ".PosY"), OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + i + ".PosZ"));
            temp.block = OGRegenBlocks.plugin.getConfig().getString("Singular blocks." + i + ".ItemsAdderBlock");
            blocks.add(temp);
        }
    }
}
