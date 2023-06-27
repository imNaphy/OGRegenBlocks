package me.naphy.ogregenblocks;

import me.naphy.ogregenblocks.Commands.*;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class OGRegenBlocks extends JavaPlugin {

    public static OGRegenBlocks plugin;
    public boolean pluginSwitch;

    @Override
    public void onEnable() {
        plugin = this;
        this.saveDefaultConfig();
        pluginSwitch = this.getConfig().getBoolean("Enabled");
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getCommand("test").setExecutor(new test());
        getCommand("ogregenblocks").setExecutor(new Main());
        getCommand("addregionregen").setExecutor(new AddRegionRegen());
        getCommand("addsingularblockregen").setExecutor(new AddSingularBlockRegen());
        getCommand("regioninfo").setExecutor(new RegionInfo());
        getCommand("blockinfo").setExecutor(new BlockInfo());
        getCommand("deleteregionregen").setExecutor(new DeleteRegionRegen());
        getCommand("deleteblockregen").setExecutor(new DeleteBlockRegen());
        getCommand("startaddingblocksregen").setExecutor(new StartAddingBlocksRegen());
        getCommand("stopaddingblocksregen").setExecutor(new StopAddingBlocksRegen());

        //getCommand("findblockid").setExecutor(new FindBlockId());
        RegionLoader.init();
        Scheduler.start();
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&a[OGRegenBlocks] The plugin has been loaded!"));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[OGRegenBlocks] The plugin has been unloaded!"));
    }
}
