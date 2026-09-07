# 💥 Umpan Balik Tempur & Efek Audio Visual (Minecraft 26.3)

> 📌 **Penafian Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup commit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

## 1. Informasi Teknis Resmi
| Parameter | Spesifikasi Teknis |
| :--- | :--- |
| **Subsystem Name** | Sistem Umpan Balik Sensorik Tempur Dinamis |
| **Controlling GameRule** | `ig:enable_combat_juice` (Default: `true`) |
| **Trigger Threshold** | Attack Strength Scale $S > 0.8$ ($80\%$) |
| **Particle Types** | `ParticleTypes.CRIT`, `ParticleTypes.ENCHANTED_HIT` |
| **Sound Event** | `SoundEvents.PLAYER_ATTACK_STRONG` |
| **Sound Category** | `SoundSource.PLAYERS` |
| **Audio Pitch Range** | $1.0\times$ to $1.4\times$ (Dynamic Charge Shift) |
| **Bytecode Hook** | `PlayerMixin.cca$applyJuice` $\to$ `CCAHooks.applyCombatJuice` |

---

## 2. Alur Kerja Bertahan Hidup Pemain
1. **Mengisi Serangan**: Biarkan pengukur terisi melebihi 80% ($S > 0.8$), hanya butuh 3.2 tick (0.16 detik).
2. **Mengeksekusi Serangan**: Klik kiri entitas untuk menyerang.
3. **Percikan Visual**: Server memancarkan 10 partikel Crit dan 5 partikel Enchanted Hit di tengah hitbox musuh.
4. **Kenaikan Nada Audio**: Memainkan `SoundEvents.PLAYER_ATTACK_STRONG` dengan nada hingga $1.4\times$.
5. **Pengaturan**: Dapat dinonaktifkan kapan saja melalui `/gamerule ig:enable_combat_juice false`.

---

## 3. Formula Matematika & Persamaan

### Kondisi Ambang Pemicu
$$\text{JuiceEnabled} = \text{true} \quad \land \quad S_{\text{strength}} > 0.8$$

### Formula Pergeseran Nada Audio Dinamis
$$\text{Pitch} = 1.0 + (S_{\text{strength}} - 0.8) \times 2.0$$

Analisis Nilai Batas:
* Pada ambang batas bawah ($S = 0.8$): $\text{{Pitch}} = 1.00$
* Pada daya tengah ($S = 0.9$): $\text{{Pitch}} = 1.20$
* Pada daya maksimal ($S = 1.0$): $\text{{Pitch}} = 1.40$

### Jumlah Emisi Partikel & Offset Spasial
* **`ParticleTypes.CRIT`**: Count $N = 10$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.
* **`ParticleTypes.ENCHANTED_HIT`**: Count $N = 5$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.

---

## 4. Diagram ASCII & Mesin Status

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

## 5. Skema SNBT & Tag Item

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

## 6. Matriks Referensi Menyeluruh

| Charge Scale ($S$) | Charge Status | Particle Burst | Sound Event | Audio Pitch |
| :---: | :--- | :--- | :--- | :---: |
| **$0.00 - 0.80$** | Incomplete Charge | None | Default vanilla hit | Vanilla |
| **$0.81$** | Threshold Reached | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.02\times$ |
| **$0.85$** | Strong Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.10\times$ |
| **$0.90$** | High-Power Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.20\times$ |
| **$0.95$** | Near-Max Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.30\times$ |
| **$1.00$** | Full Max Strike | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.40\times$ |

---

## 7. Hook Pengembang & Mixin

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

## 🔗 Tautan Dokumentasi Terkait
* [[Kembali ke Portal Minecraft 26.3|id_id-26.3-Home]]
* [[26.3 Mekanika Cooldown Senjata|id_id-26.3-Weapon-Cooldown-Mechanics]]
* [[26.3 Konfigurasi & Matriks GameRules|id_id-26.3-Configuration-and-GameRules]]
* [[26.3 Arsitektur & Analisis Mixin|id_id-26.3-Architecture-and-Mixins]]
