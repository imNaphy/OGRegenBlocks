package me.naphy.ogregenblocks.Commands;

import com.sk89q.worldedit.math.BlockVector3;
import dev.lone.itemsadder.api.CustomBlock;
import me.naphy.ogregenblocks.OGRegenBlocks;
import me.naphy.ogregenblocks.RegionLoader;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class StopAddingBlocksRegen implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("stopaddingblocksregen")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (!(StartAddingBlocksRegen.playerEditors.contains(sender.getName()))) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You are not in the &bEasy Block Regen Adder&7!"));
                return true;
            }
            for (StartAddingBlocksRegen.EditorsInfo editor : StartAddingBlocksRegen.editors) {
                if (editor.player.getName().equals(sender.getName())) {
                    for (String name : StartAddingBlocksRegen.playerEditors) {
                        if (name.equals(editor.player.getName())) {
                            StartAddingBlocksRegen.playerEditors.remove(name);
                            break;
                        }
                    }
                    for (StartAddingBlocksRegen.CoordsInfo coords : editor.coords) {
                        RegionLoader.BlockInfo temp = new RegionLoader.BlockInfo();
                        String tempString = GenerateString(12);
                        boolean test = true;
                        boolean test2 = false;
                        while (!test2) {
                            test = true;
                            for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Singular blocks").getKeys(false)) {
                                if (tempString.equals(i)) {
                                    test = false;
                                    break;
                                }
                            }
                            if (test) {
                                test2 = true;
                            }
                            else {
                                tempString = GenerateString(12);
                            }
                        }
                        temp.blockName = tempString;
                        temp.blockTimer = editor.timer;
                        temp.blockEnabled = true;
                        temp.Pos = BlockVector3.at(coords.X, coords.Y, coords.Z);
                        temp.blockWorld = editor.world;
                        temp.block = editor.block;
                        OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".Enabled", true);
                        OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".Timer", temp.blockTimer);
                        OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".World", temp.blockWorld.getName());
                        OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".PosX", temp.Pos.getX());
                        OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".PosY", temp.Pos.getY());
                        OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".PosZ", temp.Pos.getZ());
                        OGRegenBlocks.plugin.getConfig().set("Singular blocks." + temp.blockName + ".ItemsAdderBlock", temp.block);
                        OGRegenBlocks.plugin.saveConfig();
                        RegionLoader.blocks.add(temp);
                        Location loc = new Location(temp.blockWorld, temp.Pos.getX(), temp.Pos.getY(), temp.Pos.getZ());
                        loc.getBlock().setType(Material.AIR);
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You have &asuccessfully &7left the &bEasy Block Regen Adder&7! All the changes made have been (hopefully) added!"));
                    StartAddingBlocksRegen.editors.remove(editor);
                    break;
                }
            }
            System.out.println(StartAddingBlocksRegen.editors);
            System.out.println(StartAddingBlocksRegen.playerEditors);
        }
        return true;
    }

    public static String GenerateString(int n) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }
}
