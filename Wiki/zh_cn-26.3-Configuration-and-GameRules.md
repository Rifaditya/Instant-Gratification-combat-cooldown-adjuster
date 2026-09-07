# ⚙️ 配置与游戏规则矩阵 (Minecraft 26.3)

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

## 1. 官方技术信息
| 参数 | 技术规范 |
| :--- | :--- |
| **Category Identifier** | `combat-cooldown-adjuster:combat_cooldown` |
| **Total Registered Rules** | 9 (7 Integers, 2 Booleans) |
| **Registry Manager** | `DynamicGameRuleManager` (DasikLibrary) |
| **Runtime Mutability** | In-game `/gamerule <name> <value>` (Zero restart latency) |
| **Storage Persistence** | World save `level.dat` |
| **Player Agency Standard** | Anti-Nanny Invariant (Permits values from 0 up to `Integer.MAX_VALUE`) |

---

## 2. 生存战斗操作流程

1. **Viewing Current Rules**: Type `/gamerule ig:` to view tab-completion of all 9 registered rules.
2. **Modifying Attack Delays**: Set sword cooldown to 2 ticks: `/gamerule ig:sword_cooldown_ticks 2`
3. **Pure 1.8 Click-Spam**: Set `/gamerule ig:sword_cooldown_ticks 0`
4. **Toggling Swap Agility**: Set `/gamerule ig:prevent_item_swap_cooldown true`
5. **Managing Combat Juice**: Set `/gamerule ig:enable_combat_juice true`

---

## 3. 玩家主权与反说教原则 (Anti-Nanny Invariant)
本模组严格恪守 **Player Agency & Anti-Nanny Invariant**：
* **不设人为上限**：Tick 延迟允许设置至 `Integer.MAX_VALUE` ($2,147,483,647$)，玩家享有完全自由。
* **非负下界守卫**：强制下界 $T \ge 0$，杜绝除零或负数引发的游戏引擎崩溃。

---

## 4. 游戏规则生命周期图

```
    [ World Load / Server Startup ]
                  |
                  v
    CombatCooldownAdjuster#onInitialize()
                  |
                  v
         CombatRules#register()
                  |
                  v
    DynamicGameRuleManager.registerInteger / registerBoolean
                  |
                  v
     Registered under Category: "combat-cooldown-adjuster:combat_cooldown"
                  |
                  +---> In-Game Chat / Tab Completion Available
                  +---> Client-Server Sync Handled via Vanilla Network
                  +---> Serialized to level.dat
```

---

## 5. 全面参考矩阵

| # | GameRule Key | Data Type | Default | Permitted Bounds | Description & Tactical Usage |
| :-: | :--- | :---: | :---: | :---: | :--- |
| **1** | `ig:sword_cooldown_ticks` | Integer | `4` | $0$ to $2^{31}-1$ | Attack cooldown for Swords (`#minecraft:swords`). Yields $5.0\text{ attacks/sec}$. |
| **2** | `ig:axe_cooldown_ticks` | Integer | `8` | $0$ to $2^{31}-1$ | Attack cooldown for Axes (`#minecraft:axes`). Yields $2.5\text{ attacks/sec}$. |
| **3** | `ig:pickaxe_cooldown_ticks` | Integer | `4` | $0$ to $2^{31}-1$ | Attack cooldown for Pickaxes (`#minecraft:pickaxes`). Yields $5.0\text{ attacks/sec}$. |
| **4** | `ig:shovel_cooldown_ticks` | Integer | `2` | $0$ to $2^{31}-1$ | Attack cooldown for Shovels (`#minecraft:shovels`). Yields $10.0\text{ attacks/sec}$. |
| **5** | `ig:hoe_cooldown_ticks` | Integer | `1` | $0$ to $2^{31}-1$ | Attack cooldown for Hoes (`#minecraft:hoes`). Yields $20.0\text{ attacks/sec}$. |
| **6** | `ig:spear_cooldown_ticks` | Integer | `6` | $0$ to $2^{31}-1$ | Attack cooldown for Spears (`#c:spears`). Yields $3.33\text{ attacks/sec}$. |
| **7** | `ig:generic_cooldown_ticks` | Integer | `4` | $0$ to $2^{31}-1$ | Fallback cooldown for untagged items, unarmed fists, and torches. |
| **8** | `ig:prevent_item_swap_cooldown` | Boolean | `true` | `true` / `false` | When `true`, switching hotbar items will NOT reset the attack strength ticker. |
| **9** | `ig:enable_combat_juice` | Boolean | `true` | `true` / `false` | When `true`, enables dual-layer hit sparks and dynamic pitch shifting on $>80\%$ hits. |

---

## 6. 开发者与 Mixin 注入分析

```java
int swordTicks = CombatRules.getInt(level, CombatRules.SWORD_TICKS);
boolean swapAgility = CombatRules.getBoolean(level, CombatRules.PREVENT_SWAP_RESET);
```

---

## 🔗 相关文档链接
* [[返回 Minecraft 26.3 门户|zh_cn-26.3-Home]]
* [[26.3 武器冷却机制|zh_cn-26.3-Weapon-Cooldown-Mechanics]]
* [[26.3 战斗打击感与音效反馈|zh_cn-26.3-Combat-Juice-and-Audio-Feedback]]
* [[26.3 架构设计与 Mixin 解析|zh_cn-26.3-Architecture-and-Mixins]]
