# 🏗️ 架构设计与 Mixin 解析 (Minecraft 26.2)

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

## 1. 官方技术信息
| 参数 | 技术规范 |
| :--- | :--- |
| **Namespace Package** | `net.instantgratification.combatcooldownadjuster` |
| **Bytecode Transformer** | SpongePowered Mixin 0.8+ |
| **Configuration File** | `combat-cooldown-adjuster.mixins.json` |
| **Injected Entity** | `net.minecraft.world.entity.player.Player` |
| **Logic Provider** | `net.instantgratification.combatcooldownadjuster.util.CCAHooks` |
| **GameRule Provider** | `net.instantgratification.combatcooldownadjuster.registry.CombatRules` |
| **Execution Complexity** | $O(1)$ constant time lookup per attack tick |

---

## 2. 生存战斗操作流程

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

## 3. 零垃圾回收 (Zero-Allocation) 极速路径
* **标签匹配**：`ItemStack#is(TagKey)` 执行 $O(1)$ 常数时间哈希检查。
* **规则查询**：`CombatRules.getInt` 访问内存缓存标量，无任何对象分配。
* **零 GC 堆开销**：`CCAHooks.getCooldownTicks` 在高频攻击时不分配任何堆内存对象，百人团战依然稳如磐石。

---

## 4. 可视化 ASCII 状态转移图

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

## 5. 全面参考矩阵

| Injected Method in `Player.java` | Mixin Method Signature | Injection Point | Cancellable | Functional Purpose |
| :--- | :--- | :---: | :---: | :--- |
| `getCurrentItemAttackStrengthDelay()` | `cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir)` | `@At("HEAD")` | `true` | Intercepts attack delay and sets customized ticks. |
| `resetAttackStrengthTicker()` | `cca$preventSwapReset(CallbackInfo ci)` | `@At("HEAD")` | `true` | Conditionally cancels ticker reset on item swap. |
| `attack(Entity target)` | `cca$applyJuice(Entity target, CallbackInfo ci)` | `@At("HEAD")` | `false` | Spawns critical particles and pitch-shifted audio on high charge. |

---

## 6. 开发者与 Mixin 注入分析

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

## 🔗 相关文档链接
* [[返回 Minecraft 26.2 门户|zh_cn-26.2-Home]]
* [[26.2 武器冷却机制|zh_cn-26.2-Weapon-Cooldown-Mechanics]]
* [[26.2 战斗打击感与音效反馈|zh_cn-26.2-Combat-Juice-and-Audio-Feedback]]
* [[26.2 配置与游戏规则矩阵|zh_cn-26.2-Configuration-and-GameRules]]
* [[26.2 开发者配置与构建|zh_cn-26.2-Developer-Setup-and-Building]]
