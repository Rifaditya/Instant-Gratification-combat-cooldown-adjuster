# 🛠️ 개발자 설정 및 툴체인 (Minecraft 26.3)

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

## 1. 공식 기술 사양 정보
| 매개변수 | 기술 사양 |
| :--- | :--- |
| **Minecraft Target** | `26.3-snapshot-6` |
| **Target Subproject** | `Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster/` |
| **Mod SemVer** | `1.0.1+26.3` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Build Automation** | Gradle 9.3+ with Fabric Loom |
| **Fabric Loader** | `0.19.3` |
| **Fabric API** | `0.156.1+26.3` |
| **DasikLibrary** | `1.8.36` |

---

## 2. 작업 환경 필수 구성 요소 및 설정

1. **Install OpenJDK 25**: Download and install Eclipse Adoptium Temurin OpenJDK 25.
2. **Verify Installation**:
   ```bash
   java -version
   ```

---

## 3. 단계별 빌드 워크플로

```bash
# Navigate to subproject directory
cd "Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster"

# Run non-daemon build
./gradlew build --no-daemon
```

### Output Binaries:
* `combat-cooldown-adjuster-1.0.1+26.3.jar`
* `combat-cooldown-adjuster-1.0.1+26.3-sources.jar`

---

## 4. 서브프로젝트 아키텍처 및 Loom 설정

```properties
minecraft_version=26.3-snapshot-6
fabric_version=0.156.1+26.3
fabric_loader_version=0.19.3
dasik_library_version=1.8.36
```

---

## 🔗 관련 문서 링크
* [[Minecraft 26.3 포털로 돌아가기|ko_kr-26.3-Home]]
* [[26.3 아키텍처 및 믹스인(Mixins) 분석|ko_kr-26.3-Architecture-and-Mixins]]
* [[개발자 환경 설정 및 통합 빌드 가이드|ko_kr-Developer-Setup-and-Building]]
