# Minecraft Area Protection Mod

This mod allows players to restrict building and breaking of blocks in their own areas using a simple "Claim Wand".
We provide two ways to run this mod on your server: **Skript** and a standard **Java (Spigot/Paper) Plugin**, so there is a version compatible with any setup!

## Features
- **Claim Wand**: Right-click or left-click with a special Golden Shovel to select boundary corners.
- **Save Claims**: Use `/claim <name>` to protect the selection for only the owner.
- **Protection**: Unauthorized players cannot modify blocks inside protected regions!
- **Admin Tools**: Easy commands for moderators to monitor or delete claims if necessary.

---

## 1. Java (Spigot) Plugin Version
This is a standard standalone plugin. You can compile and deploy it to any Spigot, Paper, or Purpur Java server.

### Installation
1. Compile the plugin using Maven from the `JavaPlugin` directory:
   ```bash
   cd JavaPlugin
   mvn clean package
   ```
2. Take the `AreaProtection-1.0.jar` from the `JavaPlugin/target/` directory and place it into your server's `plugins/` folder.
3. Restart your server. The plugin will be enabled automatically!

---

## 2. Skript Version
If you prefer lightweight scripts or already have the Skript plugin installed, you can use the `.sk` file directly for identical functionality.

### Installation
1. Install [Skript](https://github.com/SkriptLang/Skript/releases) onto your server.
2. Drag and drop the `area_protection.sk` file into the `plugins/Skript/scripts/` folder on your server.
3. In the game, type `/sk reload area_protection` to immediately activate the mod. No server restart required!

---

## Commands and Usage
Both versions share the exact same commands and logic:

1. `/claimwand`
   - Gives you a Golden Shovel named **"Claim Wand"**.
   - **Left-Click** a block to set Position 1.
   - **Right-Click** a block to set Position 2.

2. `/claim <name>`
   - Completes the claim over the area between your two selected positions.
   - Other players can no longer place or break blocks inside this claim.

3. `/adminclaim <list|remove> [name]`
   - View a list of all current claims, or forcefully remove an interfering claim.
   - **Requirement**: The player must have the `protection.admin` permission node (typically granted automatically to Server Operators).
