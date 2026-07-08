<div align="center">
  <img src="https://raw.githubusercontent.com/Rifaditya/Instant-Gratification-combat-cooldown-adjuster/main/src/main/resources/assets/cca/icon.png" width="128" alt="Combat Cooldown Adjuster Icon">
</div>

<p align="center">
    <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <a href="https://modrinth.com/mod/dasik-library"><img src="https://img.shields.io/badge/Requires-Dasik_Library-blue?style=for-the-badge&logo=modrinth" alt="Modrinth: Dasik Library"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPLv3-green?style=for-the-badge" alt="License">
    <img src="https://img.shields.io/badge/Minecraft-26.1+-brightgreen?style=for-the-badge" alt="Minecraft 26.1+">
</p>

# ⚔️ Combat Cooldown Adjuster

**No Backports:** This mod targets **Minecraft 26.1+**. Older versions are unsupported.

> **Stop waiting. Start fighting. High-gratification combat is here.**

**Combat Cooldown Adjuster** completely replaces the rigid, hard-coded vanilla combat timings with a flexible, tick-precise categorical system. Take control of your combat rhythm and remove the friction that slows you down.

Part of the **Instant Gratification Collection** — mods that respect the player's time.

---

## ✨ Features

### 🕰️ Categorical Tick Overrides
Adjust attack speeds per item category with millisecond precision. No more messy multipliers—set exact tick delays for:
- 🗡️ **Swords** (Default: 4 ticks)
- 🪓 **Axes** (Default: 8 ticks)
- ⛏️ **Pickaxes** (Default: 4 ticks)
- 🧹 **Shovels** (Default: 2 ticks)
- 🎋 **Hoes** (Default: 1 tick)
- 🔱 **Spears** (Native support for `c:spears` tag)
- 📦 **Generic** (Fallback for anything else)

### 🏎️ Swap Agility
Vanilla resets your attack cooldown every time you switch items. **Combat Cooldown Adjuster** lets you bypass this reset entirely. Switch between weapons in your hotbar and strike instantly—perfect for combo-heavy gameplay.

### 🎞️ Combat Juice
Every high-charge hit now feels impactful. 
- **Visuals**: Extra `crit` and `enchanted_hit` particles on impact.
- **Audio**: Pitch-shifted strong attack sounds that scale with your weapon's charge.
- Feel the power of every strike.

### 🏷️ Native Compatibility
Built on the **Vanilla Tag System**. Works automatically with custom weapons from other mods (Better Nether, Mythic Metals, etc.) as long as they are properly tagged.

---

## ⚙️ Configuration (Native Game Rules)


> [!IMPORTANT]
> **Config vs. In-Game GameRules:**
> The global configuration file only defines **default values for new worlds** at creation time.
> If you have **already created/opened a world**, changing the config file will have no effect. You must change the settings in-game using the **Edit Game Rules** UI screen or the /gamerule command.
No config files needed. Everything is handled via the native **Edit Game Rules** screen.

```sql
/gamerule ig:sword_cooldown_ticks 3       → Faster sword strikes
/gamerule ig:prevent_item_swap_cooldown true → Enable Swap Agility
/gamerule ig:enable_combat_juice true     → Enable extra feedback
```

---

## ☕ Support

If you enjoy the **Instant Gratification** collection, consider fueling the next update!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)
[![Saweria](https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge)](https://saweria.co/DasikIgaijinn)

> [!NOTE]
> **Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

---

## 📜 Credits

| Role | Author |
| :--- | :--- |
| **Architect** | **Dasik (Rifaditya)** |
| **Collection** | Instant Gratification |
| **License** | GPLv3 |

---

> [!IMPORTANT]
> **Modpack Permissions:** You are free to include this mod in modpacks, provided the modpack is hosted on the same platform (e.g. Modrinth). Cross-platform distribution is not permitted.

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
