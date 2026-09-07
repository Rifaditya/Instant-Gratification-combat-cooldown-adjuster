# 📊 버전 호환성 매트릭스

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

## 1. 공식 기술 사양 정보
| 매개변수 | 기술 사양 |
| :--- | :--- |
| **모드 식별자** | `combat-cooldown-adjuster` |
| **모드 컬렉션** | Instant Gratification (IG) |
| **지원되는 Fabric 앵커 버전** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Java 실행 환경** | OpenJDK 25 (Hotspot 64-bit) |
| **빌드 도구 체인** | Gradle 9.3+ with Fabric Loom |
| **아키텍처 표준** | 1 Jar 1 Version (1버전 1Jar 정책) |
| **공개 API 상태** | 독립형 모드 (DasikLibrary API 사용) |

---

## 2. 버전 호환성 매트릭스
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 표준 앵커 |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 최신 스냅샷 브랜치 |

---

## 3. 1 Jar 1 Version 정책 및 범용 라이브러리 경계

### 전용 모드 바이너리 빌드
넓은 버전 범위에서 순수 연산 API를 제공하는 공용 라이브러리와 달리, `net.minecraft.world.entity.player.Player`에 직접 바이트코드를 주입하는 모드는 대상 버전의 Mojang 난독화 매핑 및 메서드 서명과 엄격하게 컴파일 시점에 검증되어야 합니다.
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### 범용 DasikLibrary 통합
Combat Cooldown Adjuster는 런타임 동적 게임 규칙 등록(`DynamicGameRuleManager`)을 위해 **DasikLibrary**에 의존합니다. DasikLibrary는 개방형 버전 경계(`>=26.1.2-`)를 따르며 다음을 보장합니다:
1. 서버 GameRule 직렬화와의 완벽한 전후방 호환성.
2. 게임 내 `/gamerule` 동적 Tab 자동 완성 완벽 지원.
3. 클라이언트 클래스로더 안전 가드 (서버 전용 평가로 클라이언트 충돌 방지).

---

## 4. 설치 및 필수 구성 요소 워크플로

```
[ Download Compatible Mod Jar ]
               |
               +---> Check Minecraft Target (26.2 vs 26.3)
               |
[ Verify Dependencies ]
       |
       +---> Fabric Loader (>=0.19.1 for 26.2 | >=0.19.3 for 26.3)
       +---> Fabric API (matching MC release)
       +---> DasikLibrary (>=1.6.9+ for 26.2 | >=1.8.36 for 26.3)
               |
[ Deploy to .minecraft/mods/ ]
               |
[ Launch Game with JDK 25 ]
```

### 설치 확인 체크리스트:
1. Java 런타임이 OpenJDK 25 Hotspot(예: Eclipse Adoptium `jdk-25.0.3+`)인지 확인.
2. `mods` 폴더에 `fabric-api`가 있는지 확인.
3. `mods` 폴더에 `dasik-library`가 있는지 확인.
4. 게임을 실행하고 로그에 다음 출력이 나타나는지 확인:
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. 관련 문서 링크
* [[메인 위키 포털로 돌아가기|ko_kr-Home]]
* [[26.2 무기 쿨다운 메커니즘|ko_kr-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 무기 쿨다운 메커니즘|ko_kr-26.3-Weapon-Cooldown-Mechanics]]
* [[문제 해결 및 FAQ|ko_kr-Troubleshooting-and-FAQ]]
