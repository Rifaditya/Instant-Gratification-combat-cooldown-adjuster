# ⚙️ 配置與遊戲規則矩陣 (Minecraft 26.2)

> 📌 **程式碼倉庫來源免責聲明**：本維基文件反映了**程式碼倉庫中的當前源碼狀態**，可能包含領先於 CurseForge 與 Modrinth 平臺公開發布版本的最新未發布提交或開發中功能。

## 1. 官方技術資訊
| 參數 | 技術規範 |
| :--- | :--- |
| **Category Identifier** | `combat-cooldown-adjuster:combat_cooldown` |
| **Total Registered Rules** | 9 (7 Integers, 2 Booleans) |
| **Registry Manager** | `DynamicGameRuleManager` (DasikLibrary) |
| **Runtime Mutability** | In-game `/gamerule <name> <value>` (Zero restart latency) |
| **Storage Persistence** | World save `level.dat` |
| **Player Agency Standard** | Anti-Nanny Invariant (Permits values from 0 up to `Integer.MAX_VALUE`) |

---

## 2. 生存戰鬥操作流程

1. **Viewing Current Rules**: Type `/gamerule ig:` to view tab-completion of all 9 registered rules.
2. **Modifying Attack Delays**: Set sword cooldown to 2 ticks: `/gamerule ig:sword_cooldown_ticks 2`
3. **Pure 1.8 Click-Spam**: Set `/gamerule ig:sword_cooldown_ticks 0`
4. **Toggling Swap Agility**: Set `/gamerule ig:prevent_item_swap_cooldown true`
5. **Managing Combat Juice**: Set `/gamerule ig:enable_combat_juice true`

---

## 3. 玩家主權與反說教原則 (Anti-Nanny Invariant)
本模組嚴格恪守 **Player Agency & Anti-Nanny Invariant**：
* **不設人為上限**：Tick 延遲允許設定至 `Integer.MAX_VALUE` ($2,147,483,647$)，玩家享有完全自由。
* **非負下界守衛**：強制下界 $T \ge 0$，杜絕除零或負數引發的遊戲引擎當機。

---

## 4. 遊戲規則生命週期圖

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

## 5. 全面參考矩陣

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

## 6. 開發者與 Mixin 注入分析

```java
int swordTicks = CombatRules.getInt(level, CombatRules.SWORD_TICKS);
boolean swapAgility = CombatRules.getBoolean(level, CombatRules.PREVENT_SWAP_RESET);
```

---

## 🔗 相關文件連結
* [[返回 Minecraft 26.2 門戶|zh_tw-26.2-Home]]
* [[26.2 武器冷卻機制|zh_tw-26.2-Weapon-Cooldown-Mechanics]]
* [[26.2 戰鬥打擊感與音效回饋|zh_tw-26.2-Combat-Juice-and-Audio-Feedback]]
* [[26.2 架構設計與 Mixin 解析|zh_tw-26.2-Architecture-and-Mixins]]
