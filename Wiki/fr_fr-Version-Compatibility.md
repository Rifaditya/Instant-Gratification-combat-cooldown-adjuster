# 📊 Matrice de Compatibilité des Versions

> 📌 **Clause de Non-Responsabilité relative au Dépôt Source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

## 1. Spécifications Techniques Officielles
| Paramètre | Spécification Technique |
| :--- | :--- |
| **Identifiant du Mod** | `combat-cooldown-adjuster` |
| **Collection du Mod** | Instant Gratification (IG) |
| **Versions Fabric Prises en Charge** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Plateforme Java** | OpenJDK 25 (Hotspot 64-bit) |
| **Chaîne de Compilation** | Gradle 9.3+ with Fabric Loom |
| **Norme d'Architecture** | Politique 1 Jar 1 Version |
| **Statut de l'API Publique** | Mod Autonome (Utilise l'API DasikLibrary) |

---

## 2. Matrice de Compatibilité des Versions
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 Version Standard |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 Branche Snapshot Récente |

---

## 3. Politique 1 Jar 1 Version vs Bibliothèques Universelles

### Fichiers Binaires Dédiés
Contrairement aux bibliothèques partagées, les mods injectant du bytecode directement dans `net.minecraft.world.entity.player.Player` requièrent une vérification rigoureuse à la compilation face aux correspondances Mojang exactes.
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### Intégration Universelle avec DasikLibrary
Combat Cooldown Adjuster s'appuie sur **DasikLibrary** pour l'enregistrement dynamique des GameRules (`DynamicGameRuleManager`), garantissant :
1. Une compatibilité totale avec la sérialisation des règles serveur.
2. L'autocomplétion dynamique par tabulation pour `/gamerule`.
3. La sécurité du chargeur de classes côté client évitant tout crash.

---

## 4. Procédure d'Installation et Prérequis

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

### Liste de Vérification :
1. Vérifier que Java est OpenJDK 25 Hotspot (ex. Eclipse Adoptium `jdk-25.0.3+`).
2. Vérifier la présence de `fabric-api` dans le dossier `mods`.
3. Vérifier la présence de `dasik-library` dans le dossier `mods`.
4. Lancer le jeu et repérer dans les logs :
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. Liens de Documentation Associés
* [[Retour au Portail Principal du Wiki|fr_fr-Home]]
* [[26.2 Mécaniques de Temps de Recharge des Armes|fr_fr-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 Mécaniques de Temps de Recharge des Armes|fr_fr-26.3-Weapon-Cooldown-Mechanics]]
* [[Dépannage et FAQ|fr_fr-Troubleshooting-and-FAQ]]
