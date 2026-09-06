# ⚔️ Kepuasan Instan: Penyesuai Cooldown Serangan (Combat Cooldown Adjuster) Wiki Bahasa Indonesia

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Pernyataan Penyangkalan Sumber Repositori**: Dokumentasi dalam Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup komit terbaru yang belum dirilis atau fitur dalam tahap pengembangan sebelum build rilis publik di CurseForge dan Modrinth.

Selamat datang di dokumentasi teknis resmi **Instant Gratification: Combat Cooldown Adjuster** untuk Minecraft Fabric! Mod ini dirancang untuk mengembalikan sensasi pertempuran jarak dekat yang cepat dan responsif dengan menghilangkan penundaan serangan kaku dari Minecraft 1.9+, memberikan kendali tick yang fleksibel serta umpan balik audiovisual yang memuaskan.

---

## 🧭 Portal Dokumentasi Berdasarkan Versi

Berdasarkan kebijakan **1 Jar 1 Version**, setiap versi Minecraft yang didukung memiliki pohon dokumentasi khusus:

| Versi Minecraft | SemVer Mod | Fabric Loader | Portal Dokumentasi |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 Masuk ke Portal Minecraft 26.2|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 Masuk ke Portal Minecraft 26.3|26.3-Home]] |

---

## ⚡ Fitur dan Mekanisme Utama

1. **Penyesuaian Tick Kategori Senjata (Categorical Tick Overrides)**:
   Mengganti sistem attack speed bawaan dengan durasi tick langsung per tag item (`#minecraft:swords`, `#minecraft:axes`, `#c:spears`, dll.). Pedang berkecepatan 4 tick (5 serangan/detik), Kapak 8 tick, dan Cangkul 1 tick (20 serangan/detik). Mengatur nilai ke `0` mengembalikan gaya spam-klik instan Minecraft 1.8!
2. **Kelincahan Ganti Slot (Swap Agility)**:
   Aturan `ig:prevent_item_swap_cooldown` mencegah pengukur serangan di-reset saat berganti slot hotbar, memungkinkan kombo senjata beruntun.
3. **Efek Serangan Kuat (Combat Juice)**:
   Serangan dengan daya isi $>80\%$ ($S > 0.8$) memicu hamburan partikel kritikal ganda dan menaikkan nada suara serangan secara dinamis dari $1.0\times$ hingga $1.4\times$.
4. **GameRules Dinamis Berbasis DasikLibrary**:
   Semua 9 pengaturan dapat diubah langsung di dalam game melalui perintah `/gamerule` tanpa perlu restart server.

---

## 📊 Tabel Referensi GameRules

| GameRule | Tipe | Standar | Deskripsi |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | Integer | `4` | Cooldown serangan pedang dalam tick (0.2 detik) |
| `ig:axe_cooldown_ticks` | Integer | `8` | Cooldown serangan kapak dalam tick (0.4 detik) |
| `ig:pickaxe_cooldown_ticks` | Integer | `4` | Cooldown serangan beliung |
| `ig:shovel_cooldown_ticks` | Integer | `2` | Cooldown serangan sekop |
| `ig:hoe_cooldown_ticks` | Integer | `1` | Cooldown serangan cangkul |
| `ig:spear_cooldown_ticks` | Integer | `6` | Cooldown serangan tombak (`#c:spears`) |
| `ig:generic_cooldown_ticks` | Integer | `4` | Cooldown untuk tangan kosong dan item lainnya |
| `ig:prevent_item_swap_cooldown` | Boolean | `true` | Jangan reset pengukur serangan saat berganti item |
| `ig:enable_combat_juice` | Boolean | `true` | Aktifkan partikel dan dinamika nada suara serangan |

---

## ⚖️ Lisensi & Kepemilikan
* **Pembuat**: Dasik (Rifaditya)
* **Lisensi**: GNU General Public License v3.0 (GPLv3)
* **Portal Utama**: [[Kembali ke Beranda Bahasa Inggris|Home]]
