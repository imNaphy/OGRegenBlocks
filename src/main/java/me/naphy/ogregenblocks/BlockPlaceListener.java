package me.naphy.ogregenblocks;

import me.naphy.ogregenblocks.Commands.StartAddingBlocksRegen;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType().equals(Material.STONE)) {
            if (StartAddingBlocksRegen.playerEditors.contains(event.getPlayer().getName())) {
                StartAddingBlocksRegen.CoordsInfo temp = new StartAddingBlocksRegen.CoordsInfo();
                temp.X = event.getBlockPlaced().getX();
                temp.Y = event.getBlockPlaced().getY();
                temp.Z = event.getBlockPlaced().getZ();
                for (StartAddingBlocksRegen.EditorsInfo editor : StartAddingBlocksRegen.editors) {
                    if (editor.player.getName().equals(event.getPlayer().getName())) {
                        editor.coords.add(temp);
                    }
                }
            }
        }
    }
}
