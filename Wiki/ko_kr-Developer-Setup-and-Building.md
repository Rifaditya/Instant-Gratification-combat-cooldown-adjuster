# 🛠️ 개발자 환경 설정 및 통합 빌드 가이드

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

## 1. 공식 기술 사양 정보
| 매개변수 | 기술 사양 |
| :--- | :--- |
| **빌드 도구 체인** | Gradle 9.3+ with Fabric Loom |
| **Java 실행 환경** | OpenJDK 25 (Hotspot 64-bit) |
| **모드 컬렉션** | Multi-Version Anchor Subprojects (`26.2` & `26.3`) |
| **Bytecode Injector** | SpongePowered Mixin 0.8+ |
| **핵심 의존성** | Fabric API, DasikLibrary (`DynamicGameRuleManager`) |
| **Compilation Flag** | `./gradlew build --no-daemon` |

---

## 2. 작업 환경 필수 구성 요소 및 설정

Building Combat Cooldown Adjuster from source requires an active JDK 25 installation.

```bash
# Verify Java version
java -version
# Expected output: openjdk version "25" ...
```

---

## 3. 단계별 빌드 워크플로

```
[ Clone Repository ]
         |
         v
[ Navigate to Target Subproject Directory ]
  - Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster/
  - Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster/
         |
         v
[ Verify gradle.properties Configuration ]
         |
         v
[ Execute Unified Build Command ]
  ./gradlew build --no-daemon
         |
         v
[ Inspect Output Artifacts in build/libs/ ]
```

```bash
# For Linux / macOS:
./gradlew build --no-daemon

# For Windows PowerShell:
.\gradlew build --no-daemon
```

Artifacts are generated in `build/libs/`:
* Standard binary: `combat-cooldown-adjuster-1.0.1+<mc_version>.jar`
* Sources jar: `combat-cooldown-adjuster-1.0.1+<mc_version>-sources.jar`

---

## 4. 서브프로젝트 아키텍처 및 Loom 설정

```properties
org.gradle.parallel=false
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true
```

### Dependency Matrix across Anchors:
* **Version 26.2 (`gradle.properties`)**:
  ```properties
  minecraft_version=26.1.2
  fabric_version=0.145.4+26.1.2
  fabric_loader_version=0.19.1
  dasik_library_version=1.6.9+build.24
  ```
* **Version 26.3 (`gradle.properties`)**:
  ```properties
  minecraft_version=26.3-snapshot-6
  fabric_version=0.156.1+26.3
  fabric_loader_version=0.19.3
  dasik_library_version=1.8.36
  ```

---

## 5. Mixin 아키텍처 및 순수성 표준
Combat Cooldown Adjuster는 엄격한 **Mixin 순수성 표준**을 준수합니다:
1. **Mixin 클래스 내 로직 배제**: `PlayerMixin.java`에는 연산이나 문자열 조작이 포함되지 않습니다.
2. **정적 유틸리티 위임**: 모든 수학 연산과 파티클 처리는 `CCAHooks.java`에 캡슐화되어 있습니다.
3. **취소 안전성**: `@At("HEAD")` 주입 지점에서 `cancellable = true`를 선언하여 안전하게 흐름을 제어합니다.

### Injected Target Signatures in `PlayerMixin.java`:
```java
// 1. Override weapon cooldown delay
@Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("HEAD"), cancellable = true)
private void cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir) {
    Player player = (Player) (Object) this;
    ItemStack stack = this.getMainHandItem();
    int ticks = CCAHooks.getCooldownTicks(player, stack);
    if (ticks >= 0) {
        cir.setReturnValue((float) ticks);
    }
}

// 2. Prevent attack meter reset on hotbar item switch
@Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"), cancellable = true)
private void cca$preventSwapReset(CallbackInfo ci) {
    Player player = (Player) (Object) this;
    if (CombatRules.getBoolean(player.level(), CombatRules.PREVENT_SWAP_RESET)) {
        ci.cancel();
    }
}

// 3. Inject Combat Juice particle and sound feedback
@Inject(method = "attack", at = @At("HEAD"))
private void cca$applyJuice(Entity target, CallbackInfo ci) {
    Player player = (Player) (Object) this;
    CCAHooks.applyCombatJuice(player, target);
}
```

---

## 6. 애드온 개발자용 데이터 기반 확장 인터페이스
`#c:spears`를 활용한 창 아이템 연동:
`data/c/tags/item/spears.json`에 아이템 식별자를 추가하기만 하면 창 쿨다운 규칙이 자동으로 적용됩니다.

```json
{
  "replace": false,
  "values": [
    "examplemod:iron_spear",
    "examplemod:diamond_spear"
  ]
}
```

---

## 7. 관련 문서 링크
* [[메인 위키 포털로 돌아가기|ko_kr-Home]]
* [[26.2 아키텍처 및 믹스인(Mixins) 분석|ko_kr-26.2-Architecture-and-Mixins]]
* [[26.3 아키텍처 및 믹스인(Mixins) 분석|ko_kr-26.3-Architecture-and-Mixins]]
* [[버전 호환성 매트릭스|ko_kr-Version-Compatibility]]
