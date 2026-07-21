# ⚔️ Combat Cooldown Adjuster

**Target Version:** Minecraft 26.1+ (Fabric)
**Dependencies:** Fabric API, DasikLibrary

> **"Stop the wait. Dominate the fight."**

**Combat Cooldown Adjuster** is a precision combat overhaul that hands control back to the player. By decoupling combat cooldowns from rigid vanilla math and replacing them with a **Tick-Based Categorical System**, you can fine-tune your combat experience to match your playstyle perfectly.

---

## 🚀 Core Features

### ⏱️ Categorical Control
Forget generic speed multipliers. Set exact tick delays for every tool type in the game:
- **Swords, Axes, Spears, Pickaxes, Shovels, Hoes.**
- Every category is individually configurable via GameRules.

### ⚡ Swap Agility
Tired of the artificial delay when switching weapons? **Swap Agility** removes the attack cooldown reset on item swap. Master the "Hotbar Combo" and strike the moment your weapon hits your hand.

### 💥 Combat Juice
We've added extra sensory feedback to make combat feel more gratifying:
- **Impact Particles**: `crit` and `enchanted_hit` spawns on target.
- **Dynamic Audio**: Strong attacks feature pitch-shifted audio that reflects your weapon's charge level.

---

## 🛠️ Configuration


<blockquote class="warning">
<strong>âš ï¸ Important: Config vs. In-Game GameRules</strong><br>
The global configuration file only defines <strong>default values for new worlds</strong> at creation time.
If you have <strong>already created/opened a world</strong>, changing the config file will have no effect. You must change the settings in-game using the <strong>Edit Game Rules</strong> UI screen or the <code>/gamerule</code> command.
</blockquote>
Configure your experience in-game using the **Game Rules** menu. No external files required.

**Example Commands:**
- `/gamerule ig:sword_cooldown_ticks 4`
- `/gamerule ig:prevent_item_swap_cooldown true`
- `/gamerule ig:enable_combat_juice true`

---

## 🤝 Support & Credits

Developed by **Dasik (Rifaditya)** as part of the **Instant Gratification Collection**.

<blockquote>
    <strong>📦 Modpack Permissions & Distribution:</strong><br>
    You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on <strong>Modrinth</strong> or <strong>CurseForge</strong>. Re-uploading or redistributing the mod jar file to third-party sites is strictly prohibited unless explicitly permitted by the creator.
    <br><br>
    <strong>License & Forks:</strong><br>
    Since the source code is licensed under <strong>GNU GPLv3</strong>, you are fully permitted to fork the repository, make modifications, build your own versions, and distribute them under the terms of the GPLv3. The prohibition on third-party redistribution applies exclusively to the official compiled releases/jars published by the original creator (Dasik/Rifaditya). Forks must be published as distinct projects, not direct re-uploads of official builds.
</blockquote>


---
*Part of the Instant Gratification Collection*
