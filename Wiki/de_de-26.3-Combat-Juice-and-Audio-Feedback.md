# 💥 Kampffeedback & Audioeffekte (Minecraft 26.3)

> 📌 **Quellcode-Repository-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Release-Builds auf CurseForge und Modrinth enthalten kann.

## 1. Offizielle technische Spezifikation
| Parameter | Technische Spezifikation |
| :--- | :--- |
| **Subsystem Name** | Dynamisches sensorisches Kampffeedback-System |
| **Controlling GameRule** | `ig:enable_combat_juice` (Default: `true`) |
| **Trigger Threshold** | Attack Strength Scale $S > 0.8$ ($80\%$) |
| **Particle Types** | `ParticleTypes.CRIT`, `ParticleTypes.ENCHANTED_HIT` |
| **Sound Event** | `SoundEvents.PLAYER_ATTACK_STRONG` |
| **Sound Category** | `SoundSource.PLAYERS` |
| **Audio Pitch Range** | $1.0\times$ to $1.4\times$ (Dynamic Charge Shift) |
| **Bytecode Hook** | `PlayerMixin.cca$applyJuice` $\to$ `CCAHooks.applyCombatJuice` |

---

## 2. Überlebens-Gameplay & Bedienung
1. **Aufladung**: Ladebalken über 80% bringen ($S > 0.8$, dauert nur 3.2 Ticks).
2. **Angriff**: Linksklick auf das Ziel führt `Player#attack` aus.
3. **Partikel**: 10 kritische und 5 magische Partikel am Zielzentrum.
4. **Audio-Tonhöhe**: `SoundEvents.PLAYER_ATTACK_STRONG` wird mit bis zu $1.4\times$ Tonhöhe abgespielt.
5. **Einstellung**: Jederzeit mit `/gamerule ig:enable_combat_juice false` abschaltbar.

---

## 3. Mathematische Formeln & Gleichungen

### Auslösebedingung
$$\text{JuiceEnabled} = \text{true} \quad \land \quad S_{\text{strength}} > 0.8$$

### Formel für die dynamische Tonhöhenverschiebung
$$\text{Pitch} = 1.0 + (S_{\text{strength}} - 0.8) \times 2.0$$

Grenzwertanalyse:
* Schwellenwert ($S = 0.8$): $\text{{Pitch}} = 1.00$
* Mittlere Ladung ($S = 0.9$): $\text{{Pitch}} = 1.20$
* Volle Ladung ($S = 1.0$): $\text{{Pitch}} = 1.40$

### Partikelparameter & Offsets
* **`ParticleTypes.CRIT`**: Count $N = 10$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.
* **`ParticleTypes.ENCHANTED_HIT`**: Count $N = 5$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.

---

## 4. ASCII-Ablaufdiagramme & Zustände

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

## 5. Gegenstands-Tags & SNBT-Schemata

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

## 6. Vollständige Referenztabelle

| Charge Scale ($S$) | Charge Status | Particle Burst | Sound Event | Audio Pitch |
| :---: | :--- | :--- | :--- | :---: |
| **$0.00 - 0.80$** | Incomplete Charge | None | Default vanilla hit | Vanilla |
| **$0.81$** | Threshold Reached | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.02\times$ |
| **$0.85$** | Strong Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.10\times$ |
| **$0.90$** | High-Power Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.20\times$ |
| **$0.95$** | Near-Max Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.30\times$ |
| **$1.00$** | Full Max Strike | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.40\times$ |

---

## 7. Entwickler- & Mixin-Analyse

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

## 🔗 Weiterführende Links
* [[Zurück zum Minecraft 26.3 Portal|de_de-26.3-Home]]
* [[26.3 Waffen-Abklingzeit-Mechanik|de_de-26.3-Weapon-Cooldown-Mechanics]]
* [[26.3 Konfiguration & GameRules-Matrix|de_de-26.3-Configuration-and-GameRules]]
* [[26.3 Architektur & Mixin-Analyse|de_de-26.3-Architecture-and-Mixins]]
