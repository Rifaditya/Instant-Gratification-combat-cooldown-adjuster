# ⚙️ Konfiguration & GameRules-Matrix (Minecraft 26.2)

> 📌 **Quellcode-Repository-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Release-Builds auf CurseForge und Modrinth enthalten kann.

## 1. Offizielle technische Spezifikation
| Parameter | Technische Spezifikation |
| :--- | :--- |
| **Category Identifier** | `combat-cooldown-adjuster:combat_cooldown` |
| **Total Registered Rules** | 9 (7 Integers, 2 Booleans) |
| **Registry Manager** | `DynamicGameRuleManager` (DasikLibrary) |
| **Runtime Mutability** | In-game `/gamerule <name> <value>` (Zero restart latency) |
| **Storage Persistence** | World save `level.dat` |
| **Player Agency Standard** | Anti-Nanny Invariant (Permits values from 0 up to `Integer.MAX_VALUE`) |

---

## 2. Überlebens-Gameplay & Bedienung

1. **Viewing Current Rules**: Type `/gamerule ig:` to view tab-completion of all 9 registered rules.
2. **Modifying Attack Delays**: Set sword cooldown to 2 ticks: `/gamerule ig:sword_cooldown_ticks 2`
3. **Pure 1.8 Click-Spam**: Set `/gamerule ig:sword_cooldown_ticks 0`
4. **Toggling Swap Agility**: Set `/gamerule ig:prevent_item_swap_cooldown true`
5. **Managing Combat Juice**: Set `/gamerule ig:enable_combat_juice true`

---

## 3. Prinzip der Spielerfreiheit (Anti-Nanny Invariant)
Die Mod befolgt strikt das **Player Agency & Anti-Nanny Invariant**:
* **Keine künstlichen Obergrenzen**: Ticks können bis zu `Integer.MAX_VALUE` ($2,147,483,647$) eingestellt werden.
* **Sichere Untergrenze**: $T \ge 0$ verhindert Divisionen durch 0.

---

## 4. GameRule-Lebenszyklus

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

## 5. Vollständige Referenztabelle

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

## 6. Entwickler- & Mixin-Analyse

```java
int swordTicks = CombatRules.getInt(level, CombatRules.SWORD_TICKS);
boolean swapAgility = CombatRules.getBoolean(level, CombatRules.PREVENT_SWAP_RESET);
```

---

## 🔗 Weiterführende Links
* [[Zurück zum Minecraft 26.2 Portal|de_de-26.2-Home]]
* [[26.2 Waffen-Abklingzeit-Mechanik|de_de-26.2-Weapon-Cooldown-Mechanics]]
* [[26.2 Kampffeedback & Audioeffekte|de_de-26.2-Combat-Juice-and-Audio-Feedback]]
* [[26.2 Architektur & Mixin-Analyse|de_de-26.2-Architecture-and-Mixins]]
