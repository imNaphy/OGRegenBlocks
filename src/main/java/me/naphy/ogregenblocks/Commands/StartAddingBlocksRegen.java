package me.naphy.ogregenblocks.Commands;

import dev.lone.itemsadder.api.CustomBlock;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StartAddingBlocksRegen implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7Invalid use of the command. Please use: &c/startaddingblocksregen <time> <block>&7!";
    public static class CoordsInfo {
        public int X;
        public int Y;
        public int Z;
    }

    public static class EditorsInfo {
        public Player player;
        public int timer;
        public World world;
        public String block;
        public List<CoordsInfo> coords;
    }
    public static List<EditorsInfo> editors = new ArrayList<>();
    public static List<String> playerEditors = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("startaddingblocksregen")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            if (playerEditors.contains(sender.getName())) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You are already in the &bEasy Block Regen Adder&7!"));
                return true;
            }
            if (CustomBlock.getInstance(args[1]) == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7That block doesn't exist or isn't registered in &cItemsAdder&7!"));
                return true;
            }
            if (!((Player) sender).getGameMode().equals(GameMode.CREATIVE)) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You must be in &cCREATIVE &7before being able to add blocks!"));
                return true;
            }
            if (!((Player) sender).getInventory().getItemInMainHand().equals(new ItemStack(Material.STONE))) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You must hold a &cSTONE &7before being able to add blocks!"));
                return true;
            }
            EditorsInfo temp = new EditorsInfo();
            temp.player = (Player) sender;
            temp.timer = Integer.parseInt(args[0]);
            temp.world = ((Player) sender).getWorld();
            temp.block = args[1];
            temp.coords = new ArrayList<>();
            editors.add(temp);
            playerEditors.add(sender.getName());
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You have now &asuccessfully &7entered the &bEasy Block Regen Adder&7! To place regen blocks, simply place the stone whenever you wish to have the block. When you're ready, just write &c/stopaddingblocksregen&7!"));
        }
        return true;
    }
}
