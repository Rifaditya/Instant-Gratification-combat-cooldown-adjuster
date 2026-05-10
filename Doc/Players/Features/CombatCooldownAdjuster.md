# ✨ Combat Cooldown Adjuster: Features

This document provides a deep dive into the mechanics added by the mod.

---

## 🕰️ Categorical Ticks
Vanilla Minecraft uses a complex speed multiplier system. **Combat Cooldown Adjuster** bypasses this and uses **Exact Ticks**.

| Category | Default Ticks | GameRule |
| :--- | :--- | :--- |
| 🗡️ Sword | 4 | `ig:sword_cooldown_ticks` |
| 🪓 Axe | 8 | `ig:axe_cooldown_ticks` |
| 🔱 Spear | 6 | `ig:spear_cooldown_ticks` |
| ⛏️ Pickaxe | 4 | `ig:pickaxe_cooldown_ticks` |
| 🧹 Shovel | 2 | `ig:shovel_cooldown_ticks` |
| 🎋 Hoe | 1 | `ig:hoe_cooldown_ticks` |
| 📦 Generic | 4 | `ig:generic_cooldown_ticks` |

> [!TIP]
> **1 Tick = 0.05 Seconds.** A setting of `4` ticks means you can strike every `0.2` seconds.

---

## 🏎️ Swap Agility
**Feature**: `ig:prevent_item_swap_cooldown` (Default: `true`)

In vanilla, switching your main-hand item resets your attack strength to zero. This mod prevents that reset if the rule is enabled.
- **Use Case**: Fire a bow, swap to a sword, and strike instantly.
- **Use Case**: Switch from an Axe (high damage) to a Sword (low delay) to finish a target without waiting for the sword's "equip animation" to finish.

---

## 🎞️ Combat Juice
**Feature**: `ig:enable_combat_juice` (Default: `true`)

We believe combat should be sensory. When you land a hit with **>80% charge**:
1. **Particles**: A burst of `crit` and `enchanted_hit` particles appears at the target's center.
2. **Audio**: The standard "strong attack" sound is played, but its **pitch increases** as your weapon charge approaches 100%.
   - **80% Charge**: Normal Pitch (1.0)
   - **100% Charge**: High Pitch (~1.4)

---

## 🏷️ Custom Weapon Support
The mod natively supports any item tagged with `c:spears`. If you are using a weapon mod that doesn't fit into vanilla categories, you can add it to this tag via a data pack to grant it the "Spear" cooldown speed.
