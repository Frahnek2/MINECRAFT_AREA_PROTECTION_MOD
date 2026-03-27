package com.areaprotection.listeners;

import com.areaprotection.AreaProtectionPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WandListener implements Listener {

    private final AreaProtectionPlugin plugin;

    public WandListener(AreaProtectionPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || item.getType() != Material.GOLDEN_SHOVEL) return;
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;
        if (!item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "" + ChatColor.BOLD + "Claim Wand")) return;

        Action action = event.getAction();
        if (action == Action.LEFT_CLICK_BLOCK) {
            event.setCancelled(true);
            if (event.getClickedBlock() != null) {
                plugin.getPos1Map().put(player.getUniqueId(), event.getClickedBlock().getLocation());
                player.sendMessage(ChatColor.GREEN + "Position 1 set to " +
                        event.getClickedBlock().getX() + ", " +
                        event.getClickedBlock().getY() + ", " +
                        event.getClickedBlock().getZ() + ".");
            }
        } else if (action == Action.RIGHT_CLICK_BLOCK) {
            event.setCancelled(true);
            if (event.getClickedBlock() != null) {
                plugin.getPos2Map().put(player.getUniqueId(), event.getClickedBlock().getLocation());
                player.sendMessage(ChatColor.GREEN + "Position 2 set to " +
                        event.getClickedBlock().getX() + ", " +
                        event.getClickedBlock().getY() + ", " +
                        event.getClickedBlock().getZ() + ".");
            }
        }
    }
}
