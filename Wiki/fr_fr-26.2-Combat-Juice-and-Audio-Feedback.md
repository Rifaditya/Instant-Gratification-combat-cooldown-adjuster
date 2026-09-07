# 💥 Impact Visuel et Retour Audio en Combat (Minecraft 26.2)

> 📌 **Clause de Non-Responsabilité relative au Dépôt Source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

## 1. Spécifications Techniques Officielles
| Paramètre | Spécification Technique |
| :--- | :--- |
| **Subsystem Name** | Système de Rétroaction Sensorielle Dynamique |
| **Controlling GameRule** | `ig:enable_combat_juice` (Default: `true`) |
| **Trigger Threshold** | Attack Strength Scale $S > 0.8$ ($80\%$) |
| **Particle Types** | `ParticleTypes.CRIT`, `ParticleTypes.ENCHANTED_HIT` |
| **Sound Event** | `SoundEvents.PLAYER_ATTACK_STRONG` |
| **Sound Category** | `SoundSource.PLAYERS` |
| **Audio Pitch Range** | $1.0\times$ to $1.4\times$ (Dynamic Charge Shift) |
| **Bytecode Hook** | `PlayerMixin.cca$applyJuice` $\to$ `CCAHooks.applyCombatJuice` |

---

## 2. Déroulement en Survie
1. **Montée en Charge** : La jauge dépasse 80% ($S > 0.8$, en seulement 3.2 ticks).
2. **Frappe** : Clic gauche sur la cible pour exécuter l'attaque.
3. **Éclats Visuels** : Émission de 10 particules Crit et 5 Enchanted Hit au centre de l'ennemi.
4. **Variation Audio** : Le son `SoundEvents.PLAYER_ATTACK_STRONG` monte jusqu'à $1.4\times$ de tonalité.
5. **Configuration** : Désactivable avec `/gamerule ig:enable_combat_juice false`.

---

## 3. Formules Mathématiques & Équations

### Condition de Déclenchement
$$\text{JuiceEnabled} = \text{true} \quad \land \quad S_{\text{strength}} > 0.8$$

### Formule de Modulation de Hauteur Audio
$$\text{Pitch} = 1.0 + (S_{\text{strength}} - 0.8) \times 2.0$$

Analyse des Valeurs Limites :
* Seuil inférieur ($S = 0.8$) : $\text{{Pitch}} = 1.00$
* Charge moyenne ($S = 0.9$) : $\text{{Pitch}} = 1.20$
* Charge maximale ($S = 1.0$) : $\text{{Pitch}} = 1.40$

### Paramètres d'Émission des Particules
* **`ParticleTypes.CRIT`**: Count $N = 10$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.
* **`ParticleTypes.ENCHANTED_HIT`**: Count $N = 5$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.

---

## 4. Diagrammes ASCII et Machine d'États

```
                 [ Player Attacks Target Entity ]
                                |
                                v
                   PlayerMixin.cca$applyJuice
                                |
                                v
                   CCAHooks.applyCombatJuice
                                |
                Is ig:enable_combat_juice true?
                       /                 \
                     YES                  NO
                     /                     \
        Get attackStrengthScale(0.5f)     (Return silently)
                     |
            Is attackStrength > 0.8?
                   /         \
                 YES          NO
                 /             \
      [ Emit Visual & Audio ]  (Return silently)
         |
         +--> ServerLevel.sendParticles(CRIT, count=10)
         +--> ServerLevel.sendParticles(ENCHANTED_HIT, count=5)
         +--> Calculate Pitch = 1.0 + (attackStrength - 0.8) * 2.0
         +--> Level.playSound(PLAYER_ATTACK_STRONG, volume=1.0, pitch)
```

---

## 5. Schémas des Tags d'Objets et SNBT

```json
{
  "sound": "minecraft:entity.player.attack.strong",
  "source": "PLAYERS",
  "volume": 1.0,
  "pitch_min": 1.0,
  "pitch_max": 1.4
}
```

---

## 6. Matrice de Référence Complète

| Charge Scale ($S$) | Charge Status | Particle Burst | Sound Event | Audio Pitch |
| :---: | :--- | :--- | :--- | :---: |
| **$0.00 - 0.80$** | Incomplete Charge | None | Default vanilla hit | Vanilla |
| **$0.81$** | Threshold Reached | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.02\times$ |
| **$0.85$** | Strong Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.10\times$ |
| **$0.90$** | High-Power Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.20\times$ |
| **$0.95$** | Near-Max Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.30\times$ |
| **$1.00$** | Full Max Strike | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.40\times$ |

---

## 7. Analyse des Points d'Injection Mixin

```java
public static void applyCombatJuice(Player player, Entity target) {
    if (!CombatRules.getBoolean(player.level(), CombatRules.ENABLE_JUICE)) return;

    float attackStrength = player.getAttackStrengthScale(0.5f);
    if (attackStrength > 0.8f) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY(0.5), target.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
            serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, target.getX(), target.getY(0.5), target.getZ(), 5, 0.1, 0.1, 0.1, 0.1);
        }
        
        float pitch = 1.0f + (attackStrength - 0.8f) * 2.0f;
        player.level().playSound(null, target.getX(), target.getY(), target.getZ(), 
            SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1.0f, pitch);
    }
}
```

---

## 🔗 Liens de Documentation Associés
* [[Retour au Portail Minecraft 26.2|fr_fr-26.2-Home]]
* [[26.2 Mécaniques de Temps de Recharge des Armes|fr_fr-26.2-Weapon-Cooldown-Mechanics]]
* [[26.2 Configuration et Matrice des GameRules|fr_fr-26.2-Configuration-and-GameRules]]
* [[26.2 Architecture et Analyse des Mixins|fr_fr-26.2-Architecture-and-Mixins]]
