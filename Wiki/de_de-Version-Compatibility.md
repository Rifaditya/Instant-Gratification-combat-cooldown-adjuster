# 📊 Versionskompatibilitätsmatrix

> 📌 **Quellcode-Repository-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Release-Builds auf CurseForge und Modrinth enthalten kann.

## 1. Offizielle technische Spezifikation
| Parameter | Technische Spezifikation |
| :--- | :--- |
| **Mod-Identifikator** | `combat-cooldown-adjuster` |
| **Mod-Kollektion** | Instant Gratification (IG) |
| **Unterstützte Fabric-Versionen** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Java-Laufzeitumgebung** | OpenJDK 25 (Hotspot 64-bit) |
| **Build-Toolchain** | Gradle 9.3+ with Fabric Loom |
| **Architektur-Standard** | 1 Jar 1 Version Richtlinie |
| **Öffentlicher API-Status** | Eigenständige Mod (nutzt DasikLibrary API) |

---

## 2. Versionskompatibilitätsmatrix
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 Standard-Anchor |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 Snapshot-Zweig |

---

## 3. 1 Jar 1 Version Richtlinie vs. Universelle Bibliotheken

### Dedizierte Mod-Dateien
Im Gegensatz zu universellen Rechenbibliotheken müssen Mods, die Bytecode direkt in `net.minecraft.world.entity.player.Player` injizieren, zur Kompilierzeit exakt gegen die Mojang-Mappings der Zielversion geprüft werden.
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### Universelle DasikLibrary-Integration
Combat Cooldown Adjuster nutzt **DasikLibrary** für die dynamische GameRule-Registrierung (`DynamicGameRuleManager`):
1. Vollständige Vor- und Rückwärtskompatibilität mit Server-Serialisierungen.
2. Dynamische Tab-Vervollständigung im Spiel für `/gamerule`.
3. Client-Klassenlader-Sicherheit zur Vermeidung von Abstürzen.

---

## 4. Installationsablauf & Voraussetzungen

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

### Überprüfungsliste:
1. Sicherstellen, dass OpenJDK 25 Hotspot installiert ist (z. B. Eclipse Adoptium `jdk-25.0.3+`).
2. Prüfen, ob `fabric-api` im `mods`-Ordner liegt.
3. Prüfen, ob `dasik-library` im `mods`-Ordner liegt.
4. Spiel starten und Protokoll prüfen:
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. Weiterführende Links
* [[Zurück zum Hauptportal des Wikis|de_de-Home]]
* [[26.2 Waffen-Abklingzeit-Mechanik|de_de-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 Waffen-Abklingzeit-Mechanik|de_de-26.3-Weapon-Cooldown-Mechanics]]
* [[Fehlerbehebung & FAQ|de_de-Troubleshooting-and-FAQ]]
