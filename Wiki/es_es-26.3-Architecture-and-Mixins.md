# 🏗️ Arquitectura y Análisis de Mixins (Minecraft 26.3)

> 📌 **Descargo de Responsabilidad del Código Fuente del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, el cual puede incluir commits recientes no publicados o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

## 1. Ficha Técnica Oficial
| Parámetro | Especificación Técnica |
| :--- | :--- |
| **Namespace Package** | `net.instantgratification.combatcooldownadjuster` |
| **Bytecode Transformer** | SpongePowered Mixin 0.8+ |
| **Configuration File** | `combat-cooldown-adjuster.mixins.json` |
| **Injected Entity** | `net.minecraft.world.entity.player.Player` |
| **Logic Provider** | `net.instantgratification.combatcooldownadjuster.util.CCAHooks` |
| **GameRule Provider** | `net.instantgratification.combatcooldownadjuster.registry.CombatRules` |
| **Execution Complexity** | $O(1)$ constant time lookup per attack tick |

---

## 2. Guía de Juego en Supervivencia

```
[ Game / Server Startup ]
         |
         v
CombatCooldownAdjuster#onInitialize()
         |
         v
CombatRules#register()
         |
         v
Register 9 DynamicGameRules under "combat-cooldown-adjuster:combat_cooldown"
         |
======================== RUNTIME COMBAT LOOP ========================
         |
[ Player Attacks with Weapon ]
         |
         +--> PlayerMixin.cca$overrideAttackDelay
         |       |--> CCAHooks.getCooldownTicks(player, stack)
         |       |--> Matches Tag (#minecraft:swords, etc.)
         |       +--> Returns custom float tick delay (cir.setReturnValue)
         |
         +--> PlayerMixin.cca$applyJuice
                 |--> CCAHooks.applyCombatJuice(player, target)
                 |--> Checks ig:enable_combat_juice and charge > 0.8
                 +--> Emits ServerLevel particles & playSound
         |
[ Player Switches Hotbar Slot ]
         |
         +--> PlayerMixin.cca$preventSwapReset
                 |--> Checks ig:prevent_item_swap_cooldown
                 +--> Cancels ticker reset (ci.cancel())
```

---

## 3. Rutas Rápidas sin Asignación de Memoria (Zero-Allocation)
* **Verificación de Etiquetas**: `ItemStack#is(TagKey)` se evalúa en tiempo constante $O(1)$.
* **Consulta de Reglas**: `CombatRules.getInt` lee la memoria caché sin crear objetos.
* **Sin Carga de GC**: `CCAHooks.getCooldownTicks` no genera objetos en el montículo (heap).

---

## 4. Diagramas ASCII y Estados

```
+-------------------------------------------------------------------+
|               net.minecraft.world.entity.player.Player            |
+-------------------------------------------------------------------+
                                  ^
                                  | (Bytecode Injection)
+---------------------------------+---------------------------------+
|                           PlayerMixin                             |
|  - cca$overrideAttackDelay(CallbackInfoReturnable<Float>)         |
|  - cca$preventSwapReset(CallbackInfo)                             |
|  - cca$applyJuice(Entity, CallbackInfo)                           |
+---------------------------------+---------------------------------+
                                  |
                                  v (Delegates Logic)
+---------------------------------+---------------------------------+
|                             CCAHooks                              |
|  - getCooldownTicks(Player, ItemStack): int                       |
|  - applyCombatJuice(Player, Entity): void                         |
|  - SPEARS: TagKey<Item> (#c:spears)                               |
+---------------------------------+---------------------------------+
                                  |
                                  v (Queries Values)
+---------------------------------+---------------------------------+
|                           CombatRules                             |
|  - COMBAT_COOLDOWN: GameRuleCategory                              |
|  - SWORD_TICKS, AXE_TICKS, PICKAXE_TICKS, ... (9 GameRules)       |
|  - getInt(Level, GameRule<Integer>): int                          |
|  - getBoolean(Level, GameRule<Boolean>): boolean                  |
+-------------------------------------------------------------------+
```

---

## 5. Matriz de Referencia Completa

| Injected Method in `Player.java` | Mixin Method Signature | Injection Point | Cancellable | Functional Purpose |
| :--- | :--- | :---: | :---: | :--- |
| `getCurrentItemAttackStrengthDelay()` | `cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir)` | `@At("HEAD")` | `true` | Intercepts attack delay and sets customized ticks. |
| `resetAttackStrengthTicker()` | `cca$preventSwapReset(CallbackInfo ci)` | `@At("HEAD")` | `true` | Conditionally cancels ticker reset on item swap. |
| `attack(Entity target)` | `cca$applyJuice(Entity target, CallbackInfo ci)` | `@At("HEAD")` | `false` | Spawns critical particles and pitch-shifted audio on high charge. |

---

## 6. Análisis de Inyecciones de Mixin

```java
@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow public abstract ItemStack getMainHandItem();

    @Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("HEAD"), cancellable = true)
    private void cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        ItemStack stack = this.getMainHandItem();
        int ticks = CCAHooks.getCooldownTicks(player, stack);
        if (ticks >= 0) {
            cir.setReturnValue((float) ticks);
        }
    }

    @Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"), cancellable = true)
    private void cca$preventSwapReset(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (CombatRules.getBoolean(player.level(), CombatRules.PREVENT_SWAP_RESET)) {
            ci.cancel();
        }
    }

    @Inject(method = "attack", at = @At("HEAD"))
    private void cca$applyJuice(Entity target, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        CCAHooks.applyCombatJuice(player, target);
    }
}
```

---

## 🔗 Enlaces de Documentación Relacionados
* [[Volver al Portal de Minecraft 26.3|es_es-26.3-Home]]
* [[26.3 Mecánicas de Enfriamiento de Armas|es_es-26.3-Weapon-Cooldown-Mechanics]]
* [[26.3 Retroalimentación de Combate y Efectos de Audio|es_es-26.3-Combat-Juice-and-Audio-Feedback]]
* [[26.3 Configuración y Matriz de GameRules|es_es-26.3-Configuration-and-GameRules]]
* [[26.3 Entorno de Desarrollo y Herramientas|es_es-26.3-Developer-Setup-and-Building]]
