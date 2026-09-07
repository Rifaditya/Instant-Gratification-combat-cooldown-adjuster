# 🛠️ 開発環境セットアップとツールチェーン (Minecraft 26.3)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

## 1. 公式技術情報
| パラメータ | 技術仕様 |
| :--- | :--- |
| **Minecraft Target** | `26.3-snapshot-6` |
| **Target Subproject** | `Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster/` |
| **Mod SemVer** | `1.0.1+26.3` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Build Automation** | Gradle 9.3+ with Fabric Loom |
| **Fabric Loader** | `0.19.3` |
| **Fabric API** | `0.156.1+26.3` |
| **DasikLibrary** | `1.8.36` |

---

## 2. ワークスペース前提条件と環境セットアップ

1. **Install OpenJDK 25**: Download and install Eclipse Adoptium Temurin OpenJDK 25.
2. **Verify Installation**:
   ```bash
   java -version
   ```

---

## 3. ステップバイステップビルド手順

```bash
# Navigate to subproject directory
cd "Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster"

# Run non-daemon build
./gradlew build --no-daemon
```

### Output Binaries:
* `combat-cooldown-adjuster-1.0.1+26.3.jar`
* `combat-cooldown-adjuster-1.0.1+26.3-sources.jar`

---

## 4. サブプロジェクト構造と Loom 設定

```properties
minecraft_version=26.3-snapshot-6
fabric_version=0.156.1+26.3
fabric_loader_version=0.19.3
dasik_library_version=1.8.36
```

---

## 🔗 関連ドキュメントリンク
* [[Minecraft 26.3 ポータルに戻る|ja_jp-26.3-Home]]
* [[26.3 アーキテクチャ設計と Mixin 解析|ja_jp-26.3-Architecture-and-Mixins]]
* [[開発環境セットアップ & 統合ビルドガイド|ja_jp-Developer-Setup-and-Building]]
