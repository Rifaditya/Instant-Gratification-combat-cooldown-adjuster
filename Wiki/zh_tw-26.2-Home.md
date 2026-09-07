# ⚔️ Minecraft 26.2 文件門戶

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **程式碼倉庫來源免責聲明**：本維基文件反映了**程式碼倉庫中的當前源碼狀態**，可能包含領先於 CurseForge 與 Modrinth 平臺公開發布版本的最新未發布提交或開發中功能。

歡迎造訪 **Instant Gratification: Combat Cooldown Adjuster** 在 **Minecraft 26.2**（目標 `26.1.2`）的技術文件門戶！本版本樹內所有技術文件嚴格匹配當前版本的位元組碼映射、Loom 相依項與功能規範。

---

## 🧭 Minecraft 26.2 子系統導覽矩陣

| 功能 / 子系統 | 詳細描述 | 專屬維基頁面 |
| :--- | :--- | :--- |
| **武器冷卻機制** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.2 武器冷卻機制|zh_tw-26.2-Weapon-Cooldown-Mechanics]] |
| **戰鬥打擊感與音效回饋** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.2 戰鬥打擊感與音效回饋|zh_tw-26.2-Combat-Juice-and-Audio-Feedback]] |
| **配置與遊戲規則矩陣** | Complete reference matrix of all 9 namespaced GameRules | [[26.2 配置與遊戲規則矩陣|zh_tw-26.2-Configuration-and-GameRules]] |
| **架構設計與 Mixin 解析** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.2 架構設計與 Mixin 解析|zh_tw-26.2-Architecture-and-Mixins]] |
| **開發者配置與構建** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.2 開發者配置與構建|zh_tw-26.2-Developer-Setup-and-Building]] |

---

## 📊 官方技術資訊

| 參數 | 技術規範 |
| :--- | :--- |
| **Minecraft Release Target** | `26.1.2` |
| **Mod SemVer Release** | `1.0.1+26.2` |
| **Fabric Loader Requirement** | `0.19.1` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.6.9+build.24` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ 核心子系統亮點

1. **亞 Tick 武器攻速機制 (Sub-Tick Weapon Timing)**：
   以直接整數 Tick 覆蓋原版攻擊冷卻（$T_{\text{{delay}}}$）。劍預設 4 Tick（5.0 次/秒），斧預設 8 Tick（2.5 次/秒），鋤頭預設 1 Tick（20.0 次/秒）。設定 $T = 0$ 開啟純粹 1.8 極速狂點連擊。

2. **快捷列無縫切換 (Hotbar Swap Agility)**：
   開啟 `ig:prevent_item_swap_cooldown` 可徹底移除切換快捷列時的強制蓄力懲罰，支援行雲流水的武器連招連續打擊。

3. **多感官戰鬥打擊感 (Multi-Sensory Combat Juice)**：
   蓄力超過 80% 的強力攻擊會觸發雙層粒子特效（`CRIT` 暴擊與 `ENCHANTED_HIT` 附魔暴擊）及動態升調音效（$1.0\times$ 到 $1.4\times$）。

---

## 🔗 全域維基傳送門
* [[返回主維基門戶|zh_tw-Home]]
* [[Minecraft 26.3 文件門戶|zh_tw-26.3-Home]]
* [[版本相容性矩陣|zh_tw-Version-Compatibility]]
* [[疑難排解與常見問題|zh_tw-Troubleshooting-and-FAQ]]
