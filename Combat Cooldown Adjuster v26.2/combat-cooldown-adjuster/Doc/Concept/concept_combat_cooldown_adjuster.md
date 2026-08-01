# Concept: Combat Cooldown Adjuster

> **Philosophy**: Instant Gratification (IG) - "Respect the Player's Time, Not the Game's Rules."
> **Core Identity**: Reclaims the high-speed combat power fantasy by stripping away the artificial "wait-to-hit" friction of 1.9+ combat. This mod provides granular control over attack speed, allowing for everything from a subtle "Turbo" mode to total "Zero Cooldown" click-spamming, all while adding visual and auditory "Juice" to keep the experience feeling premium.

## Core Mechanics

### 1. Categorical Tick Overrides
- **Mechanism**: Intercepts the cooldown delay calculation. Instead of a global multiplier, it applies a specific **Tick Duration** based on the item's category.
- **Logic**: Modifies `Player#getCurrentItemAttackStrengthDelay()`. It checks the held `ItemStack` for tags and returns the corresponding GameRule value.
- **The "Feel"**: Total control. You can make Swords hyper-fast (2 ticks) while keeping Axes slow and heavy (12 ticks), or vice-versa.

### 2. Item Categories & Tag Detection
The mod automatically classifies items into the following buckets using 26.x Item Tags:
- **Swords**: `minecraft:swords`
- **Axes**: `minecraft:axes`
- **Pickaxes**: `minecraft:pickaxes`
- **Shovels**: `minecraft:shovels`
- **Hoes**: `minecraft:hoes`
- **Spears**: `c:spears` (Common tag)
- **Generic**: Any item not covered above (including fists).

### 3. Swap Agility (No Friction Swapping)
- **Mechanism**: Normally, switching items in the hotbar resets the attack cooldown. This feature neutralizes that reset when enabled.
- **Logic**: Mixin into `Player#resetAttackStrengthTicker()` to bypass the reset logic if the `ig:prevent_item_swap_cooldown` GameRule is true.
- **Goal**: Allow for rapid "Combo Swapping" (e.g., Bow shot -> Sword hit -> Axe finisher) without losing momentum.

### 4. Combat Juice (Dynamic Feedback)
- **Mechanism**: To prevent high-speed combat from feeling "floaty," the mod injects multi-sensory feedback based on attack frequency.
- **Visuals**: Spawns `minecraft:crit` and `minecraft:enchanted_hit` particles even on non-jump attacks if the cooldown is >80% charged.
- **Audio**: Successive hits within 10 ticks trigger a slightly higher-pitched `entity.player.attack.crit` sound, creating a pseudo-combo meter effect.


## Technical Hooks (Snapshot 26.x)
- `net.minecraft.world.entity.player.Player`:
    - `getCurrentItemAttackStrengthDelay()`: Target for the multiplier injection. [Verified: Line 1695]
    - `resetAttackStrengthTicker()`: Target for the swap-reset bypass. [Verified: Line 1713]
    - `attack(Entity target)`: Injection point for the "Juice" logic and particle bursts.

## Configuration (GameRules)
*Note: All rules are prefixed with `ig:` and managed via DasikLibrary.*

- `ig:sword_cooldown_ticks`: (Integer, default `4`)
- `ig:axe_cooldown_ticks`: (Integer, default `8`)
- `ig:pickaxe_cooldown_ticks`: (Integer, default `4`)
- `ig:shovel_cooldown_ticks`: (Integer, default `2`)
- `ig:hoe_cooldown_ticks`: (Integer, default `1`)
- `ig:spear_cooldown_ticks`: (Integer, default `6`)
- `ig:generic_cooldown_ticks`: (Integer, default `4`. Applies to tools/items not in the above categories).
- `ig:prevent_item_swap_cooldown`: (Boolean, default `true`. Prevents hotbar swapping from resetting the ticker).
- `ig:enable_combat_juice`: (Boolean, default `true`. Enables particles and pitch-shifted sounds).

## Assets Needed
- **Branding**: [icon.png](../Media/icon.png) - Premium arcade-style diamond sword icon.
- **Particles**: Vanilla `crit`, `enchanted_hit`, `sweep_attack`.
- **Sounds**: Vanilla `entity.player.attack.*` (Pitch-shifted via code).
- **Code**: Heavy Mixin usage in `Player.java`.

## Quality Assurance [PRO PROTOCOL]
- **Verification Commands**:
    - `/gamerule ig:sword_cooldown_ticks 1` (Test near-instant sword spam).
    - `/gamerule ig:axe_cooldown_ticks 20` (Test slow heavy axe for comparison).
- **Test Cases**:
    - **Vanilla Parity**: Verify that hitting *before* the tick threshold still deals reduced damage (vanilla scaling), just with a shifted time window.
    - **Swap Persistence**: Switch from a Sword to an Axe and back; verify the attack meter does not reset.
    - **Juice Audit**: Ensure particles only spawn when the meter is above the 80% threshold.
