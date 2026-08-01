# ⚙️ GameRule Reference

The following rules are registered under the `ig:combat_cooldown` category.

| Key | Type | Default | Description |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | Integer | 4 | Attack delay for Swords (in ticks). |
| `ig:axe_cooldown_ticks` | Integer | 8 | Attack delay for Axes (in ticks). |
| `ig:pickaxe_cooldown_ticks` | Integer | 4 | Attack delay for Pickaxes (in ticks). |
| `ig:shovel_cooldown_ticks` | Integer | 2 | Attack delay for Shovels (in ticks). |
| `ig:hoe_cooldown_ticks` | Integer | 1 | Attack delay for Hoes (in ticks). |
| `ig:spear_cooldown_ticks` | Integer | 6 | Attack delay for items in `c:spears` tag. |
| `ig:generic_cooldown_ticks` | Integer | 4 | Fallback attack delay for untagged items. |
| `ig:prevent_item_swap_cooldown` | Boolean | true | If true, item swapping doesn't reset the ticker. |
| `ig:enable_combat_juice` | Boolean | true | Toggles extra particles and sound feedback. |

---

## 🔧 Technical Implementation
Rules are registered via `DynamicGameRuleManager` from **DasikLibrary**. 

### Accessing Rules via Code:
```java
// Get sword ticks for the current level
int ticks = CombatRules.getInt(level, CombatRules.SWORD_TICKS);
```

### Key Mapping:
The internal `Identifier` for the category is:
`combat-cooldown-adjuster:combat_cooldown`
