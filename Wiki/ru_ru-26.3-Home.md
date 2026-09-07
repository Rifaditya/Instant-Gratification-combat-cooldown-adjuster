# ⚔️ Портал документации Minecraft 26.3

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Вики отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или разрабатываемые функции, опережающие публичные сборки на CurseForge и Modrinth.

Добро пожаловать на технический портал документации **Instant Gratification: Combat Cooldown Adjuster** для **Minecraft 26.3** (цель `26.3-snapshot-6`)! Все материалы соответствуют кодовой базе данного релиза.

---

## 🧭 Матрица навигации по разделам Minecraft 26.3

| Функция / Подсистема | Описание | Страница документации |
| :--- | :--- | :--- |
| **Механика перезарядки оружия** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.3 Механика перезарядки оружия|ru_ru-26.3-Weapon-Cooldown-Mechanics]] |
| **Динамика ударов и аудио-отклик** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.3 Динамика ударов и аудио-отклик|ru_ru-26.3-Combat-Juice-and-Audio-Feedback]] |
| **Конфигурация и игровые правила** | Complete reference matrix of all 9 namespaced GameRules | [[26.3 Конфигурация и игровые правила|ru_ru-26.3-Configuration-and-GameRules]] |
| **Архитектура и миксины** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.3 Архитектура и миксины|ru_ru-26.3-Architecture-and-Mixins]] |
| **Среда разработчика и сборка** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.3 Среда разработчика и сборка|ru_ru-26.3-Developer-Setup-and-Building]] |

---

## 📊 Официальная техническая информация

| Параметр | Техническая спецификация |
| :--- | :--- |
| **Minecraft Release Target** | `26.3-snapshot-6` |
| **Mod SemVer Release** | `1.0.1+26.3` |
| **Fabric Loader Requirement** | `0.19.3` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.8.36` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ Ключевые особенности подсистем

1. **Субтиковая настройка скорости оружия (Sub-Tick Weapon Timing)**:
   Прямое переопределение задержки в тиках ($T_{\text{{delay}}}$). Мечи: 4 тика (5.0 ударов/с), топоры: 8 тиков (2.5 ударов/с), мотыги: 1 тик (20.0 ударов/с). $T = 0$ возвращает спам-клики из 1.8.

2. **Быстрая смена слота (Hotbar Swap Agility)**:
   Правило `ig:prevent_item_swap_cooldown` устраняет сброс шкалы атаки при смене слота хотбара.

3. **Мультисенсорный сочный отклик (Multi-Sensory Combat Juice)**:
   Удары с силой >80% вызывают частицы критического удара и повышение тона звука (от $1.0\times$ до $1.4\times$).

---

## 🔗 Основные порталы
* [[Вернуться на главный портал Вики|ru_ru-Home]]
* [[Портал документации Minecraft 26.2|ru_ru-26.2-Home]]
* [[Матрица совместимости версий|ru_ru-Version-Compatibility]]
* [[Устранение неполадок и FAQ|ru_ru-Troubleshooting-and-FAQ]]
