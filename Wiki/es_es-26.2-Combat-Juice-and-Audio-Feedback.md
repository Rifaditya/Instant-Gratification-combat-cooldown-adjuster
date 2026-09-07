# 💥 Retroalimentación de Combate y Efectos de Audio (Minecraft 26.2)

> 📌 **Descargo de Responsabilidad del Código Fuente del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, el cual puede incluir commits recientes no publicados o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

## 1. Ficha Técnica Oficial
| Parámetro | Especificación Técnica |
| :--- | :--- |
| **Subsystem Name** | Sistema de Retroalimentación Sensorial Dinámica |
| **Controlling GameRule** | `ig:enable_combat_juice` (Default: `true`) |
| **Trigger Threshold** | Attack Strength Scale $S > 0.8$ ($80\%$) |
| **Particle Types** | `ParticleTypes.CRIT`, `ParticleTypes.ENCHANTED_HIT` |
| **Sound Event** | `SoundEvents.PLAYER_ATTACK_STRONG` |
| **Sound Category** | `SoundSource.PLAYERS` |
| **Audio Pitch Range** | $1.0\times$ to $1.4\times$ (Dynamic Charge Shift) |
| **Bytecode Hook** | `PlayerMixin.cca$applyJuice` $\to$ `CCAHooks.applyCombatJuice` |

---

## 2. Guía de Juego en Supervivencia
1. **Carga**: Cargar el ataque por encima del 80% ($S > 0.8$, solo 3.2 ticks).
2. **Golpe**: Clic izquierdo para golpear la entidad objetivo.
3. **Partículas**: Emisión de 10 partículas Crit y 5 partículas Enchanted Hit en el centro del objetivo.
4. **Elevación de Tono**: Sonido `SoundEvents.PLAYER_ATTACK_STRONG` con tono de hasta $1.4\times$.
5. **Ajuste**: Se puede desactivar con `/gamerule ig:enable_combat_juice false`.

---

## 3. Fórmulas Matemáticas y Ecuaciones

### Condición de Activación
$$\text{JuiceEnabled} = \text{true} \quad \land \quad S_{\text{strength}} > 0.8$$

### Fórmula del Cambio de Tono de Audio
$$\text{Pitch} = 1.0 + (S_{\text{strength}} - 0.8) \times 2.0$$

Análisis de Valores Límites:
* Umbral mínimo ($S = 0.8$): $\text{{Pitch}} = 1.00$
* Carga media ($S = 0.9$): $\text{{Pitch}} = 1.20$
* Carga máxima ($S = 1.0$): $\text{{Pitch}} = 1.40$

### Parámetros de Emisión de Partículas
* **`ParticleTypes.CRIT`**: Count $N = 10$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.
* **`ParticleTypes.ENCHANTED_HIT`**: Count $N = 5$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.

---

## 4. Diagramas ASCII y Estados

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

## 5. Esquemas de Etiquetas de Objetos y SNBT

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

## 6. Matriz de Referencia Completa

| Charge Scale ($S$) | Charge Status | Particle Burst | Sound Event | Audio Pitch |
| :---: | :--- | :--- | :--- | :---: |
| **$0.00 - 0.80$** | Incomplete Charge | None | Default vanilla hit | Vanilla |
| **$0.81$** | Threshold Reached | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.02\times$ |
| **$0.85$** | Strong Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.10\times$ |
| **$0.90$** | High-Power Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.20\times$ |
| **$0.95$** | Near-Max Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.30\times$ |
| **$1.00$** | Full Max Strike | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.40\times$ |

---

## 7. Análisis de Inyecciones de Mixin

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

## 🔗 Enlaces de Documentación Relacionados
* [[Volver al Portal de Minecraft 26.2|es_es-26.2-Home]]
* [[26.2 Mecánicas de Enfriamiento de Armas|es_es-26.2-Weapon-Cooldown-Mechanics]]
* [[26.2 Configuración y Matriz de GameRules|es_es-26.2-Configuration-and-GameRules]]
* [[26.2 Arquitectura y Análisis de Mixins|es_es-26.2-Architecture-and-Mixins]]
