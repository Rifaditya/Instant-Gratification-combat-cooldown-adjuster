# 💥 Динамика ударов и аудио-отклик (Minecraft 26.3)

> 📌 **Отказ от ответственности за исходный код репозитория**: Документация в этой Вики отражает **текущее состояние исходного кода в репозитории**, которое может включать недавние невыпущенные коммиты или разрабатываемые функции, опережающие публичные сборки на CurseForge и Modrinth.

## 1. Официальная техническая информация
| Параметр | Техническая спецификация |
| :--- | :--- |
| **Subsystem Name** | Динамическая сенсорная обратная связь боя |
| **Controlling GameRule** | `ig:enable_combat_juice` (Default: `true`) |
| **Trigger Threshold** | Attack Strength Scale $S > 0.8$ ($80\%$) |
| **Particle Types** | `ParticleTypes.CRIT`, `ParticleTypes.ENCHANTED_HIT` |
| **Sound Event** | `SoundEvents.PLAYER_ATTACK_STRONG` |
| **Sound Category** | `SoundSource.PLAYERS` |
| **Audio Pitch Range** | $1.0\times$ to $1.4\times$ (Dynamic Charge Shift) |
| **Bytecode Hook** | `PlayerMixin.cca$applyJuice` $\to$ `CCAHooks.applyCombatJuice` |

---

## 2. Игровой процесс выживания
1. **Зарядка**: Шкала атаки превышает 80% ($S > 0.8$, всего 3.2 тика).
2. **Удар**: Левый клик по цели вызывает `Player#attack`.
3. **Вспышка частиц**: Сервер генерирует 10 частиц Crit и 5 частиц Enchanted Hit в центре хитбокса цели.
4. **Динамический тон**: Звук `SoundEvents.PLAYER_ATTACK_STRONG` повышает тон до $1.4\times$.
5. **Настройка**: Можно отключить правилом `/gamerule ig:enable_combat_juice false`.

---

## 3. Математические формулы и уравнения

### Условие срабатывания
$$\text{JuiceEnabled} = \text{true} \quad \land \quad S_{\text{strength}} > 0.8$$

### Формула динамического изменения тона звука
$$\text{Pitch} = 1.0 + (S_{\text{strength}} - 0.8) \times 2.0$$

Анализ граничных значений:
* Порог срабатывания ($S = 0.8$): $\text{{Pitch}} = 1.00$
* Средний заряд ($S = 0.9$): $\text{{Pitch}} = 1.20$
* Полный заряд ($S = 1.0$): $\text{{Pitch}} = 1.40$

### Параметры генерации частиц
* **`ParticleTypes.CRIT`**: Count $N = 10$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.
* **`ParticleTypes.ENCHANTED_HIT`**: Count $N = 5$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.

---

## 4. Блок-схемы и конечные автоматы

```
                 [ Player Attacks Target Entity ]
                                |
                                v
                   PlayerMixin.cca$applyJuice
                                |
                                v
                   CCAHooks.applyCombatJuice
                                |
                Is ig:enable_combat_juice true?
                       /                 \
                     YES                  NO
                     /                     \
        Get attackStrengthScale(0.5f)     (Return silently)
                     |
            Is attackStrength > 0.8?
                   /         \
                 YES          NO
                 /             \
      [ Emit Visual & Audio ]  (Return silently)
         |
         +--> ServerLevel.sendParticles(CRIT, count=10)
         +--> ServerLevel.sendParticles(ENCHANTED_HIT, count=5)
         +--> Calculate Pitch = 1.0 + (attackStrength - 0.8) * 2.0
         +--> Level.playSound(PLAYER_ATTACK_STRONG, volume=1.0, pitch)
```

---

## 5. Схемы тегов предметов и SNBT

```json
{
  "sound": "minecraft:entity.player.attack.strong",
  "source": "PLAYERS",
  "volume": 1.0,
  "pitch_min": 1.0,
  "pitch_max": 1.4
}
```

---

## 6. Полная справочная таблица

| Charge Scale ($S$) | Charge Status | Particle Burst | Sound Event | Audio Pitch |
| :---: | :--- | :--- | :--- | :---: |
| **$0.00 - 0.80$** | Incomplete Charge | None | Default vanilla hit | Vanilla |
| **$0.81$** | Threshold Reached | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.02\times$ |
| **$0.85$** | Strong Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.10\times$ |
| **$0.90$** | High-Power Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.20\times$ |
| **$0.95$** | Near-Max Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.30\times$ |
| **$1.00$** | Full Max Strike | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.40\times$ |

---

## 7. Анализ внедрений Mixin

```java
public static void applyCombatJuice(Player player, Entity target) {
    if (!CombatRules.getBoolean(player.level(), CombatRules.ENABLE_JUICE)) return;

    float attackStrength = player.getAttackStrengthScale(0.5f);
    if (attackStrength > 0.8f) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY(0.5), target.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
            serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, target.getX(), target.getY(0.5), target.getZ(), 5, 0.1, 0.1, 0.1, 0.1);
        }
        
        float pitch = 1.0f + (attackStrength - 0.8f) * 2.0f;
        player.level().playSound(null, target.getX(), target.getY(), target.getZ(), 
            SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1.0f, pitch);
    }
}
```

---

## 🔗 Ссылки на связанные разделы
* [[Вернуться к порталу Minecraft 26.3|ru_ru-26.3-Home]]
* [[26.3 Механика перезарядки оружия|ru_ru-26.3-Weapon-Cooldown-Mechanics]]
* [[26.3 Конфигурация и игровые правила|ru_ru-26.3-Configuration-and-GameRules]]
* [[26.3 Архитектура и миксины|ru_ru-26.3-Architecture-and-Mixins]]
