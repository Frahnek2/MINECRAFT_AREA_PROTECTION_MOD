package com.areaprotection.commands;

import com.areaprotection.AreaProtectionPlugin;
import com.areaprotection.Claim;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public class CommandManager implements CommandExecutor {

    private final AreaProtectionPlugin plugin;

    public CommandManager(AreaProtectionPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use these commands.");
            return true;
        }

        Player player = (Player) sender;
        String cmd = command.getName().toLowerCase();

        switch (cmd) {
            case "claimwand":
                ItemStack wand = new ItemStack(Material.GOLDEN_SHOVEL);
                ItemMeta meta = wand.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Claim Wand");
                    wand.setItemMeta(meta);
                }
                player.getInventory().addItem(wand);
                player.sendMessage(ChatColor.GREEN + "You have received the Claim Wand! Left-click a block for Position 1, Right-click for Position 2.");
                return true;

            case "claim":
                if (args.length < 1) {
                    player.sendMessage(ChatColor.RED + "Usage: /claim <name>");
                    return true;
                }
                String claimName = args[0];

                if (plugin.getClaims().containsKey(claimName)) {
                    player.sendMessage(ChatColor.RED + "A claim with that name already exists!");
                    return true;
                }

                UUID uuid = player.getUniqueId();
                Location pos1 = plugin.getPos1Map().get(uuid);
                Location pos2 = plugin.getPos2Map().get(uuid);

                if (pos1 == null) {
                    player.sendMessage(ChatColor.RED + "You must set pos1 first using the left-click of the Claim Wand!");
                    return true;
                }
                if (pos2 == null) {
                    player.sendMessage(ChatColor.RED + "You must set pos2 first using the right-click of the Claim Wand!");
                    return true;
                }
                if (pos1.getWorld() != pos2.getWorld()) {
                    player.sendMessage(ChatColor.RED + "Both positions must be in the same world!");
                    return true;
                }

                Claim newClaim = new Claim(claimName, uuid, pos1, pos2);
                plugin.getClaims().put(claimName, newClaim);
                
                plugin.getPos1Map().remove(uuid);
                plugin.getPos2Map().remove(uuid);

                player.sendMessage(ChatColor.GREEN + "Claim " + ChatColor.YELLOW + claimName + ChatColor.GREEN + " has been successfully created!");
                return true;

            case "adminclaim":
                if (!player.hasPermission("protection.admin")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                    return true;
                }

                if (args.length < 1) {
                    player.sendMessage(ChatColor.RED + "Usage: /adminclaim <list|remove> [name]");
                    return true;
                }

                String subCmd = args[0].toLowerCase();
                if (subCmd.equals("remove")) {
                    if (args.length < 2) {
                        player.sendMessage(ChatColor.RED + "Usage: /adminclaim remove <name>");
                        return true;
                    }
                    String targetClaim = args[1];
                    if (plugin.getClaims().remove(targetClaim) != null) {
                        player.sendMessage(ChatColor.GREEN + "Claim " + ChatColor.YELLOW + targetClaim + ChatColor.GREEN + " has been completely deleted.");
                    } else {
                        player.sendMessage(ChatColor.RED + "That claim does not exist.");
                    }
                } else if (subCmd.equals("list")) {
                    if (plugin.getClaims().isEmpty()) {
                        player.sendMessage(ChatColor.YELLOW + "There are no claims currently.");
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "List of all claims:");
                        for (String key : plugin.getClaims().keySet()) {
                            player.sendMessage(ChatColor.WHITE + "- " + ChatColor.GREEN + key);
                        }
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Usage: /adminclaim <list|remove> [name]");
                }
                return true;
        }
        return false;
    }
}
