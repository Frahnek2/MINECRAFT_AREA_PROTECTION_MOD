package com.areaprotection.listeners;

import com.areaprotection.AreaProtectionPlugin;
import com.areaprotection.Claim;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ProtectionListener implements Listener {

    private final AreaProtectionPlugin plugin;

    public ProtectionListener(AreaProtectionPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (shouldCancel(event.getPlayer(), event.getBlock().getLocation())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "This area is protected. You cannot build here!");
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (shouldCancel(event.getPlayer(), event.getBlock().getLocation())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatColor.RED + "This area is protected. You cannot break blocks here!");
        }
    }

    private boolean shouldCancel(Player player, Location loc) {
        if (player.hasPermission("protection.admin")) {
            return false;
        }
        for (Claim claim : plugin.getClaims().values()) {
            if (claim.contains(loc)) {
                if (!claim.getOwner().equals(player.getUniqueId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
