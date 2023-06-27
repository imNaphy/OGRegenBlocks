package me.naphy.ogregenblocks.Commands;

import me.naphy.ogregenblocks.OGRegenBlocks;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RegionInfo implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7Invalid use of the command. Please use: &c/regioninfo <codename>&7!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("regioninfo")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (args.length != 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Regions").getKeys(false)) {
                if (args[0].equals(i)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Info about the &c" + i + "&7 region: " +
                            "\n\n&7Enabled: &c" + OGRegenBlocks.plugin.getConfig().getBoolean("Regions." + i + ".Enabled") +
                            "\n&7Timer: &c" + OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".Timer") +
                            "\n&7World: &c" + OGRegenBlocks.plugin.getConfig().getString("Regions." + i + ".World") +
                            "\n&7Lowest corner coords: &c" + OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MinPosX") + ", " + OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MinPosY") + ", " + OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MinPosZ") +
                            "\n&7Highest corner coords: &c" + OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MaxPosX") + ", " + OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MaxPosY") + ", " + OGRegenBlocks.plugin.getConfig().getInt("Regions." + i + ".MaxPosZ") +
                            "\n&7Block: &c") + OGRegenBlocks.plugin.getConfig().getString("Regions." + i + ".ItemsAdderBlock")
                    );
                    return true;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7We couldn't find a region with that codename!"));
            return true;
        }
        return true;
    }
}
