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

**Permissions:** 
- You are free to use this in modpacks hosted on CurseForge.
- Re-hosting the JAR on other sites is prohibited.

---
*Part of the Instant Gratification Collection*
