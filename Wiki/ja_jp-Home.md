# ⚔️ 即時満足：コンバット・クールダウン・アジャスター (Combat Cooldown Adjuster) 日本語Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **リポジトリソースに関する免責事項**：このWikiのドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForgeおよびModrinthでの公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

Minecraft Fabric用 **Combat Cooldown Adjuster** の公式技術Wikiへようこそ！本MODは、Minecraft 1.9以降の戦闘更新によって生じた「攻撃待ち時間」を解消し、カスタマイズ可能なティック遅延と爽快な打撃フィードバックによって高速かつ直感的な戦闘体験を取り戻します。

---

## 🧭 バージョン別ポータル

当MODの **1 Jar 1 Version（1バージョン1JAR）** 方針に基づき、バージョンごとに独立したドキュメントを用意しています：

| 対象バージョン | MOD SemVer | Fabric Loader | ドキュメントポータル |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 Minecraft 26.2 ポータル|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 Minecraft 26.3 ポータル|26.3-Home]] |

---

## ⚡ 主な機能

1. **カテゴリー別ティック上書き (Categorical Tick Overrides)**：
   アイテムタグごとに攻撃遅延ティック数を設定（20ティック＝1秒）。剣は4ティック（毎秒5回）、斧は8ティック、クワは1ティック（毎秒20回攻撃）。数値を `0` に設定すると、Minecraft 1.8時代の連打攻撃が完全復活します！
2. **ホットバー切り替え敏捷性 (Swap Agility)**：
   `ig:prevent_item_swap_cooldown` を有効にすると、ホットバーのアイテムを切り替えても攻撃ゲージがリセットされず、流れるような武器コンボが可能です。
3. **戦闘打撃エフェクト (Combat Juice)**：
   攻撃チャージが80%を超える（$S > 0.8$）ヒット時に、クリティカル粒子が噴出し、攻撃サウンドのピッチが $1.0\times$ から $1.4\times$ へとダイナミックに上昇します。
4. **DasikLibraryによる動的GameRules**：
   全9種類の項目は `/gamerule` コマンドでリアルタイムに変更可能で、サーバーの再起動は不要です。

---

## 📊 GameRules一覧表

| GameRule名 | 型 | デフォルト | 説明 |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | 整数 | `4` | 剣の攻撃遅延ティック数（0.2秒） |
| `ig:axe_cooldown_ticks` | 整数 | `8` | 斧の攻撃遅延ティック数（0.4秒） |
| `ig:pickaxe_cooldown_ticks` | 整数 | `4` | ツルハシの攻撃遅延ティック数 |
| `ig:shovel_cooldown_ticks` | 整数 | `2` | シャベルの攻撃遅延ティック数 |
| `ig:hoe_cooldown_ticks` | 整数 | `1` | クワの攻撃遅延ティック数 |
| `ig:spear_cooldown_ticks` | 整数 | `6` | 槍の攻撃遅延ティック数 (`#c:spears`) |
| `ig:generic_cooldown_ticks` | 整数 | `4` | 素手や未分類アイテムの攻撃遅延 |
| `ig:prevent_item_swap_cooldown` | 真偽値 | `true` | ホットバー切り替え時にゲージをリセットしない |
| `ig:enable_combat_juice` | 真偽値 | `true` | 強打撃時の粒子および動的サウンド効果を有効化 |

---

## ⚖️ ライセンス & 著作権
* **製作者**: Dasik (Rifaditya)
* **ライセンス**: GNU General Public License v3.0 (GPLv3)
* **メインポータル**: [[英語ポータルへ戻る|Home]]
