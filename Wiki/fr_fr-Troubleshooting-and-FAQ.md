# 🔧 Dépannage et FAQ

> 📌 **Clause de Non-Responsabilité relative au Dépôt Source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

## 1. Spécifications Techniques Officielles
| Paramètre | Détails de Diagnostic |
| :--- | :--- |
| **Sous-système Cible** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **GameRules de Contrôle** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **Point d'Injection Clé** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **Dépendance Principale** | `dasik-library` (Dynamic GameRule Manager) |
| **Espace de Noms des Logs** | `combat-cooldown-adjuster` |

---

## 2. Organigramme de Diagnostic

```
           [ Combat Timing Issue Detected ]
                          |
                          v
         Is weapon delay different from expected?
               /                         \
             YES                          NO
             /                             \
    Check GameRule:                Does item swap reset ticker?
    /gamerule ig:<item>_ticks             /             \
    Verify item tag match                YES             NO
    (e.g. #minecraft:swords)             /                \
                                  Check GameRule:     Are particles / audio missing?
                                  ig:prevent_item_          /             \
                                  swap_cooldown = true    YES             NO
                                                          /                \
                                                Attack charge > 80%?     All systems
                                                Check ig:enable_juice    functioning!
```

---

## 3. Guide de Diagnostic Étape par Étape

### Phase 1 : Calibrer les Ticks d'Arme
1. Ouvrez le tchat du jeu ou la console du serveur.
2. Consultez la valeur active de ticks pour votre arme :
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. Si la valeur retournée est `4`, la cadence d'attaque est de :
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ attaques/sec}}
4. Pour tester le clic continu instantané (façon Minecraft 1.8), exécutez :
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. Les coups portés seront appliqués sans aucun délai de recharge.

### Phase 2 : Diagnostiquer la Rapidité de Changement d'Objet
1. Équipez une épée dans l'emplacement 1 et une hache dans le 2.
2. Donnez un coup avec l'épée.
3. Appuyez aussitôt sur la touche `2` pour passer à la hache.
4. Si `ig:prevent_item_swap_cooldown` est à `true`, la jauge d'attaque **ne sera pas** réinitialisée à zéro.
5. Si elle se réinitialise, exécutez :
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### Phase 3 : Optimisation des Particules et de l'Audio
1. Combat Juice se déclenche quand la charge dépasse $0.8$ (80%).
2. En cas de ralentissement sur les petites configurations, désactivez-le sur le serveur :
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. Cela coupe les appels à `sendParticles` et `playSound` dans `CCAHooks.applyCombatJuice`.

---

## 4. Table de Calibration Mathématique

| Tick Setting ($T$) | Attack Frequency ($f = 20/T$) | Attack Interval (ms) | Combat Feel Style |
| :---: | :---: | :---: | :--- |
| **`0`** | $\infty$ (20 TPS Engine Bound) | $0\text{ ms}$ (Instantaneous) | Pure 1.8 Click-Spam Combat |
| **`1`** | $20.0\text{ attacks/sec}$ | $50\text{ ms}$ | Ultra Hyper-Speed (Hoe Default) |
| **`2`** | $10.0\text{ attacks/sec}$ | $100\text{ ms}$ | Turbo Agility (Shovel Default) |
| **`4`** | $5.0\text{ attacks/sec}$ | $200\text{ ms}$ | Snappy Balanced Melee (Sword/Pickaxe Default) |
| **`6`** | $3.33\text{ attacks/sec}$ | $300\text{ ms}$ | Tactical Reach Cadence (Spear Default) |
| **`8`** | $2.5\text{ attacks/sec}$ | $400\text{ ms}$ | Heavy Impact Cleaving (Axe Default) |
| **`16`+** | $\le 1.25\text{ attacks/sec}$ | $\ge 800\text{ ms}$ | Vanilla 1.9+ Style Slow Paced Combat |

---

## 5. Foire Aux Questions (FAQ)

### Q1 : Définir le délai à 0 tick perturbe-t-il le calcul des dégâts vanilla ?
**Non.** Vanilla calcule la jauge avec $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$. Lorsque $T = 0$, notre Mixin renvoie $0.0$, forçant le moteur à évaluer immédiatement $S(t) = 1.0$. Chaque frappe inflige 100% des dégâts de base.

### Q2 : Pourquoi les armes d'autres mods sans tag attaquent-elles à 4 ticks ?
Si un objet tiers ne possède pas de tags vanilla ou `#c:spears`, `CCAHooks.getCooldownTicks` bascule sur `CombatRules.GENERIC_TICKS` (par défaut : 4 ticks). Modifiable via :
```mcfunction
/gamerule ig:generic_cooldown_ticks <valeur>
```

### Q3 : Ce mod crée-t-il de la latence ou des désynchronisations en multijoueur ?
Combat Cooldown Adjuster s'exécute avec l'autorité du serveur. Sur un serveur dédié, `PlayerMixin` est injecté sur l'entité côté serveur, conservant des calculs fiables sans latence réseau.

### Q4 : Les règles GameRules sont-elles conservées dans la sauvegarde du monde ?
**Oui.** Les 9 règles sont enregistrées directement dans le fichier `level.dat` du monde via DasikLibrary.

---

## 6. Liens de Documentation Associés
* [[Retour au Portail Principal du Wiki|fr_fr-Home]]
* [[26.2 Configuration et Matrice des GameRules|fr_fr-26.2-Configuration-and-GameRules]]
* [[26.3 Configuration et Matrice des GameRules|fr_fr-26.3-Configuration-and-GameRules]]
* [[Configuration Développeur & Compilation|fr_fr-Developer-Setup-and-Building]]
