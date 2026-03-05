# Features & Mechanics

**Combat Cooldown Adjuster** is designed to provide "Instant Gratification" in Minecraft combat. Here is exactly what it changes:

## 1. 🕰️ Millisecond Timing
Unlike standard Minecraft which updates in 50ms intervals (ticks), this mod uses your computer's high-precision clock to determine when you can strike again. This makes combat feel much more responsive, especially at high speeds.

## 2. 🎞️ Smooth Animation Sync
If you've used other "no cooldown" mods, you might have noticed the arm swing looking "clipped" or static. This mod interpolates the animation so that your arm swings at a speed that perfectly matches your attack cooldown. 

## 3. 🛡️ 100% Damage Output
The moment your weapon "recharges" (based on your millisecond setting), you deal 100% damage. There is no guesswork or "half-strength" penalty if you time your clicks correctly.

## 4. 🔄 Item Swap Cooldown
Ever swapped to a sword and had to wait before you could hit? You can now customize or remove this delay specifically using the `item_swap_cooldown_ms` GameRule.

---
> [!TIP]
> Setting `attack_cooldown_ms` to `200` and `item_swap_cooldown_ms` to `100` is a great balance for fast-paced PvP!
