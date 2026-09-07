# 🔧 故障排除与常见问题

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

## 1. 官方技术信息
| 参数 | 诊断参数 |
| :--- | :--- |
| **目标子系统** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **控制游戏规则** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **关键注入点** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **核心依赖** | `dasik-library` (Dynamic GameRule Manager) |
| **日志命名空间** | `combat-cooldown-adjuster` |

---

## 2. 诊断决策流程图

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

## 3. 分阶段排查指南

### 阶段 1：校准武器 Tick 延迟
1. 打开游戏内聊天框或服务器控制台。
2. 查询当前武器类型的活跃 Tick 数：
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. 若返回默认值 `4`，则攻击频率为：
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ 次攻击/秒}}
4. 若需体验 1.8 时代的连击（Click-Spam），执行：
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. 攻击生物或测试假人，攻击将瞬间判定且无任何冷却停顿。

### 阶段 2：诊断快捷栏切换敏捷度
1. 在快捷栏 1 号槽放置剑，2 号槽放置斧。
2. 挥剑攻击触发冷却。
3. 立即按按键 `2` 切换至斧头。
4. 若 `ig:prevent_item_swap_cooldown` 为 `true`，攻击蓄力槽**不会**归零，将无缝继续充能。
5. 若蓄力槽发生重置，执行：
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### 阶段 3：粒子与音效性能优化
1. 战斗打击感（Combat Juice）在攻击蓄力超过 $0.8$（80% 充能）时触发。
2. 若低配客户端在多粒子碰撞时出现微卡顿，可在服务端关闭打击感：
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. 该设置彻底跳过 `CCAHooks.applyCombatJuice` 中的 `sendParticles` 与 `playSound` 调用，节约渲染线程与网络带宽。

---

## 4. 数学校准参考矩阵

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

## 5. 常见问题解答 (FAQ)

### Q1：将冷却 Tick 设置为 0 会破坏原版伤害缩放计算吗？
**不会。** 原版按 $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$ 计算蓄力比例。当 $T = 0$ 时，我们的 Mixin 返回延迟 $0.0$，促使原版引擎立即判定 $S(t) = 1.0$。每次命中自动打出 100% 满额武器基础伤害。

### Q2：为什么未标记标签的模组武器默认以 4 Tick 速度攻击？
若第三方物品未实现任何原版工具标签或 `#c:spears`，`CCAHooks.getCooldownTicks` 将自动回退至 `CombatRules.GENERIC_TICKS`（默认 4 Tick）。可通过以下命令调整：
```mcfunction
/gamerule ig:generic_cooldown_ticks <数值>
```

### Q3：本模组会导致多人联机延迟或客户端不同步吗？
Combat Cooldown Adjuster 完全在服务端权威运行。玩家连接专用服务器时，`PlayerMixin` 注入到服务端玩家实体上，伤害计算具备绝对权威性。单人游戏与局域网环境下双方本地执行，零网络延迟。

### Q4：游戏规则会持久保存在世界存档中吗？
**会。** 全部 9 项游戏规则均通过 DasikLibrary 由原版规则系统直接序列化到世界存档的 `level.dat` 文件中。重启服务器或重载世界后配置完好保留。

---

## 6. 相关文档链接
* [[返回主维基门户|zh_cn-Home]]
* [[26.2 配置与游戏规则矩阵|zh_cn-26.2-Configuration-and-GameRules]]
* [[26.3 配置与游戏规则矩阵|zh_cn-26.3-Configuration-and-GameRules]]
* [[开发者配置与统一构建指南|zh_cn-Developer-Setup-and-Building]]
