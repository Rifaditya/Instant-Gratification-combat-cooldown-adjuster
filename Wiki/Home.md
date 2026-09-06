# ⚔️ Instant Gratification: Combat Cooldown Adjuster Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

Welcome to the official technical documentation for **Instant Gratification: Combat Cooldown Adjuster**. This mod is engineered for Minecraft Fabric to restore high-velocity, adrenaline-fueled melee combat by dismantling the artificial "wait-to-hit" delay introduced in Minecraft 1.9+, while preserving precision damage scaling and dynamic sensory feedback.

---

## 🧭 Multi-Version Documentation Portals

Combat Cooldown Adjuster is maintained across distinct Minecraft version anchors under the **1 Jar 1 Version Policy**. Select your target version anchor below to access dedicated, isolated documentation trees:

| Version Anchor | Target Minecraft Release | Mod SemVer | Fabric Loader | Documentation Portal |
| :--- | :--- | :---: | :---: | :--- |
| **Minecraft 26.2** | `MC 26.1.2` / `26.2` | `1.0.1+26.2` | `>=0.19.1` | [[👉 Enter Minecraft 26.2 Documentation Portal|26.2-Home]] |
| **Minecraft 26.3** | `MC 26.3-snapshot-6` / `26.3` | `1.0.1+26.3` | `>=0.19.3` | [[👉 Enter Minecraft 26.3 Documentation Portal|26.3-Home]] |

---

## ⚡ Core Philosophy & Architectural Pillars

The **Instant Gratification (IG)** series is founded upon a single engineering invariant: *"Respect the Player's Time, Not the Game's Rules."* Combat Cooldown Adjuster delivers on this promise through four foundational subsystems:

1. **Categorical Attack Delay Overrides**:
   Vanilla Minecraft calculates weapon delay through rigid item attribute modifiers. Combat Cooldown Adjuster intercepts this at bytecode level, mapping weapons dynamically to customizable tick delays via native Item Tags (`#minecraft:swords`, `#minecraft:axes`, `#minecraft:pickaxes`, `#minecraft:shovels`, `#minecraft:hoes`, `#c:spears`). Setting a delay of `0` ticks unlocks unrestricted 1.8-style spam-clicking.

2. **Swap Agility (Frictionless Hotbar Combos)**:
   In vanilla combat, switching hotbar slots forces an instant penalty reset of your attack ticker. Our `ig:prevent_item_swap_cooldown` engine neutralizes this reset, allowing seamless weapon sequencing (e.g., Bow strike $\to$ Sword slash $\to$ Axe finisher) without losing combat cadence.

3. **Dynamic Combat Juice & Audio Feedback**:
   High-frequency combat must never feel weightless. When attacks land with $>80\%$ charge scale, the engine emits dual-layer hit sparks (`ParticleTypes.CRIT` and `ParticleTypes.ENCHANTED_HIT`) and dynamically pitch-shifts audio (`SoundEvents.PLAYER_ATTACK_STRONG`) from $1.0\times$ up to $1.4\times$ to reward rapid, aggressive combos.

4. **Runtime Dynamic GameRule Engine**:
   Powered by **DasikLibrary**, all 9 mod options are registered as first-class, namespaced GameRules (`combat-cooldown-adjuster:combat_cooldown`). Every single setting can be inspected, tab-completed, and dynamically mutated in-game via `/gamerule` with zero server restarts required.

---

## 📊 Quick Reference: Default GameRule Matrix

| GameRule Identifier | Type | Default Value | Target Category / Tag | Gameplay Impact |
| :--- | :---: | :---: | :--- | :--- |
| `ig:sword_cooldown_ticks` | Integer | `4` | `#minecraft:swords` | Hyper-responsive sword slashing ($5.0\text{ attacks/sec}$) |
| `ig:axe_cooldown_ticks` | Integer | `8` | `#minecraft:axes` | Heavy, deliberate cleaving ($2.5\text{ attacks/sec}$) |
| `ig:pickaxe_cooldown_ticks` | Integer | `4` | `#minecraft:pickaxes` | Fast secondary mining tool strikes ($5.0\text{ attacks/sec}$) |
| `ig:shovel_cooldown_ticks` | Integer | `2` | `#minecraft:shovels` | Rapid blunt strikes ($10.0\text{ attacks/sec}$) |
| `ig:hoe_cooldown_ticks` | Integer | `1` | `#minecraft:hoes` | Ultra-fast scythe combat ($20.0\text{ attacks/sec}$) |
| `ig:spear_cooldown_ticks` | Integer | `6` | `#c:spears` | Balanced long-range thrusts ($3.33\text{ attacks/sec}$) |
| `ig:generic_cooldown_ticks` | Integer | `4` | Untagged / Fists / Misc | Universal fallback attack delay |
| `ig:prevent_item_swap_cooldown` | Boolean | `true` | Hotbar Switch Event | Bypasses attack ticker reset on item swap |
| `ig:enable_combat_juice` | Boolean | `true` | Attack Event | Spawns critical particles and pitch-shifted audio |

---

## 📚 Essential Documentation Hubs

* [[📊 Version Compatibility Matrix|Version-Compatibility]]: Complete lifecycle support, Loom environments, Java 25 requirements, and library dependencies.
* [[🔧 Troubleshooting & FAQ Guide|Troubleshooting-and-FAQ]]: Calibration steps, tick delay mathematics, particle optimization, and network latency diagnostics.
* [[🛠️ Developer Setup & Build Guide|Developer-Setup-and-Building]]: Unified Gradle 9.3+ instructions, Loom compiler flags, Mixin architecture, and addon extension hooks.

---

## ⚖️ License & Provenance

* **Author & Lead Architect**: Dasik (Rifaditya)
* **License**: GNU General Public License v3.0 (GPLv3)
* **Design Philosophy**: Instant Gratification (IG)
* **Upstream Repository**: [GitHub Source Repository](https://github.com/Rifaditya/Instant-Gratification-combat-cooldown-adjuster)
