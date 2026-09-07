# 📊 バージョン互換性マトリクス

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

## 1. 公式技術情報
| パラメータ | 技術仕様 |
| :--- | :--- |
| **MOD 識別子** | `combat-cooldown-adjuster` |
| **MOD コレクション** | Instant Gratification (IG) |
| **サポート対象 Fabric アンカー** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Java 実行プラットフォーム** | OpenJDK 25 (Hotspot 64-bit) |
| **ビルドツールチェーン** | Gradle 9.3+ with Fabric Loom |
| **アーキテクチャ標準** | 1 Jar 1 Version (1 バージョン 1 Jar ポリシー) |
| **パブリック API ステータス** | スタンドアロン MOD (DasikLibrary API を利用) |

---

## 2. バージョン互換性マトリクス
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 標準アンカー |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 最新スナップショットブランチ |

---

## 3. 1 Jar 1 Version ポリシーとユニバーサルライブラリ境界

### 専用 MOD バイナリ
広範なバージョン境界で純粋な計算 API を公開する共有ライブラリとは異なり、`net.minecraft.world.entity.player.Player` にバイトコードを直接注入する MOD は、対象バージョンの難読化マッピングおよび記述子と厳密にコンパイル時に検証される必要があります。
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### ユニバーサル DasikLibrary 統合
Combat Cooldown Adjuster は、実行時の動的 GameRule 登録 (`DynamicGameRuleManager`) に **DasikLibrary** を利用します。DasikLibrary はオープンバージョン境界 (`>=26.1.2-`) を採用しており、以下を保証します:
1. サーバーの GameRule シリアライズとの完全な前方・後方互換性。
2. ゲーム内の `/gamerule` 動的 Tab 補完の完全サポート。
3. クライアント側のクラスローダー安全性 (サーバー専用評価によりクライアントクラッシュを完全に防止)。

---

## 4. インストールと前提条件ワークフロー

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

### インストール確認チェックリスト:
1. Java ランタイムが OpenJDK 25 Hotspot (例: Eclipse Adoptium `jdk-25.0.3+`) であることを確認。
2. `mods` フォルダに `fabric-api` が存在することを確認。
3. `mods` フォルダに `dasik-library` が存在することを確認。
4. ゲームを起動し、ログに以下が出力されることを確認:
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. 関連ドキュメントリンク
* [[メイン Wiki ポータルに戻る|ja_jp-Home]]
* [[26.2 武器クールダウンメカニクス|ja_jp-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 武器クールダウンメカニクス|ja_jp-26.3-Weapon-Cooldown-Mechanics]]
* [[トラブルシューティング & FAQ|ja_jp-Troubleshooting-and-FAQ]]
