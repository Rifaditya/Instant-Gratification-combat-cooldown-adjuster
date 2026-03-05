<div align="center">
  <img src="https://raw.githubusercontent.com/rifaditya/combat-cooldown-adjuster/main/assets/banner.png" alt="Combat Cooldown Adjuster Banner" width="100%">
  <br />
  <h1>⚔️ Combat Cooldown Adjuster</h1>
  <p><i>Respect the Player's Time, Not the Game's Rules.</i></p>
</div>

---

## ⚡ The Hook
**Combat Cooldown Adjuster** is a precision engineering mod for Minecraft 26.x Snapshot 10. It completely decouples the combat cooldown from the 20hz tick rate and weapon-specific attributes, replacing them with a custom, millisecond-precise global timer.

Whether you want 1.8-style instant click combat (0ms) or a perfectly timed tactical rhythm, this mod ensures that what you see is what you get.

## ✨ Key Features
- **🕰️ Millisecond Precision**: Attack strength is calculated using `Util.getMillis()`, allowing for infinite granularity between server ticks.
- **🎞️ Seamless Animation**: Using True Animation Interpolation, your weapon swing and crosshair indicator follow the exact configured timing. No janky snapping.
- **🛡️ Full Damage Sync**: Deal 100% damage the moment your custom cooldown expires. No "weak hit" penalties.
- **🔄 Swap Cooldown Control**: Separately configure the delay applied when switching items.

## ⚙️ Config (GameRules)
Configuration is handled natively via GameRules (powered by **DasikLibrary**).

| Rule | Default | Description |
| :--- | :---: | :--- |
| `combatcooldownadjuster:attack_cooldown_ms` | `500` | Global attack cooldown in ms. |
| `combatcooldownadjuster:item_swap_cooldown_ms` | `500` | Cooldown applied when swapping items. |

## 🧩 Compatibility
| Singleplayer | Multiplayer | Modpacks |
| :---: | :---: | :---: |
| ✅ Full | ✅ Full | ✅ Encouraged |

## ☕ Support the Project
If you enjoy the **Instant Gratification** collection, consider supporting the development:
- [Ko-fi](https://ko-fi.com/rifaditya)
- [SocioBuzz (Indonesia)](https://sociobuzz.com/rifaditya/tribe)

---

> [!NOTE]
> This mod requires **DasikLibrary** to function. Ensure it is installed on both the server and the client.

---
<div align="center">
  <i>Made with ❤️ by Rifaditya (Dasik)</i>
</div>
