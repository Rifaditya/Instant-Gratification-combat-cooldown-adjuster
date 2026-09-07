# ⚔️ Portail de Documentation Minecraft 26.3

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Clause de Non-Responsabilité relative au Dépôt Source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

Bienvenue sur le portail de documentation technique de **Instant Gratification: Combat Cooldown Adjuster** pour **Minecraft 26.3** (cible `26.3-snapshot-6`) ! Tous les guides correspondent fidèlement à cette version.

---

## 🧭 Matrice de Navigation Minecraft 26.3

| Fonctionnalité / Sous-système | Description | Page Wiki Dédiée |
| :--- | :--- | :--- |
| **Mécaniques de Temps de Recharge des Armes** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.3 Mécaniques de Temps de Recharge des Armes|fr_fr-26.3-Weapon-Cooldown-Mechanics]] |
| **Impact Visuel et Retour Audio en Combat** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.3 Impact Visuel et Retour Audio en Combat|fr_fr-26.3-Combat-Juice-and-Audio-Feedback]] |
| **Configuration et Matrice des GameRules** | Complete reference matrix of all 9 namespaced GameRules | [[26.3 Configuration et Matrice des GameRules|fr_fr-26.3-Configuration-and-GameRules]] |
| **Architecture et Analyse des Mixins** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.3 Architecture et Analyse des Mixins|fr_fr-26.3-Architecture-and-Mixins]] |
| **Environnement Développeur & Outils** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.3 Environnement Développeur & Outils|fr_fr-26.3-Developer-Setup-and-Building]] |

---

## 📊 Spécifications Techniques Officielles

| Paramètre | Spécification Technique |
| :--- | :--- |
| **Minecraft Release Target** | `26.3-snapshot-6` |
| **Mod SemVer Release** | `1.0.1+26.3` |
| **Fabric Loader Requirement** | `0.19.3` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.8.36` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ Points Clés des Sous-systèmes

1. **Cadence d'Arme Sub-Tick (Sub-Tick Weapon Timing)** :
Remplacement direct du délai par des ticks entiers ($T_{\text{{delay}}}$). Épées : 4 ticks (5.0 coups/s), haches : 8 ticks (2.5 coups/s), houes : 1 tick (20.0 coups/s). $T = 0$ débloque le spam click rapide façon 1.8.

2. **Fluidité du Changement d'Objet (Hotbar Swap Agility)** :
Activer `ig:prevent_item_swap_cooldown` supprime la réinitialisation de la jauge lors du changement de sélection dans la barre d'action.

3. **Retour de Frappe Multisensoriel (Multi-Sensory Combat Juice)** :
Les coups à plus de 80% de charge déclenchent des particules critiques doubles et une montée dynamique de la hauteur du son (de $1.0\times$ à $1.4\times$).

---

## 🔗 Portails Principaux
* [[Retour au Portail Principal du Wiki|fr_fr-Home]]
* [[Portail de Documentation Minecraft 26.2|fr_fr-26.2-Home]]
* [[Matrice de Compatibilité des Versions|fr_fr-Version-Compatibility]]
* [[Dépannage et FAQ|fr_fr-Troubleshooting-and-FAQ]]
