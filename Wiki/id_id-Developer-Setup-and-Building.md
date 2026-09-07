# 🛠️ Pengaturan Pengembang & Panduan Build Terpadu

> 📌 **Penafian Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup commit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

## 1. Informasi Teknis Resmi
| Parameter | Spesifikasi Teknis |
| :--- | :--- |
| **Toolchain Build** | Gradle 9.3+ with Fabric Loom |
| **Platform Runtime Java** | OpenJDK 25 (Hotspot 64-bit) |
| **Koleksi Mod** | Multi-Version Anchor Subprojects (`26.2` & `26.3`) |
| **Bytecode Injector** | SpongePowered Mixin 0.8+ |
| **Dependensi Utama** | Fabric API, DasikLibrary (`DynamicGameRuleManager`) |
| **Compilation Flag** | `./gradlew build --no-daemon` |

---

## 2. Prasyarat Workspace & Pengaturan Lingkungan

Building Combat Cooldown Adjuster from source requires an active JDK 25 installation.

```bash
# Verify Java version
java -version
# Expected output: openjdk version "25" ...
```

---

## 3. Alur Kerja Build Langkah demi Langkah

```
[ Clone Repository ]
         |
         v
[ Navigate to Target Subproject Directory ]
  - Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster/
  - Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster/
         |
         v
[ Verify gradle.properties Configuration ]
         |
         v
[ Execute Unified Build Command ]
  ./gradlew build --no-daemon
         |
         v
[ Inspect Output Artifacts in build/libs/ ]
```

```bash
# For Linux / macOS:
./gradlew build --no-daemon

# For Windows PowerShell:
.\gradlew build --no-daemon
```

Artifacts are generated in `build/libs/`:
* Standard binary: `combat-cooldown-adjuster-1.0.1+<mc_version>.jar`
* Sources jar: `combat-cooldown-adjuster-1.0.1+<mc_version>-sources.jar`

---

## 4. Arsitektur Subproyek & Pengaturan Loom

```properties
org.gradle.parallel=false
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true
```

### Dependency Matrix across Anchors:
* **Version 26.2 (`gradle.properties`)**:
  ```properties
  minecraft_version=26.1.2
  fabric_version=0.145.4+26.1.2
  fabric_loader_version=0.19.1
  dasik_library_version=1.6.9+build.24
  ```
* **Version 26.3 (`gradle.properties`)**:
  ```properties
  minecraft_version=26.3-snapshot-6
  fabric_version=0.156.1+26.3
  fabric_loader_version=0.19.3
  dasik_library_version=1.8.36
  ```

---

## 5. Arsitektur Mixin & Standar Kemurnian
Combat Cooldown Adjuster mematuhi **Standar Kemurnian Mixin** secara ketat:
1. **Nol Logika dalam Kelas Mixin**: `PlayerMixin.java` tidak berisi perhitungan bisnis atau manipulasi string.
2. **Delegasi Utilitas Statis**: Semua perhitungan matematika dan pemanggilan efek dirangkum dalam `CCAHooks.java`.
3. **Keamanan Pembatalan**: Injeksi menggunakan checkpoint `@At("HEAD")` eksplisit dengan `cancellable = true` saat menimpa nilai kembali.

### Injected Target Signatures in `PlayerMixin.java`:
```java
// 1. Override weapon cooldown delay
@Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("HEAD"), cancellable = true)
private void cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir) {
    Player player = (Player) (Object) this;
    ItemStack stack = this.getMainHandItem();
    int ticks = CCAHooks.getCooldownTicks(player, stack);
    if (ticks >= 0) {
        cir.setReturnValue((float) ticks);
    }
}

// 2. Prevent attack meter reset on hotbar item switch
@Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"), cancellable = true)
private void cca$preventSwapReset(CallbackInfo ci) {
    Player player = (Player) (Object) this;
    if (CombatRules.getBoolean(player.level(), CombatRules.PREVENT_SWAP_RESET)) {
        ci.cancel();
    }
}

// 3. Inject Combat Juice particle and sound feedback
@Inject(method = "attack", at = @At("HEAD"))
private void cca$applyJuice(Entity target, CallbackInfo ci) {
    Player player = (Player) (Object) this;
    CCAHooks.applyCombatJuice(player, target);
}
```

---

## 6. Hook Ekstensi Pengembang Addon & Data-Driven
Integrasi Tombak melalui `#c:spears`:
Tambahkan item tombak mod Anda ke `data/c/tags/item/spears.json` untuk menerapkan aturan cooldown tombak secara otomatis.

```json
{
  "replace": false,
  "values": [
    "examplemod:iron_spear",
    "examplemod:diamond_spear"
  ]
}
```

---

## 7. Tautan Dokumentasi Terkait
* [[Kembali ke Portal Utama Wiki|id_id-Home]]
* [[26.2 Arsitektur & Analisis Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[26.3 Arsitektur & Analisis Mixin|id_id-26.3-Architecture-and-Mixins]]
* [[Matriks Kompatibilitas Versi|id_id-Version-Compatibility]]
