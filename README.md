<div align="center">
  <h1>⚔️ Combat Cooldown Adjuster</h1>
  <p><i>"Respect the Player's Time, Not the Game's Rules."</i></p>

  <a href="https://modrinth.com">
    <img src="https://img.shields.io/badge/Modrinth-Available-00AF5C?style=for-the-badge&logo=modrinth" alt="Modrinth">
  </a>
  <a href="https://curseforge.com">
    <img src="https://img.shields.io/badge/CurseForge-Available-F16436?style=for-the-badge&logo=curseforge" alt="CurseForge">
  </a>
  <img src="https://img.shields.io/badge/Minecraft-26.1%20Snapshot%2010-blue?style=for-the-badge&logo=minecraft" alt="Minecraft Version">
  <img src="https://img.shields.io/badge/Java-25-red?style=for-the-badge&logo=openjdk" alt="Java Version">
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

## ⚙️ Configuration (GameRules)
This mod uses **DasikLibrary** for high-performance GameRule management. No complex JSONs or external config screens required.

| Rule Name | Default | Description |
| :--- | :---: | :--- |
| `combatcooldownadjuster:attack_cooldown_ms` | `500` | Global attack cooldown in milliseconds. |
| `combatcooldownadjuster:item_swap_cooldown_ms` | `500` | Cooldown applied when swapping items. |

## 🛠️ Build Instructions
Requires **OpenJDK 25** and **Gradle 9.3+**.

```powershell
# Clone the repository
git clone https://github.com/rifaditya/combat-cooldown-adjuster.git

# Build the JAR
./gradlew build
```

## 🏗️ Technical Overview
Built for high-performance and precision:
- **Thin Mod, Fat Library**: Logic-heavy components reside in [DasikLibrary](https://github.com/rifaditya/dasik-library).
- **O(1) Hotpath**: Cooldown checks are zero-allocation and O(1) complexity.
- **Java 25 Native**: Leverages Virtual Threads and Pattern Matching for peak performance.

## 🤝 Contribution & Credits
- **Lead Architect**: [Rifaditya (Dasik)](https://github.com/rifaditya)
- **License**: GPL-3.0

> [!IMPORTANT]
> This mod is strictly for **Snapshot 26.1 Snapshot 10+**. Legacy versions (1.21.x) are NOT supported and will not be backported.

---
<div align="center">
  Made with ❤️ for the Minecraft community.
</div>
