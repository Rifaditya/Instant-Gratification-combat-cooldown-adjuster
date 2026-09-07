# 🏗️ Architektur & Mixin-Analyse (Minecraft 26.2)

> 📌 **Quellcode-Repository-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Release-Builds auf CurseForge und Modrinth enthalten kann.

## 1. Offizielle technische Spezifikation
| Parameter | Technische Spezifikation |
| :--- | :--- |
| **Namespace Package** | `net.instantgratification.combatcooldownadjuster` |
| **Bytecode Transformer** | SpongePowered Mixin 0.8+ |
| **Configuration File** | `combat-cooldown-adjuster.mixins.json` |
| **Injected Entity** | `net.minecraft.world.entity.player.Player` |
| **Logic Provider** | `net.instantgratification.combatcooldownadjuster.util.CCAHooks` |
| **GameRule Provider** | `net.instantgratification.combatcooldownadjuster.registry.CombatRules` |
| **Execution Complexity** | $O(1)$ constant time lookup per attack tick |

---

## 2. Überlebens-Gameplay & Bedienung

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

## 3. Schnelle Pfade ohne Heap-Belastung (Zero-Allocation)
* **Tag-Prüfung**: `ItemStack#is(TagKey)` läuft in konstanter Zeit $O(1)$.
* **Regelabfrage**: `CombatRules.getInt` liest gecachte Speicherwerte ohne Objekterstellung.
* **Keine GC-Belastung**: `CCAHooks.getCooldownTicks` erzeugt null Heap-Allokationen.

---

## 4. ASCII-Ablaufdiagramme & Zustände

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

## 5. Vollständige Referenztabelle

| Injected Method in `Player.java` | Mixin Method Signature | Injection Point | Cancellable | Functional Purpose |
| :--- | :--- | :---: | :---: | :--- |
| `getCurrentItemAttackStrengthDelay()` | `cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir)` | `@At("HEAD")` | `true` | Intercepts attack delay and sets customized ticks. |
| `resetAttackStrengthTicker()` | `cca$preventSwapReset(CallbackInfo ci)` | `@At("HEAD")` | `true` | Conditionally cancels ticker reset on item swap. |
| `attack(Entity target)` | `cca$applyJuice(Entity target, CallbackInfo ci)` | `@At("HEAD")` | `false` | Spawns critical particles and pitch-shifted audio on high charge. |

---

## 6. Entwickler- & Mixin-Analyse

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

## 🔗 Weiterführende Links
* [[Zurück zum Minecraft 26.2 Portal|de_de-26.2-Home]]
* [[26.2 Waffen-Abklingzeit-Mechanik|de_de-26.2-Weapon-Cooldown-Mechanics]]
* [[26.2 Kampffeedback & Audioeffekte|de_de-26.2-Combat-Juice-and-Audio-Feedback]]
* [[26.2 Konfiguration & GameRules-Matrix|de_de-26.2-Configuration-and-GameRules]]
* [[26.2 Entwickler-Setup & Toolchain|de_de-26.2-Developer-Setup-and-Building]]
