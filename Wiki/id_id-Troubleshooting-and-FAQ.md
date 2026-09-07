# 🔧 Panduan Pemecahan Masalah & FAQ

> 📌 **Penafian Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup commit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

## 1. Informasi Teknis Resmi
| Parameter | Detail Diagnostik |
| :--- | :--- |
| **Subsistem Target** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **GameRule Pengendali** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **Titik Injeksi Utama** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **Dependensi Utama** | `dasik-library` (Dynamic GameRule Manager) |
| **Namespace Log** | `combat-cooldown-adjuster` |

---

## 2. Diagram Alur Diagnostik

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

## 3. Panduan Diagnostik Langkah demi Langkah

### Fase 1: Kalibrasi Tick Senjata
1. Buka obrolan dalam game atau konsol server.
2. Periksa nilai tick aktif untuk jenis senjata Anda:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. Jika nilai kembali default `4`, frekuensi serangan adalah:
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ serangan/detik}}
4. Untuk menguji spam klik instan (gaya Minecraft 1.8), jalankan:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. Serangan akan terdaftar secara instan tanpa jeda cooldown serangan.

### Fase 2: Diagnosa Kelincahan Ganti Senjata (Swap Agility)
1. Pasang Pedang di slot 1 dan Kapak di slot 2.
2. Ayunkan Pedang untuk memicu cooldown.
3. Segera tekan `2` untuk beralih ke Kapak.
4. Jika `ig:prevent_item_swap_cooldown` bernilai `true`, pengukur daya serangan Anda **tidak** akan direset ke nol; ia akan terus terisi dengan mulus.
5. Jika meteran mereset, jalankan:
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### Fase 3: Optimasi Partikel & Audio
1. Combat Juice aktif saat skala serangan lebih besar dari $0.8$ (80% daya penuh).
2. Jika perangkat keras klien mengalami stutter saat ledakan partikel, nonaktifkan Combat Juice di server:
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. Ini sepenuhnya menonaktifkan panggilan ke `sendParticles` dan `playSound` di `CCAHooks.applyCombatJuice`, menghemat thread rendering dan bandwidth jaringan.

---

## 4. Tabel Referensi Kalibrasi Matematika

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

## 5. Pertanyaan yang Sering Diajukan (FAQ)

### Q1: Apakah mengatur tick delay ke 0 merusak penskalaan damage vanilla?
**Tidak.** Vanilla menghitung skala kekuatan serangan sebagai $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$. Ketika $T = 0$, Mixin kami mengembalikan penundaan $0.0$, menyebabkan mesin game vanilla segera mengevaluasi $S(t) = 1.0$. Setiap serangan otomatis memberikan 100% damage dasar penuh.

### Q2: Mengapa senjata mod khusus yang tidak diberi tag menyerang pada 4 tick?
Jika item mod pihak ketiga tidak menerapkan tag alat vanilla atau `#c:spears`, `CCAHooks.getCooldownTicks` akan kembali ke `CombatRules.GENERIC_TICKS` (default: 4 tick). Anda dapat menyesuaikannya melalui:
```mcfunction
/gamerule ig:generic_cooldown_ticks <nilai>
```

### Q3: Apakah mod ini menyebabkan lag multiplayer atau desync klien?
Combat Cooldown Adjuster berjalan secara otoritatif di sisi server. Saat pemain terhubung ke server khusus, `PlayerMixin` menyuntikkan ke entitas pemain sisi server, dan kalkulasi damage tetap otoritatif. Dalam singleplayer dan LAN, kedua sisi berjalan secara lokal dengan latensi jaringan nol.

### Q4: Apakah GameRules disimpan dalam penyimpanan world?
**Ya.** Semua 9 GameRules diserialisasi langsung ke file `level.dat` dunia melalui serialisasi GameRule vanilla yang dikelola oleh DasikLibrary.

---

## 6. Tautan Dokumentasi Terkait
* [[Kembali ke Portal Utama Wiki|id_id-Home]]
* [[26.2 Konfigurasi & Matriks GameRules|id_id-26.2-Configuration-and-GameRules]]
* [[26.3 Konfigurasi & Matriks GameRules|id_id-26.3-Configuration-and-GameRules]]
* [[Pengaturan Pengembang & Panduan Build Terpadu|id_id-Developer-Setup-and-Building]]
