# 🛠️ Entwickler-Setup & Toolchain (Minecraft 26.2)

> 📌 **Quellcode-Repository-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Release-Builds auf CurseForge und Modrinth enthalten kann.

## 1. Offizielle technische Spezifikation
| Parameter | Technische Spezifikation |
| :--- | :--- |
| **Minecraft Target** | `26.1.2` |
| **Target Subproject** | `Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster/` |
| **Mod SemVer** | `1.0.1+26.2` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Build Automation** | Gradle 9.3+ with Fabric Loom |
| **Fabric Loader** | `0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.6.9+build.24` |

---

## 2. Entwicklungsumgebung & Voraussetzungen

1. **Install OpenJDK 25**: Download and install Eclipse Adoptium Temurin OpenJDK 25.
2. **Verify Installation**:
   ```bash
   java -version
   ```

---

## 3. Schritt-für-Schritt-Build-Ablauf

```bash
# Navigate to subproject directory
cd "Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster"

# Run non-daemon build
./gradlew build --no-daemon
```

### Output Binaries:
* `combat-cooldown-adjuster-1.0.1+26.2.jar`
* `combat-cooldown-adjuster-1.0.1+26.2-sources.jar`

---

## 4. Subprojekt-Architektur & Loom-Einstellungen

```properties
minecraft_version=26.1.2
fabric_version=0.145.4+26.1.2
fabric_loader_version=0.19.1
dasik_library_version=1.6.9+build.24
```

---

## 🔗 Weiterführende Links
* [[Zurück zum Minecraft 26.2 Portal|de_de-26.2-Home]]
* [[26.2 Architektur & Mixin-Analyse|de_de-26.2-Architecture-and-Mixins]]
* [[Entwickler-Setup & Build-Anleitung|de_de-Developer-Setup-and-Building]]
