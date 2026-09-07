# 📊 Матрица совместимости версий

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Вики отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или разрабатываемые функции, опережающие публичные сборки на CurseForge и Modrinth.

## 1. Официальная техническая информация
| Параметр | Техническая спецификация |
| :--- | :--- |
| **Идентификатор мода** | `combat-cooldown-adjuster` |
| **Коллекция модов** | Instant Gratification (IG) |
| **Поддерживаемые версии Fabric** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Платформа Java** | OpenJDK 25 (Hotspot 64-bit) |
| **Инструменты сборки** | Gradle 9.3+ with Fabric Loom |
| **Стандарт архитектуры** | Политика 1 Jar 1 Version (Один Jar на версию) |
| **Статус публичного API** | Автономный мод (использует DasikLibrary API) |

---

## 2. Матрица совместимости версий
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 Стандартный релиз |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 Передовая ветка |

---

## 3. Политика 1 Jar 1 Version и границы библиотек

### Специализированные бинарные файлы
В отличие от разделяемых библиотек, моды, внедряющие байткод непосредственно в `net.minecraft.world.entity.player.Player`, требуют строгой проверки на этапе компиляции под конкретные маппинги Mojang.
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### Интеграция с универсальной DasikLibrary
Combat Cooldown Adjuster использует **DasikLibrary** для динамической регистрации правил (`DynamicGameRuleManager`). DasikLibrary гарантирует:
1. Полную совместимость с сериализацией GameRule на сервере.
2. Динамическое автодополнение в чате через Tab для `/gamerule`.
3. Безопасность загрузчика классов (предотвращение сбоев на клиенте).

---

## 4. Рабочий процесс установки и требования

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

### Контрольный список проверки установки:
1. Убедитесь, что среда выполнения — OpenJDK 25 Hotspot (например, Eclipse Adoptium `jdk-25.0.3+`).
2. Проверьте наличие `fabric-api` в папке `mods`.
3. Проверьте наличие `dasik-library` в папке `mods`.
4. Запустите игру и найдите в логах строку:
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. Ссылки на связанные разделы
* [[Вернуться на главный портал Вики|ru_ru-Home]]
* [[26.2 Механика перезарядки оружия|ru_ru-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 Механика перезарядки оружия|ru_ru-26.3-Weapon-Cooldown-Mechanics]]
* [[Устранение неполадок и FAQ|ru_ru-Troubleshooting-and-FAQ]]
