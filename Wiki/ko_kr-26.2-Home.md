# ⚔️ Minecraft 26.2 문서 포털

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

**Minecraft 26.2**(대상 `26.1.2`)용 **Instant Gratification: Combat Cooldown Adjuster** 기술 문서 포털에 오신 것을 환영합니다! 이 버전 트리의 모든 문서는 해당 릴리스의 바이트코드 매핑 및 기능 사양을 정확히 반영합니다.

---

## 🧭 Minecraft 26.2 내비게이션 매트릭스

| 기능 / 서브시스템 | 상세 설명 | 전용 위키 페이지 |
| :--- | :--- | :--- |
| **무기 쿨다운 메커니즘** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.2 무기 쿨다운 메커니즘|ko_kr-26.2-Weapon-Cooldown-Mechanics]] |
| **전투 타격감 및 오디오 피드백** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.2 전투 타격감 및 오디오 피드백|ko_kr-26.2-Combat-Juice-and-Audio-Feedback]] |
| **구성 및 게임 규칙(GameRules) 매트릭스** | Complete reference matrix of all 9 namespaced GameRules | [[26.2 구성 및 게임 규칙(GameRules) 매트릭스|ko_kr-26.2-Configuration-and-GameRules]] |
| **아키텍처 및 믹스인(Mixins) 분석** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.2 아키텍처 및 믹스인(Mixins) 분석|ko_kr-26.2-Architecture-and-Mixins]] |
| **개발자 설정 및 툴체인** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.2 개발자 설정 및 툴체인|ko_kr-26.2-Developer-Setup-and-Building]] |

---

## 📊 공식 기술 사양 정보

| 매개변수 | 기술 사양 |
| :--- | :--- |
| **Minecraft Release Target** | `26.1.2` |
| **Mod SemVer Release** | `1.0.1+26.2` |
| **Fabric Loader Requirement** | `0.19.1` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.6.9+build.24` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ 핵심 서브시스템 하이라이트

1. **서브 틱 무기 공격 속도 (Sub-Tick Weapon Timing)**:
   정수 틱 오버라이드로 공격 딜레이를 직접 지정합니다. 검은 4틱(초당 5.0회), 도끼는 8틱(초당 2.5회), 괭이는 1틱(초당 20.0회)입니다. $T = 0$ 설정 시 순수 1.8 광클 콤보가 활성화됩니다.

2. **핫바 스왑 민첩성 (Hotbar Swap Agility)**:
   `ig:prevent_item_swap_cooldown` 활성화 시 슬롯 전환 시 게이지 리셋을 방지하여 연속 무기 스왑 콤보를 지원합니다.

3. **다감각 전투 타격감 (Multi-Sensory Combat Juice)**:
   80% 이상 충전 공격 시 이중 크리티컬 파티클 및 최대 $1.4\times$ 피치 상승 사운드가 발동합니다.

---

## 🔗 글로벌 포털 허브
* [[메인 위키 포털로 돌아가기|ko_kr-Home]]
* [[Minecraft 26.3 문서 포털|ko_kr-26.3-Home]]
* [[버전 호환성 매트릭스|ko_kr-Version-Compatibility]]
* [[문제 해결 및 FAQ|ko_kr-Troubleshooting-and-FAQ]]
