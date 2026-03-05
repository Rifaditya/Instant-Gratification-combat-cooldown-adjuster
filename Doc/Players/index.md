# Player Guide

Welcome to the **Combat Cooldown Adjuster** player guide. This mod allows you to customize how combat feels on your server.

## 📖 Contents
- [Features & Mechanics](./Features.md): Detailed explanation of what the mod changes in your gameplay.
- [FAQ](#-frequently-asked-questions): Common questions and troubleshooting.

## ⚔️ What is this mod?
In standard Minecraft, weapons have a "recharge" time. If you click too fast, you deal very little damage. This mod allows server owners to change how fast that recharge happens, using precise millisecond values instead of generic speed attributes.

## 🛠️ How to Configure
Configuration is done through **GameRules**. Open your chat and type:
`/gamerule combatcooldownadjuster:attack_cooldown_ms <value>`

- **500**: Standard Sword speed (roughly).
- **100**: Fast paced combat.
- **0**: Classic 1.8-style spam clicking.

---
## ❓ Frequently Asked Questions

### Does this work in Multiplayer?
Yes! The mod is fully compatible with Dedicated Servers. For the best experience, it is recommended to have the mod installed on both the server and client (for animation syncing).

### Does it work with custom weapons?
Yes! The mod bypasses vanilla attributes, meaning it applies its custom cooldown to **every** item in the game.

---
*Created by Rifaditya (Dasik).*
