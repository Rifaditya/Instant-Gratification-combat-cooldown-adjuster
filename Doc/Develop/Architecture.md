# Architecture: Millisecond Precision

## 🧠 The "Why"
In vanilla Minecraft, the combat strength is interpolated over 20 ticks per second (TPS). This results in a minimum granularity of 50ms. For professional combat or "Instant Gratification" style gameplay, this 50ms step is too slow.

**Combat Cooldown Adjuster** solves this by bypassing `Player.attackStrengthTicker` and instead using `Util.getMillis()`.

## 🛠️ Implementation Details

### 1. `PlayerMixin`
We intercept `getAttackStrengthScale` and `getItemSwapScale`. Instead of calculating progress based on the current ticker value, we compare the current `Util.getMillis()` against a stored `lastAttackTimeMs` timestamp.

```java
// Logic Snippet from PlayerMixin.java
long currentTime = Util.getMillis();
long attackElapsed = currentTime - lastAttackTimeMs;
float attackProgress = Math.min(1.0F, (float) attackElapsed / configMs);
cir.setReturnValue(attackProgress);
```

### 2. `LivingEntityMixin`
To ensure the arm swing animation doesn't look janky, we override `getCurrentSwingDuration` to return the tick-equivalent of our millisecond cooldown. This ensures the client-side rendering interpolation matches the server-side cooldown window.

## ⚡ Performance
- **Zero Allocation**: We do not create any objects (`new`) during the `getAttackStrengthScale` hotpath.
- **O(1)**: All checks are constant time.
- **Thread Safety**: Timestamps are primitive `long` values, safe for Snapshot 10's multithreaded entity ticking.
