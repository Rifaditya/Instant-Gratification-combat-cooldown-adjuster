# 📊 Matriks Kompatibilitas Versi

> 📌 **Penafian Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup commit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

## 1. Informasi Teknis Resmi
| Parameter | Spesifikasi Teknis |
| :--- | :--- |
| **Pengenal Mod** | `combat-cooldown-adjuster` |
| **Koleksi Mod** | Instant Gratification (IG) |
| **Versi Jangkar Fabric yang Didukung** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Platform Runtime Java** | OpenJDK 25 (Hotspot 64-bit) |
| **Toolchain Build** | Gradle 9.3+ with Fabric Loom |
| **Standar Arsitektur** | Kebijakan 1 Jar 1 Versi (1 Jar 1 Version Policy) |
| **Status API Publik** | Mod Mandiri (Menggunakan API DasikLibrary) |

---

## 2. Matriks Kompatibilitas Versi
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 Jangkar Standar |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 Cabang Mutakhir |

---

## 3. Kebijakan 1 Jar 1 Versi vs Batasan Universal Library

### Biner Mod Khusus
Berbeda dengan pustaka bersama yang mengekspor API komputasi murni di berbagai versi, mod yang menyuntikkan bytecode langsung ke `net.minecraft.world.entity.player.Player` memerlukan verifikasi waktu kompilasi yang ketat terhadap pemetaan Mojang yang tepat.
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### Integrasi Universal DasikLibrary
Combat Cooldown Adjuster mengandalkan **DasikLibrary** untuk pendaftaran GameRule dinamis (`DynamicGameRuleManager`). DasikLibrary mengikuti arsitektur batas versi terbuka (`>=26.1.2-`), menjamin:
1. Kompatibilitas mundur dan maju yang mulus dengan serialisasi GameRule server.
2. Pelengkapan otomatis tab dinamis `/gamerule` di semua instance game.
3. Keamanan classloader sisi klien (evaluasi sisi server mencegah crash pada klien).

---

## 4. Alur Kerja Instalasi & Prasyarat

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

### Daftar Periksa Verifikasi:
1. Pastikan runtime Java adalah OpenJDK 25 Hotspot (misalnya Eclipse Adoptium `jdk-25.0.3+`).
2. Pastikan `fabric-api` ada di folder mods Anda.
3. Pastikan `dasik-library` ada di folder mods Anda.
4. Jalankan game dan periksa log untuk:
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. Tautan Dokumentasi Terkait
* [[Kembali ke Portal Utama Wiki|id_id-Home]]
* [[26.2 Mekanika Cooldown Senjata|id_id-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 Mekanika Cooldown Senjata|id_id-26.3-Weapon-Cooldown-Mechanics]]
* [[Panduan Pemecahan Masalah & FAQ|id_id-Troubleshooting-and-FAQ]]
