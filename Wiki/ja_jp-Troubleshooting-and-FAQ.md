# 🔧 トラブルシューティング & FAQ

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

## 1. 公式技術情報
| パラメータ | 診断詳細 |
| :--- | :--- |
| **対象サブシステム** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **制御ゲームルール** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **主要注入ポイント** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **コア依存関係** | `dasik-library` (Dynamic GameRule Manager) |
| **ログ名前空間** | `combat-cooldown-adjuster` |

---

## 2. 診断フローチャート

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

## 3. 段階的トラブルシューティング手順

### フェーズ 1: 武器 Tick 遅延の校正
1. ゲーム内チャットまたはサーバーコンソールを開きます。
2. 現在の武器タイプの有効 Tick 値を照会します:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. 値がデフォルトの `4` の場合、攻撃頻度は以下のようになります:
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ 回攻撃/秒}}
4. Minecraft 1.8 スタイルの連打（Click-Spam）をテストするには、以下を実行します:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. クールダウン遅延なしで即座に攻撃判定が行われます。

### フェーズ 2: ホットバースワップ敏捷性の診断
1. スロット 1 に剣、スロット 2 に斧を装備します。
2. 剣を振って攻撃をトリガーします。
3. 直ちにキー `2` を押して斧に切り替えます。
4. `ig:prevent_item_swap_cooldown` が `true` の場合、攻撃チャージメーターはゼロにリセットされず、シームレスにチャージを継続します。
5. リセットされる場合は以下を実行します:
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### フェーズ 3: パーティクルとオーディオの最適化
1. コンバットジュース (Combat Juice) は攻撃チャージが 80% ($S > 0.8$) を超えた際にトリガーされます。
2. 低スペック環境でパーティクルによるスタッターが発生する場合は、サーバー側で無効化できます:
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. これにより `CCAHooks.applyCombatJuice` のパーティクル送信とサウンド再生がスキップされ、処理負荷を軽減します。

---

## 4. 数学的校正リファレンスマトリクス

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

## 5. よくある質問 (FAQ)

### Q1: 遅延 Tick を 0 に設定するとバニラのダメージ計算が壊れますか？
**壊れません。** バニラは $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$ でチャージ率を算出します。$T = 0$ の時、Mixin が $0.0$ を返すため即座に $S(t) = 1.0$ と判定され、常に 100% のフルダメージが適用されます。

### Q2: タグのないカスタム MOD 武器が 4 Tick で攻撃するのはなぜですか？
サードパーティ製アイテムが原版ツールタグや `#c:spears` を実装していない場合、`CCAHooks.getCooldownTicks` は `CombatRules.GENERIC_TICKS` (デフォルト: 4) にフォールバックします。以下で設定可能です:
```mcfunction
/gamerule ig:generic_cooldown_ticks <値>
```

### Q3: マルチプレイで遅延や同期ズレは発生しますか？
本 MOD は完全にサーバーオーソリタティブに動作します。専用サーバー接続時、`PlayerMixin` はサーバー側プレイヤー実体に注入され、判定は完全にサーバーで行われます。シングルや LAN でも遅延なく快適に動作します。

### Q4: ゲームルールはワールドセーブに保存されますか？
**保存されます。** 全 9 種のゲームルールは DasikLibrary 経由でワールドの `level.dat` に自動的にシリアライズされ、再起動後も保持されます。

---

## 6. 関連ドキュメントリンク
* [[メイン Wiki ポータルに戻る|ja_jp-Home]]
* [[26.2 設定とゲームルール (GameRules) マトリクス|ja_jp-26.2-Configuration-and-GameRules]]
* [[26.3 設定とゲームルール (GameRules) マトリクス|ja_jp-26.3-Configuration-and-GameRules]]
* [[開発環境セットアップ & 統合ビルドガイド|ja_jp-Developer-Setup-and-Building]]
