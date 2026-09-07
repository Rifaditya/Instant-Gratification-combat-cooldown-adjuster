# 🔧 문제 해결 및 FAQ

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

## 1. 공식 기술 사양 정보
| 매개변수 | 진단 세부 정보 |
| :--- | :--- |
| **대상 서브시스템** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **제어 게임 규칙** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **주요 주입 지점** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **핵심 의존성** | `dasik-library` (Dynamic GameRule Manager) |
| **로그 네임스페이스** | `combat-cooldown-adjuster` |

---

## 2. 진단 흐름도

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

## 3. 단계별 문제 해결 워크플로

### 1단계: 무기 틱(Tick) 지연 조정
1. 게임 내 채팅창 또는 서버 콘솔을 엽니다.
2. 현재 무기 유형의 활성 틱 값을 확인합니다:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. 기본값인 `4`가 반환되면 공격 속도는 다음과 같습니다:
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ 회 공격/초}}
4. 마인크래프트 1.8 스타일의 광클(Click-Spam)을 테스트하려면 다음을 실행합니다:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. 지연 시간 없이 즉각적인 공격 판정이 이루어집니다.

### 2단계: 핫바 교체 민첩성 진단
1. 핫바 1번에 검, 2번에 도끼를 장착합니다.
2. 검으로 공격하여 쿨다운을 발생시킵니다.
3. 즉시 숫자 키 `2`를 눌러 도끼로 전환합니다.
4. `ig:prevent_item_swap_cooldown`이 `true`이면 공격 충전 게이지가 0으로 초기화되지 않고 부드럽게 계속 충전됩니다.
5. 게이지가 초기화된다면 다음 명령어를 실행하세요:
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### 3단계: 입자 및 오디오 최적화
1. 전투 타격감(Combat Juice)은 공격 충전율이 80%($S > 0.8$)를 넘을 때 발동합니다.
2. 저사양 환경에서 프레임 드랍이 발생할 경우 서버 측에서 비활성화할 수 있습니다:
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. 이렇게 하면 `CCAHooks.applyCombatJuice`의 파티클 및 사운드 호출이 생략되어 리소스를 절약합니다.

---

## 4. 수학적 교정 참조 매트릭스

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

## 5. 자주 묻는 질문 (FAQ)

### Q1: 쿨다운 틱을 0으로 설정하면 바닐라 대미지 계산이 손상되나요?
**아닙니다.** 바닐라는 $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$로 충전율을 계산합니다. $T = 0$일 때 Mixin이 $0.0$을 반환하여 즉시 $S(t) = 1.0$으로 평가되므로 모든 타격에 100% 온전한 대미지가 적용됩니다.

### Q2: 태그가 없는 모드 무기가 기본 4틱으로 공격하는 이유는 무엇인가요?
서드파티 아이템이 바닐라 도구 태그나 `#c:spears`를 구현하지 않은 경우, `CCAHooks.getCooldownTicks`는 `CombatRules.GENERIC_TICKS`(기본값: 4틱)로 대체됩니다. 다음 명령어로 수정할 수 있습니다:
```mcfunction
/gamerule ig:generic_cooldown_ticks <값>
```

### Q3: 멀티플레이어 환경에서 지연이나 동기화 오류가 발생하나요?
Combat Cooldown Adjuster는 서버 권한으로 완벽하게 동작합니다. 전용 서버 연결 시 `PlayerMixin`이 서버 플레이어 엔티티에 주입되어 완벽한 판정을 보장합니다. 싱글플레이 및 LAN 환경에서도 지연 없이 쾌적합니다.

### Q4: 게임 규칙 설정이 월드 세이브에 저장되나요?
**저장됩니다.** 9개의 모든 게임 규칙은 DasikLibrary를 통해 월드의 `level.dat`에 직접 직렬화되어 서버 재시작 후에도 안전하게 유지됩니다.

---

## 6. 관련 문서 링크
* [[메인 위키 포털로 돌아가기|ko_kr-Home]]
* [[26.2 구성 및 게임 규칙(GameRules) 매트릭스|ko_kr-26.2-Configuration-and-GameRules]]
* [[26.3 구성 및 게임 규칙(GameRules) 매트릭스|ko_kr-26.3-Configuration-and-GameRules]]
* [[개발자 환경 설정 및 통합 빌드 가이드|ko_kr-Developer-Setup-and-Building]]
