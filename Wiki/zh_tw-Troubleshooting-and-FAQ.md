# 🔧 疑難排解與常見問題

> 📌 **程式碼倉庫來源免責聲明**：本維基文件反映了**程式碼倉庫中的當前源碼狀態**，可能包含領先於 CurseForge 與 Modrinth 平臺公開發布版本的最新未發布提交或開發中功能。

## 1. 官方技術資訊
| 參數 | 診斷參數 |
| :--- | :--- |
| **目標子系統** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **控制遊戲規則** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **關鍵注入點** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **核心相依** | `dasik-library` (Dynamic GameRule Manager) |
| **日誌命名空間** | `combat-cooldown-adjuster` |

---

## 2. 診斷決策流程圖

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

## 3. 分階段排查指南

### 階段 1：校準武器 Tick 延遲
1. 開啟遊戲內聊天框或伺服器控制台。
2. 查詢當前武器型別的活躍 Tick 數：
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. 若返回預設值 `4`，則攻擊頻率為：
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ 次攻擊/秒}}
4. 若需體驗 1.8 時代的連擊（Click-Spam），執行：
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. 攻擊生物或測試假人，攻擊將瞬間判定且無任何冷卻停頓。

### 階段 2：診斷快捷列切換敏捷度
1. 在快捷列 1 號槽放置劍，2 號槽放置斧。
2. 揮劍攻擊觸發冷卻。
3. 立即按按鍵 `2` 切換至斧頭。
4. 若 `ig:prevent_item_swap_cooldown` 為 `true`，攻擊蓄力槽**不會**歸零，將無縫繼續充能。
5. 若蓄力槽發生重設，執行：
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### 阶段 3：粒子與音效效能最佳化
1. 戰鬥打擊感（Combat Juice）在攻擊蓄力超過 $0.8$（80% 充能）時觸發。
2. 若低配客戶端在多粒子碰撞時出現微卡頓，可在伺服器端關閉打擊感：
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. 該設定徹底跳過 `CCAHooks.applyCombatJuice` 中的 `sendParticles` 與 `playSound` 呼叫，節約渲染執行緒與網路頻寬。

---

## 4. 數學校準參考矩陣

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

## 5. 常見問題解答 (FAQ)

### Q1：將冷卻 Tick 設定為 0 會破壞原版傷害縮放計算嗎？
**不會。** 原版按 $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$ 計算蓄力比例。當 $T = 0$ 時，我們的 Mixin 返回延遲 $0.0$，促使原版引擎立即判定 $S(t) = 1.0$。每次命中自動打出 100% 滿額武器基礎傷害。

### Q2：為什麼未標記標籤的模組武器預設以 4 Tick 速度攻擊？
若第三方物品未實作任何原版工具標籤或 `#c:spears`，`CCAHooks.getCooldownTicks` 將自動回退至 `CombatRules.GENERIC_TICKS`（預設 4 Tick）。可透過以下指令調整：
```mcfunction
/gamerule ig:generic_cooldown_ticks <數值>
```

### Q3：本模組會導致多人連線延遲或客戶端不同步嗎？
Combat Cooldown Adjuster 完全在伺服器端權威執行。玩家連線專用伺服器時，`PlayerMixin` 注入到伺服器端玩家實體上，傷害計算具備絕對權威性。單人遊戲與區域網路環境下雙方本地執行，零網路延遲。

### Q4：遊戲規則會持久保存在世界存檔中嗎？
**會。** 全部 9 項遊戲規則均透過 DasikLibrary 由原版規則系統直接序列化到世界存檔的 `level.dat` 檔案中。重啟伺服器或重新載入世界後配置完好保留。

---

## 6. 相關文件連結
* [[返回主維基門戶|zh_tw-Home]]
* [[26.2 配置與遊戲規則矩陣|zh_tw-26.2-Configuration-and-GameRules]]
* [[26.3 配置與遊戲規則矩陣|zh_tw-26.3-Configuration-and-GameRules]]
* [[開發者配置與統一構建指南|zh_tw-Developer-Setup-and-Building]]
