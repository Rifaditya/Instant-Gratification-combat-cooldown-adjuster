# ⚔️ Mecânicas de Tempo de Recarga de Armas (Minecraft 26.3)

> 📌 **Isenção de Responsabilidade do Código Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das versões públicas no CurseForge e Modrinth.

## 1. Informações Técnicas Oficiais
| Parâmetro | Especificação Técnica |
| :--- | :--- |
| **Subsystem Name** | Categorical Weapon Cooldown Engine |
| **Controlling Class** | `CCAHooks.java` (`getCooldownTicks`) |
| **Bytecode Injector** | `PlayerMixin.java` (`cca$overrideAttackDelay`, `cca$preventSwapReset`) |
| **Injection Target** | `net.minecraft.world.entity.player.Player` |
| **Target Methods** | `getCurrentItemAttackStrengthDelay()F`, `resetAttackStrengthTicker()V` |
| **Supported Tags** | `#minecraft:swords`, `#minecraft:axes`, `#minecraft:pickaxes`, `#minecraft:shovels`, `#minecraft:hoes`, `#c:spears` |
| **Fallback Category** | Generic / Untagged (`CombatRules.GENERIC_TICKS`) |

---

## 2. Fluxo de Sobrevivência do Jogador

1. **Equipping Weapons**: Equip any weapon or tool in your main hand. The mod automatically inspects the held `ItemStack` against vanilla and common tag registries.
2. **Executing Attacks**: Left-click to attack. The game queries `Player#getCurrentItemAttackStrengthDelay()`, which our Mixin intercepts to return your customized tick delay.
3. **Rapid Attack Sequencing**: Because delays are set to snappy intervals (e.g. 4 ticks for Swords), your attack meter recharges in just $0.20\text{ seconds}$, allowing rapid subsequent hits with full weapon damage.
4. **Hotbar Combo Swapping**: Switch between hotbar slots during intense combat. With `ig:prevent_item_swap_cooldown` enabled, your attack meter does **not** reset to zero upon switching.
5. **Instant Spam Combat (1.8 Mode)**: Set `/gamerule ig:sword_cooldown_ticks 0` to restore unconstrained 1.8-style click-spamming mechanics.

---

## 3. Fórmulas Matemáticas e Equações

### Cálculo de Atraso de Ataque Fundamental
$$T_{\text{delay}} = \text{rule}(\text{ItemTag})$$

### Frequência de Ataque ($f$)
$$f = \frac{20}{T_{\text{delay}}} \quad (T_{\text{delay}} > 0)$$

* **Sword** ($T = 4\text{ ticks}$): $f = \frac{20}{4} = 5.0\text{ attacks/sec}$
* **Axe** ($T = 8\text{ ticks}$): $f = \frac{20}{8} = 2.5\text{ attacks/sec}$
* **Pickaxe** ($T = 4\text{ ticks}$): $f = \frac{20}{4} = 5.0\text{ attacks/sec}$
* **Shovel** ($T = 2\text{ ticks}$): $f = \frac{20}{2} = 10.0\text{ attacks/sec}$
* **Hoe** ($T = 1\text{ tick}$): $f = \frac{20}{1} = 20.0\text{ attacks/sec}$
* **Spear** ($T = 6\text{ ticks}$): $f = \frac{20}{6} = 3.33\text{ attacks/sec}$
* **Generic** ($T = 4\text{ ticks}$): $f = \frac{20}{4} = 5.0\text{ attacks/sec}$

### Curva de Escala da Força de Ataque ($S(t)$)
$$S(t) = \min\left(1.0, \, \frac{t + 0.5}{T_{\text{delay}}}\right)$$

### Escala de Dano Real Causado
$$\text{Damage}(t) = \text{BaseDamage} \times \left(0.2 + 0.8 \times S(t)^2\right)$$

### Limite de Recarga Zero (Modo 1.8)
When $T_{\text{delay}} = 0$:
$$\lim_{T \to 0} S(t) = 1.0 \implies \text{Damage} = \text{BaseDamage} \times 1.0$$

