# ⚔️ Portal Dokumentasi Minecraft 26.2

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Penafian Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup commit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

Selamat datang di portal dokumentasi teknis resmi untuk **Instant Gratification: Combat Cooldown Adjuster** di **Minecraft 26.2** (menargetkan `26.1.2`). Semua dokumentasi mencerminkan spesifikasi bytecode dan dependensi Loom untuk rilis ini.

---

## 🧭 Matriks Navigasi untuk Minecraft 26.2

| Fitur / Subsistem | Deskripsi Lengkap | Halaman Wiki Khusus |
| :--- | :--- | :--- |
| **Mekanika Cooldown Senjata** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.2 Mekanika Cooldown Senjata|id_id-26.2-Weapon-Cooldown-Mechanics]] |
| **Umpan Balik Tempur & Efek Audio Visual** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.2 Umpan Balik Tempur & Efek Audio Visual|id_id-26.2-Combat-Juice-and-Audio-Feedback]] |
| **Konfigurasi & Matriks GameRules** | Complete reference matrix of all 9 namespaced GameRules | [[26.2 Konfigurasi & Matriks GameRules|id_id-26.2-Configuration-and-GameRules]] |
| **Arsitektur & Analisis Mixin** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.2 Arsitektur & Analisis Mixin|id_id-26.2-Architecture-and-Mixins]] |
| **Pengaturan Pengembang & Toolchain** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.2 Pengaturan Pengembang & Toolchain|id_id-26.2-Developer-Setup-and-Building]] |

---

## 📊 Informasi Teknis Resmi

| Parameter | Spesifikasi Teknis |
| :--- | :--- |
| **Minecraft Release Target** | `26.1.2` |
| **Mod SemVer Release** | `1.0.1+26.2` |
| **Fabric Loader Requirement** | `0.19.1` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.6.9+build.24` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ Sorotan Subsistem Utama

1. **Pengaturan Waktu Senjata Sub-Tick (Sub-Tick Weapon Timing)**:
   Menggantikan jeda serangan vanilla dengan penimpaan tick integer langsung ($T_{\text{{delay}}}$). Pedang default ke 4 tick (5.0 serangan/detik), Kapak default ke 8 tick (2.5 serangan/detik), dan Cangkul default ke 1 tick (20.0 serangan/detik). Mengatur $T = 0$ membuka spam-klik instan ala 1.8.

2. **Kelincahan Ganti Hotbar (Hotbar Swap Agility)**:
   Mengaktifkan `ig:prevent_item_swap_cooldown` menghilangkan reset pengukur serangan buatan saat mengganti item di hotbar, memungkinkan kombo senjata cepat.

3. **Combat Juice Multi-Sensorik (Multi-Sensory Combat Juice)**:
   Serangan dengan daya >80% memicu ledakan partikel ganda (`CRIT` dan `ENCHANTED_HIT`) dan audio yang dinaikkan nadanya (naik dari $1.0\times$ hingga $1.4\times$).

---

## 🔗 Hub Global & Eksternal
* [[Kembali ke Portal Utama Wiki|id_id-Home]]
* [[Portal Dokumentasi Minecraft 26.3|id_id-26.3-Home]]
* [[Matriks Kompatibilitas Versi|id_id-Version-Compatibility]]
* [[Panduan Pemecahan Masalah & FAQ|id_id-Troubleshooting-and-FAQ]]
