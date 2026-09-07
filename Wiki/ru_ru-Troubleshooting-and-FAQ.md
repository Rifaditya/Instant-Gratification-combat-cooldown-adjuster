# 🔧 Устранение неполадок и FAQ

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Вики отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или разрабатываемые функции, опережающие публичные сборки на CurseForge и Modrinth.

## 1. Официальная техническая информация
| Параметр | Диагностические данные |
| :--- | :--- |
| **Целевая подсистема** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **Управляющие игровые правила** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **Ключевая точка внедрения** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **Основная зависимость** | `dasik-library` (Dynamic GameRule Manager) |
| **Пространство имен логов** | `combat-cooldown-adjuster` |

---

## 2. Блок-схема диагностики

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

## 3. Пошаговое руководство по диагностике

### Фаза 1: Калибровка тиков задержки оружия
1. Откройте внутриигровой чат или консоль сервера.
2. Запросите текущее значение тиков для вашего типа оружия:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. Если возвращается значение по умолчанию `4`, частота атак составляет:
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ атак/сек}}
4. Чтобы включить мгновенный спам кликами (стиль 1.8), выполните:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. Удары по мобам будут регистрироваться мгновенно без задержки.

### Фаза 2: Диагностика быстрой смены оружия
1. Поместите меч в слот 1 и топор в слот 2.
2. Взмахните мечом для атаки.
3. Сразу нажмите клавишу `2` для переключения на топор.
4. Если `ig:prevent_item_swap_cooldown` равен `true`, индикатор атаки **не** сбросится в ноль, а продолжит зарядку.
5. Если шкала сбрасывается, выполните:
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### Фаза 3: Оптимизация частиц и звука
1. Combat Juice активируется, когда сила атаки превышает $0.8$ (80% заряда).
2. Если на слабых ПК возникают микрофризы от обилия частиц, отключите эффект на сервере:
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. Это полностью отключает вызовы `sendParticles` и `playSound` в `CCAHooks.applyCombatJuice`.

---

## 4. Таблица математической калибровки

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

## 5. Часто задаваемые вопросы (FAQ)

### Q1: Не ломает ли установка задержки в 0 тиков расчет урона ваниллы?
**Нет.** Ванилла вычисляет силу атаки по формуле $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$. При $T = 0$ наш Mixin возвращает задержку $0.0$, и игра сразу принимает $S(t) = 1.0$. Каждый удар наносит 100% базового урона.

### Q2: Почему оружие из других модов без тегов атакует со скоростью 4 тика?
Если предмет из мода не имеет ванильных тегов или `#c:spears`, `CCAHooks.getCooldownTicks` использует `CombatRules.GENERIC_TICKS` (по умолчанию: 4 тика). Настроить можно командой:
```mcfunction
/gamerule ig:generic_cooldown_ticks <значение>
```

### Q3: Вызывает ли мод сетевые задержки или рассинхронизацию?
Combat Cooldown Adjuster полностью авторитарен на сервере. При подключении к серверу `PlayerMixin` внедряется в сущность игрока на сервере, и расчет урона точен. В одиночной игре задержка равна нулю.

### Q4: Сохраняются ли правила GameRule в мире?
**Да.** Все 9 игровых правил сохраняются в файл `level.dat` мира через DasikLibrary.

---

## 6. Ссылки на связанные разделы
* [[Вернуться на главный портал Вики|ru_ru-Home]]
* [[26.2 Конфигурация и игровые правила|ru_ru-26.2-Configuration-and-GameRules]]
* [[26.3 Конфигурация и игровые правила|ru_ru-26.3-Configuration-and-GameRules]]
* [[Среда разработчика и руководство по сборке|ru_ru-Developer-Setup-and-Building]]
