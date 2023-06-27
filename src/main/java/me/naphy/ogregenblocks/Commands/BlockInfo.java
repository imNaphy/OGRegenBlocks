package me.naphy.ogregenblocks.Commands;

import me.naphy.ogregenblocks.OGRegenBlocks;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BlockInfo implements CommandExecutor {

    private String help = "&b[OGRegenBlocks] &7Invalid use of the command. Please use: &c/blockinfo <codename>&7!";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("blockinfo")) {
            if (!sender.hasPermission("ogregenblocks.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7You don't have access to this command!"));
                return true;
            }
            if (args.length != 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                return true;
            }
            for (String i : OGRegenBlocks.plugin.getConfig().getConfigurationSection("Singular blocks").getKeys(false)) {
                if (args[0].equals(i)) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7Info about the &c" + i + "&7 region: " +
                            "\n\n&7Enabled: &c" + OGRegenBlocks.plugin.getConfig().getBoolean("Singular blocks." + i + ".Enabled") +
                            "\n&7Timer: &c" + OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + i + ".Timer") +
                            "\n&7World: &c" + OGRegenBlocks.plugin.getConfig().getString("Singular blocks." + i + ".World") +
                            "\n&7Coords: &c" + OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + i + ".PosX") + ", " + OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + i + ".PosY") + ", " + OGRegenBlocks.plugin.getConfig().getInt("Singular blocks." + i + ".PosZ") +
                            "\n&7Block: &c") + OGRegenBlocks.plugin.getConfig().getString("Singular blocks." + i + ".ItemsAdderBlock")
                    );
                    return true;
                }
            }
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[OGRegenBlocks] &7We couldn't find a block with that codename!"));
            return true;
        }
        return true;
    }
}
