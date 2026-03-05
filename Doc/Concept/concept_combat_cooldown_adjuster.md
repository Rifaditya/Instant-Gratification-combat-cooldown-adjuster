# ⚔️ Combat Cooldown Adjuster

## 🎯 Philosophy Fit
**Instant Gratification (IG)**: *"Respect the Player's Time, Not the Game's Rules."*

This mod allows players to completely bypass or customize the 1.9+ combat cooldown mechanics, enabling rapid striking based on exact millisecond intervals rather than static 50ms server ticks or weapon attribute defaults.

---

## 🛠️ Mechanics

### 1. 🕰️ Millisecond-Precise Global Cooldown Override
- **Description**: Replaces the default `generic.attack_speed` attribute-based cooldown system with a precise millisecond timer.
- **Implementation**: Evaluated independently of the server's 20 TPS loop to ensure true responsiveness.
- **Tech Stack**: Uses `Util.getMillis()` and overrides `getCurrentItemAttackStrengthDelay()`.

### 2. 🎞️ Swing Animation Syncing
- **Description**: Visual weapon swing animation and crosshair cooldown indicator perfectly interpolate to match the millisecond duration.
- **Implementation**: Overrides `getCurrentSwingDuration()` in `LivingEntity` for native client-side interpolation parity.

### 3. 🛡️ Damage Output Preservation
- **Description**: Players deal 100% weapon damage when the cooldown expires, with no "weak hit" penalties.
- **Implementation**: Intercepts `getAttackStrengthScale()` in `Player` to return `1.0F` based on the millisecond threshold.

---

## ⚙️ Configuration (GameRules)
Native GameRules only (Requires **DasikLibrary**).

- `combatcooldownadjuster:attack_cooldown_ms` (Default: `500`)
- `combatcooldownadjuster:item_swap_cooldown_ms` (Default: `500`)

---

## 🏗️ Project Metadata
- **Version**: `1.0.1`
- **Dependencies**: `dasik-library: 1.6.9+build.14`
- **Architecture**: O(1) Hotpath, Zero-Allocation.

---

## ✅ Implementation Checklist
- [x] Feature 1: Millisecond-Precise Global Cooldown Override
- [x] Feature 2: Swing Animation & Crosshair Syncing
- [x] Feature 3: Damage Output Preservation
- [x] GameRule registration via DasikLibrary
- [x] Premium Documentation Suite (Readme, Develop, Players)

---
> [!IMPORTANT]
> This mod targets **Minecraft 26.1 Snapshot 10** exclusively. Experimental Snapshots are the only verified environment.

