# 📊 Version Compatibility Matrix

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

## 1. Official Infobox
| Parameter | Technical Specification |
| :--- | :--- |
| **Mod Identifier** | `combat-cooldown-adjuster` |
| **Mod Collection** | Instant Gratification (IG) |
| **Supported Fabric Anchors** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Build Toolchain** | Gradle 9.3+ with Fabric Loom |
| **Architecture Standard** | 1 Jar 1 Version Policy |
| **Public API Status** | Standalone Mod (Consumes DasikLibrary API) |

---

## 2. Multi-Version Lifecycle & Compatibility Matrix

Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 Standard Anchor |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 Modern Lead |

---

## 3. The 1 Jar 1 Version Policy vs. Universal Library Bounds

### Dedicated Mod Binaries
Unlike shared libraries that export pure computational APIs across wide bounds, mods that inject bytecode directly into `net.minecraft.world.entity.player.Player` require strict compile-time verification against exact Mojang mappings and intermediate descriptors.
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### Universal DasikLibrary Integration
Combat Cooldown Adjuster relies on **DasikLibrary** for dynamic runtime GameRule registration (`DynamicGameRuleManager`). DasikLibrary follows an open version bound architecture (`>=26.1.2-`), guaranteeing:
1. Seamless backward and forward compatibility with server GameRule serialization.
2. In-game `/gamerule` dynamic tab completion across all game instances.
3. Client-side classloader safety (server-only evaluations guard against client crashes).

---

## 4. Installation & Prerequisites Workflow

```
[ Download Compatible Mod Jar ]
               |
               +---> Check Minecraft Target (26.2 vs 26.3)
               |
[ Verify Dependencies ]
       |
       +---> Fabric Loader (>=0.19.1 for 26.2 | >=0.19.3 for 26.3)
       +---> Fabric API (matching MC release)
       +---> DasikLibrary (>=1.6.9+ for 26.2 | >=1.8.36 for 26.3)
               |
[ Deploy to .minecraft/mods/ ]
               |
[ Launch Game with JDK 25 ]
```

### Verification Checklist:
1. Ensure Java runtime is OpenJDK 25 Hotspot (e.g. Eclipse Adoptium `jdk-25.0.3+`).
2. Verify `fabric-api` is present in your mods folder.
3. Verify `dasik-library` is present in your mods folder.
4. Launch the game and inspect the log for:
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. Related Documentation Links
* Return to the [[Main Wiki Portal|Home]].
* Read the [[26.2 Weapon Cooldown Mechanics|26.2-Weapon-Cooldown-Mechanics]].
* Read the [[26.3 Weapon Cooldown Mechanics|26.3-Weapon-Cooldown-Mechanics]].
* Consult the [[Troubleshooting & FAQ Guide|Troubleshooting-and-FAQ]].
