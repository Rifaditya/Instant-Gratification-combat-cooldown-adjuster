# 🛠️ Configuration Développeur & Compilation

> 📌 **Clause de Non-Responsabilité relative au Dépôt Source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

## 1. Spécifications Techniques Officielles
| Paramètre | Spécification Technique |
| :--- | :--- |
| **Chaîne de Compilation** | Gradle 9.3+ with Fabric Loom |
| **Plateforme Java** | OpenJDK 25 (Hotspot 64-bit) |
| **Collection du Mod** | Multi-Version Anchor Subprojects (`26.2` & `26.3`) |
| **Bytecode Injector** | SpongePowered Mixin 0.8+ |
| **Dépendance Principale** | Fabric API, DasikLibrary (`DynamicGameRuleManager`) |
| **Compilation Flag** | `./gradlew build --no-daemon` |

---

## 2. Prérequis de l'Espace de Travail

Building Combat Cooldown Adjuster from source requires an active JDK 25 installation.

```bash
# Verify Java version
java -version
# Expected output: openjdk version "25" ...
```

---

## 3. Processus de Compilation Étape par Étape

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

## 4. Architecture du Sous-projet & Paramètres Loom

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

## 5. Architecture Mixin & Norme de Pureté
Combat Cooldown Adjuster respecte la **Norme de Pureté Mixin** :
1. **Zéro Logique Métier dans les Mixins** : `PlayerMixin.java` ne contient aucun calcul complexe.
2. **Délégation Statique** : Tous les calculs mathématiques et effets sont centralisés dans `CCAHooks.java`.
3. **Sécurité d'Annulation** : Injections explicites en `@At("HEAD")` avec `cancellable = true`.

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

## 6. Crochets d'Extension pour Addons
Intégration des Lances via `#c:spears` :
Ajoutez vos objets dans `data/c/tags/item/spears.json` pour leur appliquer automatiquement les règles de lances.

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

## 7. Liens de Documentation Associés
* [[Retour au Portail Principal du Wiki|fr_fr-Home]]
* [[26.2 Architecture et Analyse des Mixins|fr_fr-26.2-Architecture-and-Mixins]]
* [[26.3 Architecture et Analyse des Mixins|fr_fr-26.3-Architecture-and-Mixins]]
* [[Matrice de Compatibilité des Versions|fr_fr-Version-Compatibility]]
