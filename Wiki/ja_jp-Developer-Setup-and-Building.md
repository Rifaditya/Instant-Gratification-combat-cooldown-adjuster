# 🛠️ 開発環境セットアップ & 統合ビルドガイド

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

## 1. 公式技術情報
| パラメータ | 技術仕様 |
| :--- | :--- |
| **ビルドツールチェーン** | Gradle 9.3+ with Fabric Loom |
| **Java 実行プラットフォーム** | OpenJDK 25 (Hotspot 64-bit) |
| **MOD コレクション** | Multi-Version Anchor Subprojects (`26.2` & `26.3`) |
| **Bytecode Injector** | SpongePowered Mixin 0.8+ |
| **コア依存関係** | Fabric API, DasikLibrary (`DynamicGameRuleManager`) |
| **Compilation Flag** | `./gradlew build --no-daemon` |

---

## 2. ワークスペース前提条件と環境セットアップ

Building Combat Cooldown Adjuster from source requires an active JDK 25 installation.

```bash
# Verify Java version
java -version
# Expected output: openjdk version "25" ...
```

---

## 3. ステップバイステップビルド手順

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

## 4. サブプロジェクト構造と Loom 設定

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

## 5. Mixin アーキテクチャと純度基準
Combat Cooldown Adjuster は厳格に **Mixin 純度基準** を遵守しています:
1. **Mixin クラス内のロジックゼロ**: `PlayerMixin.java` には計算や文字列処理を一切含みません。
2. **静的ユーティリティへの委託**: 全ての数学計算、タグ判定、パーティクル/サウンド生成は `CCAHooks.java` にカプセル化されています。
3. **キャンセルの安全性**: `@At("HEAD")` で明示的に判定を行い、安全に処理を制御します。

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

## 6. アドオン開発者向けデータ駆動拡張フック
`#c:spears` を使用した槍の統合:
`data/c/tags/item/spears.json` にアイテム ID を追加するだけで自動的に槍のクールダウンルールが適用されます。

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

## 7. 関連ドキュメントリンク
* [[メイン Wiki ポータルに戻る|ja_jp-Home]]
* [[26.2 アーキテクチャ設計と Mixin 解析|ja_jp-26.2-Architecture-and-Mixins]]
* [[26.3 アーキテクチャ設計と Mixin 解析|ja_jp-26.3-Architecture-and-Mixins]]
* [[バージョン互換性マトリクス|ja_jp-Version-Compatibility]]
