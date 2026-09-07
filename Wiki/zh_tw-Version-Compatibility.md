# 📊 版本相容性矩陣

> 📌 **程式碼倉庫來源免責聲明**：本維基文件反映了**程式碼倉庫中的當前源碼狀態**，可能包含領先於 CurseForge 與 Modrinth 平臺公開發布版本的最新未發布提交或開發中功能。

## 1. 官方技術資訊
| 參數 | 技術規範 |
| :--- | :--- |
| **模組識別碼** | `combat-cooldown-adjuster` |
| **模組系列** | Instant Gratification (IG) |
| **支援的 Fabric 錨點版本** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Java 執行平臺** | OpenJDK 25 (Hotspot 64-bit) |
| **構建工具鏈** | Gradle 9.3+ with Fabric Loom |
| **架構規範** | 1 Jar 1 Version（單版本單 Jar 規範） |
| **公共 API 狀態** | 獨立模組（呼叫 DasikLibrary API） |

---

## 2. 版本相容性矩陣
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 標準錨點 |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 現代前沿分支 |

---

## 3. 1 Jar 1 Version 規範與通用庫相依邊界

### 專屬模組二進位構建
與透過寬泛版本範圍匯出純計算 API 的共用庫不同，直接向 `net.minecraft.world.entity.player.Player` 注入位元組碼的模組必須在編譯期嚴格對接目標版本的 Mojang 混淆映射與中間層方法描述符。
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### 通用 DasikLibrary 整合
Combat Cooldown Adjuster 依賴 **DasikLibrary** 實現執行期動態遊戲規則註冊 (`DynamicGameRuleManager`)。DasikLibrary 遵循開放版本邊界架構 (`>=26.1.2-`)，確保：
1. 與伺服器端遊戲規則序列化完全向前與向後相容。
2. 遊戲內 `/gamerule` 動態 Tab 補全覆蓋所有遊戲環境。
3. 客戶端類別載入安全防護（純伺服器端執行徹底杜絕客戶端當機）。

---

## 4. 安裝與前置準備工作流程

```
[ Download Compatible Mod Jar ]
               |
               +---> Check Minecraft Target (26.2 vs 26.3)
               |
[ Verify Dependencies ]
       |
       +---> Fabric Loader (>=0.19.1 for 26.2 | >=0.19.3 for 26.3)
       +---> Fabric API (matching MC release)
       +---> DasikLibrary (>=1.6.9+ for 26.2 | >=1.8.36 for 26.3)
               |
[ Deploy to .minecraft/mods/ ]
               |
[ Launch Game with JDK 25 ]
```

### 安裝驗證核對清單：
1. 確保 Java 執行環境為 OpenJDK 25 Hotspot（例如 Eclipse Adoptium `jdk-25.0.3+`）。
2. 驗證 `mods` 資料夾中已存在 `fabric-api`。
3. 驗證 `mods` 資料夾中已存在 `dasik-library`。
4. 啟動遊戲並檢查日誌輸出：
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. 相關文件連結
* [[返回主維基門戶|zh_tw-Home]]
* [[26.2 武器冷卻機制|zh_tw-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 武器冷卻機制|zh_tw-26.3-Weapon-Cooldown-Mechanics]]
* [[疑難排解與常見問題|zh_tw-Troubleshooting-and-FAQ]]
