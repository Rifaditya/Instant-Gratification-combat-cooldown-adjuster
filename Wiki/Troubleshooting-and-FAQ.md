# 🔧 Troubleshooting & FAQ Guide

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

## 1. Official Infobox
| Parameter | Diagnostic Details |
| :--- | :--- |
| **Target Subsystem** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **Controlling GameRules** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **Key Injection Point** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **Primary Dependency** | `dasik-library` (Dynamic GameRule Manager) |
| **Log Namespace** | `combat-cooldown-adjuster` |

---

## 2. Diagnostic Flowchart

```
           [ Combat Timing Issue Detected ]
                          |
                          v
         Is weapon delay different from expected?
               /                         \
             YES                          NO
             /                             \
    Check GameRule:                Does item swap reset ticker?
    /gamerule ig:<item>_ticks             /             \
    Verify item tag match                YES             NO
    (e.g. #minecraft:swords)             /                \
                                  Check GameRule:     Are particles / audio missing?
                                  ig:prevent_item_          /             \
                                  swap_cooldown = true    YES             NO
                                                          /                \
                                                Attack charge > 80%?     All systems
                                                Check ig:enable_juice    functioning!
```

---

## 3. Step-by-Step Diagnostic Workflow

### Phase 1: Calibrate Weapon Ticks
1. Open the in-game chat or server console.
2. Query the current active tick value for your weapon type:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. If the value returns default `4`, the attack rate is:
   $$f = \frac{20}{4} = 5.0 \text{ attacks/sec}$$
4. To test instant spam-clicking (Minecraft 1.8 style), execute:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. Strike a mob or training dummy; attacks will register instantly with zero attack cooldown delay.

### Phase 2: Diagnose Swap Agility
1. Equip a Sword in slot 1 and an Axe in slot 2.
2. Swing the Sword to trigger an attack.
3. Immediately press `2` to switch to the Axe.
4. If `ig:prevent_item_swap_cooldown` is `true`, your attack strength meter will **not** reset to zero; it will continue charging seamlessly.
5. If the meter resets, execute:
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### Phase 3: Particle & Audio Optimization
1. Combat Juice triggers when an attack scale is greater than $0.8$ ($80\%$ full charge).
2. If low-spec client hardware experiences micro-stutters during heavy combat particle bursts, disable Combat Juice on the server:
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. This completely disables server-side calls to `sendParticles` and `playSound` in `CCAHooks.applyCombatJuice`, conserving render threads and network bandwidth.

---

## 4. Mathematical Calibration Reference Table

| Tick Setting ($T$) | Attack Frequency ($f = 20/T$) | Attack Interval (ms) | Combat Feel Style |
| :---: | :---: | :---: | :--- |
| **`0`** | $\infty$ (20 TPS Engine Bound) | $0\text{ ms}$ (Instantaneous) | Pure 1.8 Click-Spam Combat |
| **`1`** | $20.0\text{ attacks/sec}$ | $50\text{ ms}$ | Ultra Hyper-Speed (Hoe Default) |
| **`2`** | $10.0\text{ attacks/sec}$ | $100\text{ ms}$ | Turbo Agility (Shovel Default) |
| **`4`** | $5.0\text{ attacks/sec}$ | $200\text{ ms}$ | Snappy Balanced Melee (Sword/Pickaxe Default) |
| **`6`** | $3.33\text{ attacks/sec}$ | $300\text{ ms}$ | Tactical Reach Cadence (Spear Default) |
| **`8`** | $2.5\text{ attacks/sec}$ | $400\text{ ms}$ | Heavy Impact Cleaving (Axe Default) |
| **`16`+** | $\le 1.25\text{ attacks/sec}$ | $\ge 800\text{ ms}$ | Vanilla 1.9+ Style Slow Paced Combat |

---

## 5. Frequently Asked Questions (FAQ)

### Q1: Does setting tick delay to 0 break vanilla damage scaling?
**No.** Vanilla calculates attack strength scale as $S(t) = \min\left(1.0, \frac{t + 0.5}{T}\right)$. When $T = 0$, our Mixin returns a delay of $0.0$, causing the vanilla game engine to immediately evaluate $S(t) = 1.0$. Every hit automatically deals $100\%$ full weapon base damage without penalty.

### Q2: Why does an untagged custom modded weapon attack at 4 ticks?
If a third-party modded item does not implement `#minecraft:swords`, `#minecraft:axes`, `#minecraft:pickaxes`, `#minecraft:shovels`, `#minecraft:hoes`, or `#c:spears`, `CCAHooks.getCooldownTicks` falls back to `CombatRules.GENERIC_TICKS` (default: 4 ticks). You can adjust this globally via:
```mcfunction
/gamerule ig:generic_cooldown_ticks <value>
```

### Q3: Does this mod cause multiplayer latency or client-side desync?
Combat Cooldown Adjuster runs server-authoritatively. When players connect to a dedicated server, `PlayerMixin` injects into the server-side player entity, and damage calculations remain authoritative. In singleplayer and LAN, both sides run locally with zero network delay.

### Q4: Are GameRules saved in world saves?
**Yes.** All 9 GameRules are serialized directly into the world's `level.dat` file via vanilla GameRule serialization managed by DasikLibrary. Changing a value persists across world reloads and server restarts.

---

## 6. Related Documentation Links
* Return to the [[Main Wiki Portal|Home]].
* View [[26.2 Configuration & GameRules|26.2-Configuration-and-GameRules]].
* View [[26.3 Configuration & GameRules|26.3-Configuration-and-GameRules]].
* Read the [[Developer Setup & Building Guide|Developer-Setup-and-Building]].
