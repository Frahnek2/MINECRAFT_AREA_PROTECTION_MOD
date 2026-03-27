package com.areaprotection;

import org.bukkit.Location;
import java.util.UUID;

public class Claim {
    private final String name;
    private final UUID owner;
    private final String world;
    private final int x1, y1, z1, x2, y2, z2;

    public Claim(String name, UUID owner, Location pos1, Location pos2) {
        this.name = name;
        this.owner = owner;
        this.world = pos1.getWorld().getName();
        this.x1 = Math.min(pos1.getBlockX(), pos2.getBlockX());
        this.y1 = Math.min(pos1.getBlockY(), pos2.getBlockY());
        this.z1 = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        this.x2 = Math.max(pos1.getBlockX(), pos2.getBlockX());
        this.y2 = Math.max(pos1.getBlockY(), pos2.getBlockY());
        this.z2 = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
    }

    public Claim(String name, UUID owner, String world, int x1, int y1, int z1, int x2, int y2, int z2) {
        this.name = name;
        this.owner = owner;
        this.world = world;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public boolean contains(Location loc) {
        if (!loc.getWorld().getName().equals(world)) return false;
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        return x >= x1 && x <= x2 && y >= y1 && y <= y2 && z >= z1 && z <= z2;
    }

    public String getName() { return name; }
    public UUID getOwner() { return owner; }
    public String getWorld() { return world; }
    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getZ1() { return z1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }
    public int getZ2() { return z2; }
}
