# ⚔️ 即時滿足：戰鬥冷卻調整器 (Combat Cooldown Adjuster) 繁體中文維基

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **程式碼儲存庫來源免責聲明**：本維基文件反映了**程式碼儲存庫中的當前源碼狀態**，可能包含領先於 CurseForge 與 Modrinth 平台公開發布版本的最新未發布提交或開發中功能。

歡迎查閱 **Instant Gratification: Combat Cooldown Adjuster** 官方技術維基！本模組專為 Minecraft Fabric 平台打造，旨在徹底消除 Minecraft 1.9+ 戰鬥更新帶來的「攻擊等待」遲滯感，重塑暢快淋漓的高速戰鬥體驗，同時保持嚴密的傷害判定與感官反饋。

---

## 🧭 多版本文件入口

根據模組的 **1 Jar 1 Version（單版本單 Jar）** 規範，各主流 Minecraft 版本均擁有獨立的文件樹：

| 遊戲版本 | 模組語意化版本 | Fabric Loader | 文件傳送門 |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 進入 Minecraft 26.2 文件門戶|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 進入 Minecraft 26.3 文件門戶|26.3-Home]] |

---

## ⚡ 核心機制概覽

1. **武器分類 Tick 覆蓋 (Categorical Tick Overrides)**：
   擺脫原版攻擊速度計算，按物品標籤分類自定義冷卻 Tick（20 Tick = 1 秒）。劍預設 4 Tick（每秒 5 次攻擊），斧預設 8 Tick（每秒 2.5 次攻擊），鋤頭預設 1 Tick（每秒 20 次超高速連擊）。設置冷卻為 `0` 即可完全恢復 1.8 時代的連點光速攻擊！
2. **快捷列切換敏捷 (Swap Agility)**：
   啟用 `ig:prevent_item_swap_cooldown`（預設開啟）後，在快捷列切換不同武器時**不會重設**攻擊充能槽，支援行雲流水的武器連招切換。
3. **戰鬥打擊反饋感 (Combat Juice)**：
   當攻擊充能蓄力達到 80% 以上（$S > 0.8$）時，命中目標將觸發雙重粒子爆炸（10 枚爆擊粒子 + 5 枚附魔爆擊粒子），並伴隨動態升調的強力攻擊音效（音調從 1.0 倍動態上升至 1.4 倍）。
4. **動態遊戲規則集成 (Dynamic GameRules)**：
   借助 **DasikLibrary**，全部 9 項參數均註冊在 `combat-cooldown-adjuster:combat_cooldown` 類別中，支援遊戲內 `/gamerule` 即時調整與 Tab 補全，無需重啟伺服器。

---

## 📊 遊戲規則快速參考

| 規則識別碼 | 類型 | 預設值 | 描述 |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | 整數 | `4` | 劍類武器攻擊冷卻 Tick（預設 4 Tick = 0.2 秒） |
| `ig:axe_cooldown_ticks` | 整數 | `8` | 斧類武器攻擊冷卻 Tick（預設 8 Tick = 0.4 秒） |
| `ig:pickaxe_cooldown_ticks` | 整數 | `4` | 鎬類工具攻擊冷卻 Tick |
| `ig:shovel_cooldown_ticks` | 整數 | `2` | 鍬類工具攻擊冷卻 Tick |
| `ig:hoe_cooldown_ticks` | 整數 | `1` | 鋤頭攻擊冷卻 Tick（連擊利器） |
| `ig:spear_cooldown_ticks` | 整數 | `6` | 矛類武器攻擊冷卻 Tick (`#c:spears`) |
| `ig:generic_cooldown_ticks` | 整數 | `4` | 通用未分類物品及空手攻擊冷卻 Tick |
| `ig:prevent_item_swap_cooldown` | 布林 | `true` | 切換快捷列物品時不重設攻擊蓄力槽 |
| `ig:enable_combat_juice` | 布林 | `true` | 啟用高蓄力攻擊時的打擊粒子與動態音效 |

---

## ⚖️ 開源協議與版權歸屬
* **作者**: Dasik (Rifaditya)
* **開源協議**: GNU General Public License v3.0 (GPLv3)
* **主站首頁**: [[返回英文總首頁|Home]]