---

## 4. Diagramas ASCII e Máquina de Estados

```
       [ Left-Click Attack Initiated ]
                      |
                      v
      Player#getCurrentItemAttackStrengthDelay()
                      |
                      v
          PlayerMixin.cca$overrideAttackDelay
                      |
                      v
             CCAHooks.getCooldownTicks()
                      |
        +-------------+-------------+
        |                           |
   [ Tag Matched ]           [ Untagged Item ]
   Swords: 4 ticks           Generic: 4 ticks
   Axes:   8 ticks           (Fists, torches)
   Hoes:   1 tick
        |                           |
        +-------------+-------------+
                      |
                      v
       Return Custom Ticks to Vanilla Engine
                      |
                      v
   Attack Strength Meter Calibrated to New Duration
```

---

## 5. Esquemas de Tags de Itens e SNBT

```json
{
  "replace": false,
  "values": [
    "#c:spears"
  ]
}
```

---

## 6. Matriz de Referência Completa

| Category | Primary Tag Identifier | Default Ticks | Attack Rate ($f$) | GameRule Key | Vanilla Equivalent |
| :--- | :--- | :---: | :---: | :--- | :--- |
| **Swords** | `#minecraft:swords` | `4` | $5.0\text{ /s}$ | `ig:sword_cooldown_ticks` | ~12–16 ticks ($1.25–1.6\text{ /s}$) |
| **Axes** | `#minecraft:axes` | `8` | $2.5\text{ /s}$ | `ig:axe_cooldown_ticks` | ~20–25 ticks ($0.8–1.0\text{ /s}$) |
| **Pickaxes** | `#minecraft:pickaxes` | `4` | $5.0\text{ /s}$ | `ig:pickaxe_cooldown_ticks` | ~17 ticks ($1.2\text{ /s}$) |
| **Shovels** | `#minecraft:shovels` | `2` | $10.0\text{ /s}$ | `ig:shovel_cooldown_ticks` | ~20 ticks ($1.0\text{ /s}$) |
| **Hoes** | `#minecraft:hoes` | `1` | $20.0\text{ /s}$ | `ig:hoe_cooldown_ticks` | ~5–10 ticks ($2.0–4.0\text{ /s}$) |
| **Spears** | `#c:spears` | `6` | $3.33\text{ /s}$ | `ig:spear_cooldown_ticks` | Varies / Custom weapon speed |
| **Generic** | Untagged / Fists / Misc | `4` | $5.0\text{ /s}$ | `ig:generic_cooldown_ticks` | ~5 ticks ($4.0\text{ /s}$) |
| **Swap Agility** | Global Hotbar Event | `true` | Instant | `ig:prevent_item_swap_cooldown` | Forces reset to 0 ticks on swap |

---

## 7. Ganchos e Injeções de Mixin

```java
@Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("HEAD"), cancellable = true)
private void cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir) {
    Player player = (Player) (Object) this;
    ItemStack stack = this.getMainHandItem();
    int ticks = CCAHooks.getCooldownTicks(player, stack);
    if (ticks >= 0) {
        cir.setReturnValue((float) ticks);
    }
}

@Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"), cancellable = true)
private void cca$preventSwapReset(CallbackInfo ci) {
    Player player = (Player) (Object) this;
    if (CombatRules.getBoolean(player.level(), CombatRules.PREVENT_SWAP_RESET)) {
        ci.cancel();
    }
}
```

---

## 🔗 Links de Documentação Relacionados
* [[Voltar ao Portal do Minecraft 26.3|pt_br-26.3-Home]]
* [[26.3 Impacto de Combate e Retorno de Áudio|pt_br-26.3-Combat-Juice-and-Audio-Feedback]]
* [[26.3 Configuração e Matriz de GameRules|pt_br-26.3-Configuration-and-GameRules]]
* [[26.3 Arquitetura e Análise de Mixins|pt_br-26.3-Architecture-and-Mixins]]
