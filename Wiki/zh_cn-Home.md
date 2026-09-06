# ⚔️ 即时满足：战斗冷却调整器 (Combat Cooldown Adjuster) 中文维基

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

欢迎查阅 **Instant Gratification: Combat Cooldown Adjuster** 官方技术维基！本模组专为 Minecraft Fabric 平台打造，旨在彻底消除 Minecraft 1.9+ 战斗更新带来的“攻击等待”迟滞感，重塑畅快淋漓的高速战斗体验，同时保持严密的伤害判定与感官反馈。

---

## 🧭 多版本文档入口

根据模组的 **1 Jar 1 Version（单版本单 Jar）** 规范，各主流 Minecraft 版本均拥有独立的文档树：

| 游戏版本 | 模组语义化版本 | Fabric Loader | 文档传送门 |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 进入 Minecraft 26.2 文档门户|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 进入 Minecraft 26.3 文档门户|26.3-Home]] |

---

## ⚡ 核心机制概览

1. **武器分类 Tick 覆盖 (Categorical Tick Overrides)**：
   摆脱原版攻击速度计算，按物品标签分类自定义冷却 Tick（20 Tick = 1 秒）。剑默认 4 Tick（每秒 5 次攻击），斧默认 8 Tick（每秒 2.5 次攻击），锄头默认 1 Tick（每秒 20 次超高速连击）。设置冷却为 `0` 即可完全恢复 1.8 时代的连点光速攻击！
2. **快捷栏切换敏捷 (Swap Agility)**：
   启用 `ig:prevent_item_swap_cooldown`（默认开启）后，在快捷栏切换不同武器时**不会重置**攻击充能槽，支持行云流水的武器连招切换。
3. **战斗打击打击感 (Combat Juice)**：
   当攻击充能蓄力达到 80% 以上（$S > 0.8$）时，命中目标将触发双重粒子爆炸（10 枚暴击粒子 + 5 枚附魔暴击粒子），并伴随动态升调的强力攻击音效（音调从 1.0 倍动态上升至 1.4 倍）。
4. **动态游戏规则集成 (Dynamic GameRules)**：
   借助 **DasikLibrary**，全部 9 项参数均注册在 `combat-cooldown-adjuster:combat_cooldown` 类别中，支持游戏内 `/gamerule` 实时调整与 Tab 补全，无需重启服务器。

---

## 📊 游戏规则快速参考

| 规则标识符 | 类型 | 默认值 | 描述 |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | 整数 | `4` | 剑类武器攻击冷却 Tick（默认 4 Tick = 0.2 秒） |
| `ig:axe_cooldown_ticks` | 整数 | `8` | 斧类武器攻击冷却 Tick（默认 8 Tick = 0.4 秒） |
| `ig:pickaxe_cooldown_ticks` | 整数 | `4` | 镐类工具攻击冷却 Tick |
| `ig:shovel_cooldown_ticks` | 整数 | `2` | 锹类工具攻击冷却 Tick |
| `ig:hoe_cooldown_ticks` | 整数 | `1` | 锄头攻击冷却 Tick（连击利器） |
| `ig:spear_cooldown_ticks` | 整数 | `6` | 矛类武器攻击冷却 Tick (`#c:spears`) |
| `ig:generic_cooldown_ticks` | 整数 | `4` | 通用未分类物品及空手攻击冷却 Tick |
| `ig:prevent_item_swap_cooldown` | 布尔 | `true` | 切换快捷栏物品时不重置攻击蓄力槽 |
| `ig:enable_combat_juice` | 布尔 | `true` | 启用高蓄力攻击时的打击粒子与动态音效 |

---

## ⚖️ 开源协议与版权归属
* **作者**: Dasik (Rifaditya)
* **开源协议**: GNU General Public License v3.0 (GPLv3)
* **主站主页**: [[返回英文总主页|Home]]
