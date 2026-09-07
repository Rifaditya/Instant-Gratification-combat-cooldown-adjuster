# 💥 전투 타격감 및 오디오 피드백 (Minecraft 26.3)

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

## 1. 공식 기술 사양 정보
| 매개변수 | 기술 사양 |
| :--- | :--- |
| **Subsystem Name** | 동적 전투 감각 피드백 시스템 |
| **Controlling GameRule** | `ig:enable_combat_juice` (Default: `true`) |
| **Trigger Threshold** | Attack Strength Scale $S > 0.8$ ($80\%$) |
| **Particle Types** | `ParticleTypes.CRIT`, `ParticleTypes.ENCHANTED_HIT` |
| **Sound Event** | `SoundEvents.PLAYER_ATTACK_STRONG` |
| **Sound Category** | `SoundSource.PLAYERS` |
| **Audio Pitch Range** | $1.0\times$ to $1.4\times$ (Dynamic Charge Shift) |
| **Bytecode Hook** | `PlayerMixin.cca$applyJuice` $\to$ `CCAHooks.applyCombatJuice` |

---

## 2. 서바이벌 조작 워크플로
1. **충전**: 충전율 80% 이상 도달 (3.2틱 소요).
2. **타격**: 엔티티 좌클릭 타격 실행.
3. **파티클 폭발**: 대상 중심에 10개 크리티컬 + 5개 인챈트 파티클 분출.
4. **사운드 피치**: `SoundEvents.PLAYER_ATTACK_STRONG`이 최대 $1.4\times$ 피치로 재생.
5. **설정**: `/gamerule ig:enable_combat_juice false`로 언제든 끄기 가능.

---

## 3. 수학 공식 및 판정 방정식

### 발동 조건 방정식
$$\text{JuiceEnabled} = \text{true} \quad \land \quad S_{\text{strength}} > 0.8$$

### 동적 피치 계산 공식
$$\text{Pitch} = 1.0 + (S_{\text{strength}} - 0.8) \times 2.0$$

경계값 분석:
* 임계 하한 ($S = 0.8$): $\text{{Pitch}} = 1.00$
* 중간 충전 ($S = 0.9$): $\text{{Pitch}} = 1.20$
* 최대 충전 ($S = 1.0$): $\text{{Pitch}} = 1.40$

### 파티클 생성 사양
* **`ParticleTypes.CRIT`**: Count $N = 10$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.
* **`ParticleTypes.ENCHANTED_HIT`**: Count $N = 5$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.

---

## 4. 시각적 ASCII 상태 전이도

```
                 [ Player Attacks Target Entity ]
                                |
                                v
                   PlayerMixin.cca$applyJuice
                                |
                                v
                   CCAHooks.applyCombatJuice
                                |
                Is ig:enable_combat_juice true?
                       /                 \
                     YES                  NO
                     /                     \
        Get attackStrengthScale(0.5f)     (Return silently)
                     |
            Is attackStrength > 0.8?
                   /         \
                 YES          NO
                 /             \
      [ Emit Visual & Audio ]  (Return silently)
         |
         +--> ServerLevel.sendParticles(CRIT, count=10)
         +--> ServerLevel.sendParticles(ENCHANTED_HIT, count=5)
         +--> Calculate Pitch = 1.0 + (attackStrength - 0.8) * 2.0
         +--> Level.playSound(PLAYER_ATTACK_STRONG, volume=1.0, pitch)
```

---

## 5. SNBT 및 아이템 태그 사양

```json
{
  "sound": "minecraft:entity.player.attack.strong",
  "source": "PLAYERS",
  "volume": 1.0,
  "pitch_min": 1.0,
  "pitch_max": 1.4
}
```

---

## 6. 종합 참조 매트릭스

| Charge Scale ($S$) | Charge Status | Particle Burst | Sound Event | Audio Pitch |
| :---: | :--- | :--- | :--- | :---: |
| **$0.00 - 0.80$** | Incomplete Charge | None | Default vanilla hit | Vanilla |
| **$0.81$** | Threshold Reached | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.02\times$ |
| **$0.85$** | Strong Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.10\times$ |
| **$0.90$** | High-Power Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.20\times$ |
| **$0.95$** | Near-Max Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.30\times$ |
| **$1.00$** | Full Max Strike | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.40\times$ |

---

## 7. 개발자용 Mixin 주입 분석

```java
public static void applyCombatJuice(Player player, Entity target) {
    if (!CombatRules.getBoolean(player.level(), CombatRules.ENABLE_JUICE)) return;

    float attackStrength = player.getAttackStrengthScale(0.5f);
    if (attackStrength > 0.8f) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY(0.5), target.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
            serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, target.getX(), target.getY(0.5), target.getZ(), 5, 0.1, 0.1, 0.1, 0.1);
        }
        
        float pitch = 1.0f + (attackStrength - 0.8f) * 2.0f;
        player.level().playSound(null, target.getX(), target.getY(), target.getZ(), 
            SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1.0f, pitch);
    }
}
```

---

## 🔗 관련 문서 링크
* [[Minecraft 26.3 포털로 돌아가기|ko_kr-26.3-Home]]
* [[26.3 무기 쿨다운 메커니즘|ko_kr-26.3-Weapon-Cooldown-Mechanics]]
* [[26.3 구성 및 게임 규칙(GameRules) 매트릭스|ko_kr-26.3-Configuration-and-GameRules]]
* [[26.3 아키텍처 및 믹스인(Mixins) 분석|ko_kr-26.3-Architecture-and-Mixins]]
