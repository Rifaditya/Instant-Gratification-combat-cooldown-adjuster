# 🏗️ Architecture et Analyse des Mixins (Minecraft 26.3)

> 📌 **Clause de Non-Responsabilité relative au Dépôt Source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

## 1. Spécifications Techniques Officielles
| Paramètre | Spécification Technique |
| :--- | :--- |
| **Namespace Package** | `net.instantgratification.combatcooldownadjuster` |
| **Bytecode Transformer** | SpongePowered Mixin 0.8+ |
| **Configuration File** | `combat-cooldown-adjuster.mixins.json` |
| **Injected Entity** | `net.minecraft.world.entity.player.Player` |
| **Logic Provider** | `net.instantgratification.combatcooldownadjuster.util.CCAHooks` |
| **GameRule Provider** | `net.instantgratification.combatcooldownadjuster.registry.CombatRules` |
| **Execution Complexity** | $O(1)$ constant time lookup per attack tick |

---

## 2. Déroulement en Survie

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

## 3. Chemins Rapides Sans Allocation Mémoire (Zero-Allocation)
* **Vérification de Tags** : `ItemStack#is(TagKey)` s'exécute en temps constant $O(1)$.
* **Lecture des Règles** : `CombatRules.getInt` lit le cache mémoire sans instancier d'objets.
* **Zéro Pression sur le GC** : `CCAHooks.getCooldownTicks` n'alloue aucun objet sur le tas.

---

## 4. Diagrammes ASCII et Machine d'États

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

## 5. Matrice de Référence Complète

| Injected Method in `Player.java` | Mixin Method Signature | Injection Point | Cancellable | Functional Purpose |
| :--- | :--- | :---: | :---: | :--- |
| `getCurrentItemAttackStrengthDelay()` | `cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir)` | `@At("HEAD")` | `true` | Intercepts attack delay and sets customized ticks. |
| `resetAttackStrengthTicker()` | `cca$preventSwapReset(CallbackInfo ci)` | `@At("HEAD")` | `true` | Conditionally cancels ticker reset on item swap. |
| `attack(Entity target)` | `cca$applyJuice(Entity target, CallbackInfo ci)` | `@At("HEAD")` | `false` | Spawns critical particles and pitch-shifted audio on high charge. |

---

## 6. Analyse des Points d'Injection Mixin

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

## 🔗 Liens de Documentation Associés
* [[Retour au Portail Minecraft 26.3|fr_fr-26.3-Home]]
* [[26.3 Mécaniques de Temps de Recharge des Armes|fr_fr-26.3-Weapon-Cooldown-Mechanics]]
* [[26.3 Impact Visuel et Retour Audio en Combat|fr_fr-26.3-Combat-Juice-and-Audio-Feedback]]
* [[26.3 Configuration et Matrice des GameRules|fr_fr-26.3-Configuration-and-GameRules]]
* [[26.3 Environnement Développeur & Outils|fr_fr-26.3-Developer-Setup-and-Building]]
