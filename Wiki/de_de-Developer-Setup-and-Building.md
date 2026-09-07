# 🛠️ Entwickler-Setup & Build-Anleitung

> 📌 **Quellcode-Repository-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Release-Builds auf CurseForge und Modrinth enthalten kann.

## 1. Offizielle technische Spezifikation
| Parameter | Technische Spezifikation |
| :--- | :--- |
| **Build-Toolchain** | Gradle 9.3+ with Fabric Loom |
| **Java-Laufzeitumgebung** | OpenJDK 25 (Hotspot 64-bit) |
| **Mod-Kollektion** | Multi-Version Anchor Subprojects (`26.2` & `26.3`) |
| **Bytecode Injector** | SpongePowered Mixin 0.8+ |
| **Hauptabhängigkeit** | Fabric API, DasikLibrary (`DynamicGameRuleManager`) |
| **Compilation Flag** | `./gradlew build --no-daemon` |

---

## 2. Entwicklungsumgebung & Voraussetzungen

Building Combat Cooldown Adjuster from source requires an active JDK 25 installation.

```bash
# Verify Java version
java -version
# Expected output: openjdk version "25" ...
```

---

## 3. Schritt-für-Schritt-Build-Ablauf

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

## 4. Subprojekt-Architektur & Loom-Einstellungen

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

## 5. Mixin-Architektur & Reinheitsstandard
Combat Cooldown Adjuster befolgt strikt den **Mixin-Reinheitsstandard**:
1. **Keine Logik in Mixin-Klassen**: `PlayerMixin.java` enthält keine Berechnungen.
2. **Statische Delegierung**: Alle Logik und Effekte liegen in `CCAHooks.java`.
3. **Sicherheit bei Abbrüchen**: Eindeutige `@At("HEAD")`-Injektionspunkte mit `cancellable = true`.

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

## 6. Erweiterungsschnittstellen für Addons
Speer-Integration über `#c:spears`:
Füge Items einfach in `data/c/tags/item/spears.json` ein, um Speer-Regeln anzuwenden.

```json
{
  "replace": false,
  "values": [
    "examplemod:iron_spear",
    "examplemod:diamond_spear"
  ]
}
```

---

## 7. Weiterführende Links
* [[Zurück zum Hauptportal des Wikis|de_de-Home]]
* [[26.2 Architektur & Mixin-Analyse|de_de-26.2-Architecture-and-Mixins]]
* [[26.3 Architektur & Mixin-Analyse|de_de-26.3-Architecture-and-Mixins]]
* [[Versionskompatibilitätsmatrix|de_de-Version-Compatibility]]
