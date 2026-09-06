# 🛠️ Developer Setup & Unified Gradle Build Guide

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

## 1. Official Infobox
| Parameter | Technical Details |
| :--- | :--- |
| **Build Automation** | Gradle 9.3+ with Fabric Loom |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Source Repositories** | Multi-Version Anchor Subprojects (`26.2` & `26.3`) |
| **Bytecode Injector** | SpongePowered Mixin 0.8+ |
| **API Dependencies** | Fabric API, DasikLibrary (`DynamicGameRuleManager`) |
| **Compilation Flag** | `./gradlew build --no-daemon` |

---

## 2. Workspace Prerequisites & Environment Setup

Building Combat Cooldown Adjuster from source requires an active JDK 25 installation.

### Recommended Environment:
* **Operating System**: Windows 10/11, Linux (Ubuntu 24.04+ / Arch), macOS (Apple Silicon / Intel).
* **JDK Distribution**: Eclipse Adoptium Temurin OpenJDK 25 (`jdk-25.0.3+` or latest build).
* **Environment Variable**: Set `JAVA_HOME` pointing to your JDK 25 directory.

```bash
# Verify Java version
java -version
# Expected output: openjdk version "25" ...
```

---

## 3. Step-by-Step Build Workflow

```
[ Clone Repository ]
         |
         v
[ Navigate to Target Subproject Directory ]
  - Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster/
  - Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster/
         |
         v
[ Verify gradle.properties Configuration ]
         |
         v
[ Execute Unified Build Command ]
  ./gradlew build --no-daemon
         |
         v
[ Inspect Output Artifacts in build/libs/ ]
```

### Executing the Build:
Navigate into the desired version anchor subproject directory and run:

```bash
# For Linux / macOS:
./gradlew build --no-daemon

# For Windows PowerShell:
.\gradlew build --no-daemon
```

Artifacts are generated in `build/libs/`:
* Standard binary: `combat-cooldown-adjuster-1.0.1+<mc_version>.jar`
* Sources jar: `combat-cooldown-adjuster-1.0.1+<mc_version>-sources.jar`

---

## 4. Subproject Architecture & Loom Settings

The repository utilizes isolated version anchor workspaces. Each subproject defines its Loom configuration in `gradle.properties` to ensure smooth compilation against modern snapshot dependencies:

```properties
org.gradle.parallel=false
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true
```

### Dependency Matrix across Anchors:
* **Version 26.2 (`gradle.properties`)**:
  ```properties
  minecraft_version=26.1.2
  fabric_version=0.145.4+26.1.2
  fabric_loader_version=0.19.1
  dasik_library_version=1.6.9+build.24
  ```
* **Version 26.3 (`gradle.properties`)**:
  ```properties
  minecraft_version=26.3-snapshot-6
  fabric_version=0.156.1+26.3
  fabric_loader_version=0.19.3
  dasik_library_version=1.8.36
  ```

---

## 5. Mixin Architecture & Purity Standard

Combat Cooldown Adjuster adheres strictly to the **Mixin Purity Standard**:
1. **Zero Logic in Mixin Classes**: `PlayerMixin.java` contains zero business calculations, string formatting, or particle loops.
2. **Static Utility Delegations**: All mathematical calculations, tag inspections, and particle/sound emissions are encapsulated inside `CCAHooks.java`.
3. **Cancellation Safety**: Injections use explicit `@At("HEAD")` callback checkpoints with `cancellable = true` when overriding return values or halting execution flow.

### Injected Target Signatures in `PlayerMixin.java`:
```java
// 1. Override weapon cooldown delay
@Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("HEAD"), cancellable = true)
private void cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir) {
    Player player = (Player) (Object) this;
    ItemStack stack = this.getMainHandItem();
    int ticks = CCAHooks.getCooldownTicks(player, stack);
    if (ticks >= 0) {
        cir.setReturnValue((float) ticks);
    }
}

// 2. Prevent attack meter reset on hotbar item switch
@Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"), cancellable = true)
private void cca$preventSwapReset(CallbackInfo ci) {
    Player player = (Player) (Object) this;
    if (CombatRules.getBoolean(player.level(), CombatRules.PREVENT_SWAP_RESET)) {
        ci.cancel();
    }
}

// 3. Inject Combat Juice particle and sound feedback
@Inject(method = "attack", at = @At("HEAD"))
private void cca$applyJuice(Entity target, CallbackInfo ci) {
    Player player = (Player) (Object) this;
    CCAHooks.applyCombatJuice(player, target);
}
```

---

## 6. Addon Developer & Data-Driven Extension Hooks

Addon creators and modpack developers can extend weapon categories without writing Java code by utilizing the Fabric/Common Conventions item tag system:

### Spear Integration via `#c:spears`:
Add your mod's spear items into `data/c/tags/item/spears.json`:
```json
{
  "replace": false,
  "values": [
    "examplemod:iron_spear",
    "examplemod:diamond_spear"
  ]
}
```
Combat Cooldown Adjuster automatically routes all items matching `#c:spears` directly to `ig:spear_cooldown_ticks` (default: 6 ticks).

---

## 7. Related Documentation Links
* Return to the [[Main Wiki Portal|Home]].
* Read the [[26.2 Architecture & Mixins|26.2-Architecture-and-Mixins]].
* Read the [[26.3 Architecture & Mixins|26.3-Architecture-and-Mixins]].
* Consult the [[Version Compatibility Matrix|Version-Compatibility]].
