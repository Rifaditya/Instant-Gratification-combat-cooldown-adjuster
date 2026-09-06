# ⚔️ 즉각적인 만족: 전투 쿨다운 조절기 (Combat Cooldown Adjuster) 한국어 위키

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **저장소 소스 면책 조항**: 이 위키의 문서는 CurseForge 및 Modrinth의 공개 릴리스 빌드보다 앞선 최신 미출시 커밋 또는 개발 중인 기능을 포함할 수 있는 **저장소의 현재 소스 코드 상태**를 반영합니다.

Minecraft Fabric용 **Combat Cooldown Adjuster** 공식 기술 위키에 오신 것을 환영합니다! 본 모드는 Minecraft 1.9+ 이후 도입된 인위적인 공격 딜레이를 제거하여 빠르고 역동적인 근접 전투 감각을 되살리며, 정밀한 틱 설정과 타격감 넘치는 감각 피드백을 제공합니다.

---

## 🧭 버전별 문서 포털

모드의 **1 Jar 1 Version** 원칙에 따라, 지원되는 각 Minecraft 버전별로 독립된 문서 트리가 제공됩니다:

| 마인크래프트 버전 | 모드 SemVer | Fabric Loader | 문서 포털 |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 Minecraft 26.2 문서 포털|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 Minecraft 26.3 문서 포털|26.3-Home]] |

---

## ⚡ 핵심 기능

1. **무기 범주별 틱 오버라이드 (Categorical Tick Overrides)**:
   아이템 태그별로 공격 쿨다운 틱을 직접 설정합니다 (20틱 = 1초). 검은 기본 4틱 (초당 5회 공격), 도끼는 8틱, 괭이는 1틱 (초당 20회 공격). 값을 `0`으로 설정하면 Minecraft 1.8 스타일의 즉각적인 광클 전투가 가능합니다!
2. **핫바 교체 민첩성 (Swap Agility)**:
   `ig:prevent_item_swap_cooldown` 규칙을 통해 핫바 슬롯을 전환해도 공격 게이지가 초기화되지 않아 매끄러운 콤보 전환이 가능합니다.
3. **타격감 강화 (Combat Juice)**:
   게이지가 80% 이상 ($S > 0.8$) 충전된 상태에서 타격 시 크리티컬 및 마법 파티클이 분출되며, 공격 사운드의 피치가 $1.0\times$에서 $1.4\times$까지 동적으로 상승합니다.
4. **DasikLibrary 기반 동적 GameRules**:
   모든 9가지 설정은 `/gamerule` 명령어를 통해 서버 재부팅 없이 실시간으로 변경할 수 있습니다.

---

## 📊 GameRules 요약표

| GameRule | 타입 | 기본값 | 설명 |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | 정수 | `4` | 검 공격 쿨다운 틱 (0.2초) |
| `ig:axe_cooldown_ticks` | 정수 | `8` | 도끼 공격 쿨다운 틱 (0.4초) |
| `ig:pickaxe_cooldown_ticks` | 정수 | `4` | 곡괭이 공격 쿨다운 틱 |
| `ig:shovel_cooldown_ticks` | 정수 | `2` | 삽 공격 쿨다운 틱 |
| `ig:hoe_cooldown_ticks` | 정수 | `1` | 괭이 공격 쿨다운 틱 (초고속 연타) |
| `ig:spear_cooldown_ticks` | 정수 | `6` | 창 공격 쿨다운 틱 (`#c:spears`) |
| `ig:generic_cooldown_ticks` | 정수 | `4` | 맨손 및 기타 아이템 공격 쿨다운 |
| `ig:prevent_item_swap_cooldown` | 불리언 | `true` | 핫바 아이템 변경 시 게이지 초기화 방지 |
| `ig:enable_combat_juice` | 불리언 | `true` | 강타 시 파티클 및 동적 피치 사운드 활성화 |

---

## ⚖️ 라이선스 및 저작권
* **제작자**: Dasik (Rifaditya)
* **라이선스**: GNU General Public License v3.0 (GPLv3)
* **메인 포털**: [[영어 메인 포털로 돌아가기|Home]]
