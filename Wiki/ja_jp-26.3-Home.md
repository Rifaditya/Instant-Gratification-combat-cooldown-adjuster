# ⚔️ Minecraft 26.3 ドキュメントポータル

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

**Minecraft 26.3**（対象 `26.3-snapshot-6`）向けの **Instant Gratification: Combat Cooldown Adjuster** 技術ドキュメントポータルへようこそ！このバージョンツリー内の全ての記述は、当該リリースのマッピングと仕様に正確に準拠しています。

---

## 🧭 Minecraft 26.3 ナビゲーションマトリクス

| 機能 / サブシステム | 詳細説明 | 専用 Wiki ページ |
| :--- | :--- | :--- |
| **武器クールダウンメカニクス** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.3 武器クールダウンメカニクス|ja_jp-26.3-Weapon-Cooldown-Mechanics]] |
| **戦闘ヒット演出とオーディオフィードバック** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.3 戦闘ヒット演出とオーディオフィードバック|ja_jp-26.3-Combat-Juice-and-Audio-Feedback]] |
| **設定とゲームルール (GameRules) マトリクス** | Complete reference matrix of all 9 namespaced GameRules | [[26.3 設定とゲームルール (GameRules) マトリクス|ja_jp-26.3-Configuration-and-GameRules]] |
| **アーキテクチャ設計と Mixin 解析** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.3 アーキテクチャ設計と Mixin 解析|ja_jp-26.3-Architecture-and-Mixins]] |
| **開発環境セットアップとツールチェーン** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.3 開発環境セットアップとツールチェーン|ja_jp-26.3-Developer-Setup-and-Building]] |

---

## 📊 公式技術情報

| パラメータ | 技術仕様 |
| :--- | :--- |
| **Minecraft Release Target** | `26.3-snapshot-6` |
| **Mod SemVer Release** | `1.0.1+26.3` |
| **Fabric Loader Requirement** | `0.19.3` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.8.36` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ コアサブシステムのハイライト

1. **サブ Tick 武器クールダウン (Sub-Tick Weapon Timing)**:
   整数の Tick 値で攻撃遅延をオーバーライド。剣は 4 Tick (5.0 回/秒)、斧は 8 Tick (2.5 回/秒)、クワは 1 Tick (20.0 回/秒)。$T = 0$ で 1.8 スタイルの瞬間連打が解放されます。

2. **ホットバースワップ敏捷性 (Hotbar Swap Agility)**:
   `ig:prevent_item_swap_cooldown` を有効にすると、スロット切り替え時のチャージリセットが撤廃され、流れるような武器コンボが可能になります。

3. **多感覚コンバットジュース (Multi-Sensory Combat Juice)**:
   80% 以上のチャージ攻撃で二重パーティクル（会心 & 魔法会心）および最大 $1.4\times$ の動的ピッチシフト音が発生します。

---

## 🔗 グローバルハブ
* [[メイン Wiki ポータルに戻る|ja_jp-Home]]
* [[Minecraft 26.2 ドキュメントポータル|ja_jp-26.2-Home]]
* [[バージョン互換性マトリクス|ja_jp-Version-Compatibility]]
* [[トラブルシューティング & FAQ|ja_jp-Troubleshooting-and-FAQ]]
