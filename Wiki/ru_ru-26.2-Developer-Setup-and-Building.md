# 🛠️ Среда разработчика и сборка (Minecraft 26.2)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Вики отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или разрабатываемые функции, опережающие публичные сборки на CurseForge и Modrinth.

## 1. Официальная техническая информация
| Параметр | Техническая спецификация |
| :--- | :--- |
| **Minecraft Target** | `26.1.2` |
| **Target Subproject** | `Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster/` |
| **Mod SemVer** | `1.0.1+26.2` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Build Automation** | Gradle 9.3+ with Fabric Loom |
| **Fabric Loader** | `0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.6.9+build.24` |

---

## 2. Требования к среде разработки

1. **Install OpenJDK 25**: Download and install Eclipse Adoptium Temurin OpenJDK 25.
2. **Verify Installation**:
   ```bash
   java -version
   ```

---

## 3. Пошаговый процесс сборки

```bash
# Navigate to subproject directory
cd "Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster"

# Run non-daemon build
./gradlew build --no-daemon
```

### Output Binaries:
* `combat-cooldown-adjuster-1.0.1+26.2.jar`
* `combat-cooldown-adjuster-1.0.1+26.2-sources.jar`

---

## 4. Архитектура подпроекта и настройки Loom

```properties
minecraft_version=26.1.2
fabric_version=0.145.4+26.1.2
fabric_loader_version=0.19.1
dasik_library_version=1.6.9+build.24
```

---

## 🔗 Ссылки на связанные разделы
* [[Вернуться к порталу Minecraft 26.2|ru_ru-26.2-Home]]
* [[26.2 Архитектура и миксины|ru_ru-26.2-Architecture-and-Mixins]]
* [[Среда разработчика и руководство по сборке|ru_ru-Developer-Setup-and-Building]]
