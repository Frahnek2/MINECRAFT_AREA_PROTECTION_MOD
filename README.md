# Area Protection Skript

This Skript provides a land protection system for your Minecraft server, allowing players to claim areas to prevent others from building or destroying blocks within them. It uses a wand-based selection system.

## Features
- **Claim Wand Selection**: Players can select an area by left-clicking (Position 1) and right-clicking (Position 2) blocks using a Claim Wand.
- **Area Protection**: Only the owner of a claimed area can place or break blocks inside it.
- **Admin Bypass & Management**: Admins with the proper permissions can bypass block protections and manage all claims on the server.

## Commands

### Player Commands
- `/claimwand`
  - **Description**: Gives you the golden shovel "Claim Wand" to select your claim area.
  - **Usage**: Left-click a block to set Position 1, and right-click a block to set Position 2.

- `/claim <name>`
  - **Description**: Creates a new claim using your selected positions with the specified name.
  - **Usage**: `/claim <name>`

### Admin Commands
- `/adminclaim <remove|list> [name]`
  - **Description**: Allows admins to list all existing claims or delete a specific claim.
  - **Permission Required**: `protection.admin`
  - **Usage**: 
    - `/adminclaim list` - Lists all currently saved claims.
    - `/adminclaim remove <name>` - Completely deletes the specified claim.

## Permissions
- `protection.admin`: Grants the ability to use the `/adminclaim` command to manage claims. It also allows the player to bypass build/break protections in all claimed areas.

## Requirements
- Skript plugin installed on your server.
