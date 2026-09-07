# 🛠️ Environnement Développeur & Outils (Minecraft 26.2)

> 📌 **Clause de Non-Responsabilité relative au Dépôt Source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

## 1. Spécifications Techniques Officielles
| Paramètre | Spécification Technique |
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

## 2. Prérequis de l'Espace de Travail

1. **Install OpenJDK 25**: Download and install Eclipse Adoptium Temurin OpenJDK 25.
2. **Verify Installation**:
   ```bash
   java -version
   ```

---

## 3. Processus de Compilation Étape par Étape

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

## 4. Architecture du Sous-projet & Paramètres Loom

```properties
minecraft_version=26.1.2
fabric_version=0.145.4+26.1.2
fabric_loader_version=0.19.1
dasik_library_version=1.6.9+build.24
```

---

## 🔗 Liens de Documentation Associés
* [[Retour au Portail Minecraft 26.2|fr_fr-26.2-Home]]
* [[26.2 Architecture et Analyse des Mixins|fr_fr-26.2-Architecture-and-Mixins]]
* [[Configuration Développeur & Compilation|fr_fr-Developer-Setup-and-Building]]
