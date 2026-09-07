# ⚔️ 무기 쿨다운 메커니즘 (Minecraft 26.3)

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

## 1. 공식 기술 사양 정보
| 매개변수 | 기술 사양 |
| :--- | :--- |
| **Subsystem Name** | Categorical Weapon Cooldown Engine |
| **Controlling Class** | `CCAHooks.java` (`getCooldownTicks`) |
| **Bytecode Injector** | `PlayerMixin.java` (`cca$overrideAttackDelay`, `cca$preventSwapReset`) |
| **Injection Target** | `net.minecraft.world.entity.player.Player` |
| **Target Methods** | `getCurrentItemAttackStrengthDelay()F`, `resetAttackStrengthTicker()V` |
| **Supported Tags** | `#minecraft:swords`, `#minecraft:axes`, `#minecraft:pickaxes`, `#minecraft:shovels`, `#minecraft:hoes`, `#c:spears` |
| **Fallback Category** | Generic / Untagged (`CombatRules.GENERIC_TICKS`) |

---

## 2. 서바이벌 조작 워크플로

1. **Equipping Weapons**: Equip any weapon or tool in your main hand. The mod automatically inspects the held `ItemStack` against vanilla and common tag registries.
2. **Executing Attacks**: Left-click to attack. The game queries `Player#getCurrentItemAttackStrengthDelay()`, which our Mixin intercepts to return your customized tick delay.
3. **Rapid Attack Sequencing**: Because delays are set to snappy intervals (e.g. 4 ticks for Swords), your attack meter recharges in just $0.20\text{ seconds}$, allowing rapid subsequent hits with full weapon damage.
4. **Hotbar Combo Swapping**: Switch between hotbar slots during intense combat. With `ig:prevent_item_swap_cooldown` enabled, your attack meter does **not** reset to zero upon switching.
5. **Instant Spam Combat (1.8 Mode)**: Set `/gamerule ig:sword_cooldown_ticks 0` to restore unconstrained 1.8-style click-spamming mechanics.

---

## 3. 수학 공식 및 판정 방정식

### 기본 공격 지연 방정식
$$T_{\text{delay}} = \text{rule}(\text{ItemTag})$$

### 공격 빈도 계산 ($f$)
$$f = \frac{20}{T_{\text{delay}}} \quad (T_{\text{delay}} > 0)$$

* **Sword** ($T = 4\text{ ticks}$): $f = \frac{20}{4} = 5.0\text{ attacks/sec}$
* **Axe** ($T = 8\text{ ticks}$): $f = \frac{20}{8} = 2.5\text{ attacks/sec}$
* **Pickaxe** ($T = 4\text{ ticks}$): $f = \frac{20}{4} = 5.0\text{ attacks/sec}$
* **Shovel** ($T = 2\text{ ticks}$): $f = \frac{20}{2} = 10.0\text{ attacks/sec}$
* **Hoe** ($T = 1\text{ tick}$): $f = \frac{20}{1} = 20.0\text{ attacks/sec}$
* **Spear** ($T = 6\text{ ticks}$): $f = \frac{20}{6} = 3.33\text{ attacks/sec}$
* **Generic** ($T = 4\text{ ticks}$): $f = \frac{20}{4} = 5.0\text{ attacks/sec}$

### 공격 충전 스케일 곡선 ($S(t)$)
$$S(t) = \min\left(1.0, \, \frac{t + 0.5}{T_{\text{delay}}}\right)$$

### 실제 대미지 배율
$$\text{Damage}(t) = \text{BaseDamage} \times \left(0.2 + 0.8 \times S(t)^2\right)$$

### 쿨다운 제로 한계 상태 (1.8 모드)
When $T_{\text{delay}} = 0$:
$$\lim_{T \to 0} S(t) = 1.0 \implies \text{Damage} = \text{BaseDamage} \times 1.0$$

---

## 4. 시각적 ASCII 상태 전이도

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

## 5. SNBT 및 아이템 태그 사양

```json
{
  "replace": false,
  "values": [
    "#c:spears"
  ]
}
```

---

## 6. 종합 참조 매트릭스

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

## 7. 개발자용 Mixin 주입 분석

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

## 🔗 관련 문서 링크
* [[Minecraft 26.3 포털로 돌아가기|ko_kr-26.3-Home]]
* [[26.3 전투 타격감 및 오디오 피드백|ko_kr-26.3-Combat-Juice-and-Audio-Feedback]]
* [[26.3 구성 및 게임 규칙(GameRules) 매트릭스|ko_kr-26.3-Configuration-and-GameRules]]
* [[26.3 아키텍처 및 믹스인(Mixins) 분석|ko_kr-26.3-Architecture-and-Mixins]]
