# ⚔️ Minecraft 26.3 文档门户

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

欢迎访问 **Instant Gratification: Combat Cooldown Adjuster** 在 **Minecraft 26.3**（目标 `26.3-snapshot-6`）的技术文档门户！本版本树内所有技术文档严格匹配当前版本的字节码映射、Loom 依赖项与功能规范。

---

## 🧭 Minecraft 26.3 子系统导航矩阵

| 功能 / 子系统 | 详细描述 | 专属维基页面 |
| :--- | :--- | :--- |
| **武器冷却机制** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.3 武器冷却机制|zh_cn-26.3-Weapon-Cooldown-Mechanics]] |
| **战斗打击感与音效反馈** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.3 战斗打击感与音效反馈|zh_cn-26.3-Combat-Juice-and-Audio-Feedback]] |
| **配置与游戏规则矩阵** | Complete reference matrix of all 9 namespaced GameRules | [[26.3 配置与游戏规则矩阵|zh_cn-26.3-Configuration-and-GameRules]] |
| **架构设计与 Mixin 解析** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.3 架构设计与 Mixin 解析|zh_cn-26.3-Architecture-and-Mixins]] |
| **开发者配置与构建** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.3 开发者配置与构建|zh_cn-26.3-Developer-Setup-and-Building]] |

---

## 📊 官方技术信息

| 参数 | 技术规范 |
| :--- | :--- |
| **Minecraft Release Target** | `26.3-snapshot-6` |
| **Mod SemVer Release** | `1.0.1+26.3` |
| **Fabric Loader Requirement** | `0.19.3` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.8.36` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ 核心子系统亮点

1. **亚 Tick 武器攻速机制 (Sub-Tick Weapon Timing)**：
   以直接整数 Tick 覆盖原版攻击冷却（$T_{\text{{delay}}}$）。剑默认 4 Tick（5.0 次/秒），斧默认 8 Tick（2.5 次/秒），锄头默认 1 Tick（20.0 次/秒）。设置 $T = 0$ 开启纯粹 1.8 极速狂点连击。

2. **快捷栏无缝切换 (Hotbar Swap Agility)**：
   开启 `ig:prevent_item_swap_cooldown` 可彻底移除切换快捷栏时的强制蓄力惩罚，支持激爽的武器连招连打。

3. **多感官战斗打击感 (Multi-Sensory Combat Juice)**：
   蓄力超过 80% 的强力攻击会触发双层粒子特效（`CRIT` 暴击与 `ENCHANTED_HIT` 附魔暴击）及动态升调音效（$1.0\times$ 到 $1.4\times$）。

---

## 🔗 全局维基传送门
* [[返回主维基门户|zh_cn-Home]]
* [[Minecraft 26.2 文档门户|zh_cn-26.2-Home]]
* [[版本兼容性矩阵|zh_cn-Version-Compatibility]]
* [[故障排除与常见问题|zh_cn-Troubleshooting-and-FAQ]]
