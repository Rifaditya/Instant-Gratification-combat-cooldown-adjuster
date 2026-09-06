# ⚔️ Instant Gratification: Combat Cooldown Adjuster — Wiki Français

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Avertissement relatif à la source du dépôt** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

Bienvenue sur le wiki technique officiel de **Combat Cooldown Adjuster** pour Minecraft Fabric ! Ce mod élimine la lenteur artificielle des combats introduite dans Minecraft 1.9+, rétablissant un rythme d'attaque ultra-réactif avec des délais configurables en ticks et des retours sensoriels intenses.

---

## 🧭 Portails par Version

Conformément à la règle **1 Jar 1 Version**, chaque version dispose de sa propre documentation isolée :

| Version de Minecraft | Version du Mod | Fabric Loader | Portail de Documentation |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 Portail Minecraft 26.2|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 Portail Minecraft 26.3|26.3-Home]] |

---

## ⚡ Mécanismes Fondamentaux

1. **Remplacement Catégoriel du Délai en Ticks** :
   Définition directe des ticks de recharge selon les tags d'objets (`#minecraft:swords`, `#minecraft:axes`, `#c:spears`). Épées : 4 ticks (5 attaques/s), Haches : 8 ticks, Houes : 1 tick (20 attaques/s). Une valeur de `0` réactive le combat rapide instantané façon Minecraft 1.8 !
2. **Agilité de la Barre Rapide (Swap Agility)** :
   La règle `ig:prevent_item_swap_cooldown` empêche la réinitialisation de la jauge d'attaque lors du changement d'objet en main.
3. **Dynamisme Sensoriel (Combat Juice)** :
   Toute frappe avec plus de 80% de charge ($S > 0.8$) produit des gerbes de particules critiques et fait varier la hauteur du son d'attaque de $1.0\times$ à $1.4\times$.
4. **GameRules Dynamiques via DasikLibrary** :
   Les 9 paramètres sont éditables directement en jeu avec `/gamerule` sans redémarrage.

---

## 📊 Matrice des GameRules

| Règle | Type | Défaut | Description |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | Entier | `4` | Délai de recharge pour les épées en ticks (0.2 s) |
| `ig:axe_cooldown_ticks` | Entier | `8` | Délai de recharge pour les haches (0.4 s) |
| `ig:pickaxe_cooldown_ticks` | Entier | `4` | Délai de recharge pour les pioches |
| `ig:shovel_cooldown_ticks` | Entier | `2` | Délai de recharge pour les pelles |
| `ig:hoe_cooldown_ticks` | Entier | `1` | Délai de recharge pour les houes |
| `ig:spear_cooldown_ticks` | Entier | `6` | Délai de recharge pour les lances (`#c:spears`) |
| `ig:generic_cooldown_ticks` | Entier | `4` | Délai par défaut pour les poings et objets divers |
| `ig:prevent_item_swap_cooldown` | Booléen | `true` | Ne pas réinitialiser la jauge lors d'un changement d'objet |
| `ig:enable_combat_juice` | Booléen | `true` | Activer particules et variation acoustique d'impact |

---

## ⚖️ Licence & Crédits
* **Auteur** : Dasik (Rifaditya)
* **Licence** : GNU General Public License v3.0 (GPLv3)
* **Accueil** : [[Retour au portail en anglais|Home]]
