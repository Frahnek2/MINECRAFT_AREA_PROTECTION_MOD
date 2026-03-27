package com.areaprotection;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AreaProtectionPlugin extends JavaPlugin {
    
    // Memory storage
    private final Map<String, Claim> claims = new HashMap<>();
    private final Map<UUID, Location> pos1Map = new HashMap<>();
    private final Map<UUID, Location> pos2Map = new HashMap<>();

    // File storage
    private File claimsFile;
    private FileConfiguration claimsConfig;

    @Override
    public void onEnable() {
        createClaimsFile();
        loadClaims();

        // Register commands and listeners
        getServer().getPluginManager().registerEvents(new com.areaprotection.listeners.WandListener(this), this);
        getServer().getPluginManager().registerEvents(new com.areaprotection.listeners.ProtectionListener(this), this);
        
        com.areaprotection.commands.CommandManager cmdManager = new com.areaprotection.commands.CommandManager(this);
        getCommand("claimwand").setExecutor(cmdManager);
        getCommand("claim").setExecutor(cmdManager);
        getCommand("adminclaim").setExecutor(cmdManager);

        getLogger().info("AreaProtection plugin enabled!");
    }

    @Override
    public void onDisable() {
        saveClaims();
        getLogger().info("AreaProtection plugin disabled and claims saved.");
    }
    
    public Map<String, Claim> getClaims() { return claims; }
    public Map<UUID, Location> getPos1Map() { return pos1Map; }
    public Map<UUID, Location> getPos2Map() { return pos2Map; }

    private void createClaimsFile() {
        claimsFile = new File(getDataFolder(), "claims.yml");
        if (!claimsFile.exists()) {
            claimsFile.getParentFile().mkdirs();
            try {
                claimsFile.createNewFile();
            } catch (IOException e) {
                getLogger().severe("Could not create claims.yml!");
            }
        }
    }
    
    public void loadClaims() {
        claims.clear();
        if (!claimsFile.exists()) return;
        claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
        if (claimsConfig.getConfigurationSection("claims") == null) return;
        
        for (String key : claimsConfig.getConfigurationSection("claims").getKeys(false)) {
            String path = "claims." + key;
            try {
                UUID owner = UUID.fromString(claimsConfig.getString(path + ".owner"));
                String world = claimsConfig.getString(path + ".world");
                int x1 = claimsConfig.getInt(path + ".x1");
                int y1 = claimsConfig.getInt(path + ".y1");
                int z1 = claimsConfig.getInt(path + ".z1");
                int x2 = claimsConfig.getInt(path + ".x2");
                int y2 = claimsConfig.getInt(path + ".y2");
                int z2 = claimsConfig.getInt(path + ".z2");
                claims.put(key, new Claim(key, owner, world, x1, y1, z1, x2, y2, z2));
            } catch (Exception e) {
                getLogger().warning("Failed to load claim: " + key);
            }
        }
    }

    public void saveClaims() {
        if (claimsConfig == null) {
            claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
        }
        claimsConfig.set("claims", null); // Clear old entries
        for (Claim claim : claims.values()) {
            String path = "claims." + claim.getName();
            claimsConfig.set(path + ".owner", claim.getOwner().toString());
            claimsConfig.set(path + ".world", claim.getWorld());
            claimsConfig.set(path + ".x1", claim.getX1());
            claimsConfig.set(path + ".y1", claim.getY1());
            claimsConfig.set(path + ".z1", claim.getZ1());
            claimsConfig.set(path + ".x2", claim.getX2());
            claimsConfig.set(path + ".y2", claim.getY2());
            claimsConfig.set(path + ".z2", claim.getZ2());
        }
        try {
            claimsConfig.save(claimsFile);
        } catch (IOException e) {
            getLogger().severe("Could not save claims.yml!");
        }
    }
}
