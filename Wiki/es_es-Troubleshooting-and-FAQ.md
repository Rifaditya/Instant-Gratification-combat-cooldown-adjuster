# 🔧 Solución de Problemas y FAQ

> 📌 **Descargo de Responsabilidad del Código Fuente del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, el cual puede incluir commits recientes no publicados o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

## 1. Ficha Técnica Oficial
| Parámetro | Detalles de Diagnóstico |
| :--- | :--- |
| **Subsistema Objetivo** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **GameRules de Control** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **Punto de Inyección Clave** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **Dependencia Principal** | `dasik-library` (Dynamic GameRule Manager) |
| **Espacio de Nombres del Log** | `combat-cooldown-adjuster` |

---

## 2. Diagrama de Flujo de Diagnóstico

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

## 3. Guía de Diagnóstico Paso a Paso

### Fase 1: Calibrar los Ticks de las Armas
1. Abre el chat del juego o la consola del servidor.
2. Consulta el valor de ticks activo para tu tipo de arma:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. Si devuelve el valor predeterminado `4`, la frecuencia de ataque es:
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ ataques/seg}}
4. Para probar el modo de golpe continuo instantáneo (estilo Minecraft 1.8), ejecuta:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. Los ataques se registrarán de forma instantánea sin pausa de recarga.

### Fase 2: Diagnosticar la Agilidad de Cambio de Barra Rápida
1. Equipa una Espada en la casilla 1 y un Hacha en la casilla 2.
2. Golpea con la espada para iniciar la recarga.
3. Cambia inmediatamente al hacha presionando `2`.
4. Si `ig:prevent_item_swap_cooldown` está en `true`, el medidor de ataque **no** se reiniciará a cero.
5. Si se reinicia, ejecuta:
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### Fase 3: Optimización de Partículas y Audio
1. Combat Juice se activa con una carga de ataque superior a $0.8$ (80%).
2. Si equipos de gama baja experimentan tirones, puedes desactivar el efecto en el servidor:
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. Esto desactiva por completo las llamadas a `sendParticles` y `playSound` en `CCAHooks.applyCombatJuice`.

---

## 4. Tabla de Calibración Matemática

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

## 5. Preguntas Frecuentes (FAQ)

### Q1: ¿Establecer el retraso en 0 ticks rompe el cálculo de daño de Minecraft vainilla?
**No.** Vainilla calcula la escala como $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$. Cuando $T = 0$, nuestro Mixin devuelve $0.0$, forzando a la lógica vainilla a evaluar $S(t) = 1.0$, aplicando el 100% del daño base del arma.

### Q2: ¿Por qué las armas de otros mods sin etiquetas atacan a 4 ticks?
Si un objeto modificado no incluye las etiquetas oficiales ni `#c:spears`, `CCAHooks.getCooldownTicks` recurre a `CombatRules.GENERIC_TICKS` (predeterminado: 4 ticks). Puedes modificarlo con:
```mcfunction
/gamerule ig:generic_cooldown_ticks <valor>
```

### Q3: ¿Produce este mod retraso en multijugador o desincronización?
Combat Cooldown Adjuster opera con autoridad del servidor. En servidores dedicados, `PlayerMixin` se inyecta en la entidad del jugador del servidor, garantizando cálculos autoritativos sin lag de red.

### Q4: ¿Se guardan las GameRules en el archivo del mundo?
**Sí.** Las 9 reglas se guardan directamente en el archivo `level.dat` a través del sistema vainilla gestionado por DasikLibrary.

---

## 6. Enlaces de Documentación Relacionados
* [[Volver al Portal Principal de la Wiki|es_es-Home]]
* [[26.2 Configuración y Matriz de GameRules|es_es-26.2-Configuration-and-GameRules]]
* [[26.3 Configuración y Matriz de GameRules|es_es-26.3-Configuration-and-GameRules]]
* [[Configuración de Desarrollador y Compilación|es_es-Developer-Setup-and-Building]]
