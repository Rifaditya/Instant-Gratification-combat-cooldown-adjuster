# 💥 戦闘ヒット演出とオーディオフィードバック (Minecraft 26.2)

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

## 1. 公式技術情報
| パラメータ | 技術仕様 |
| :--- | :--- |
| **Subsystem Name** | 動的戦闘演出システム |
| **Controlling GameRule** | `ig:enable_combat_juice` (Default: `true`) |
| **Trigger Threshold** | Attack Strength Scale $S > 0.8$ ($80\%$) |
| **Particle Types** | `ParticleTypes.CRIT`, `ParticleTypes.ENCHANTED_HIT` |
| **Sound Event** | `SoundEvents.PLAYER_ATTACK_STRONG` |
| **Sound Category** | `SoundSource.PLAYERS` |
| **Audio Pitch Range** | $1.0\times$ to $1.4\times$ (Dynamic Charge Shift) |
| **Bytecode Hook** | `PlayerMixin.cca$applyJuice` $\to$ `CCAHooks.applyCombatJuice` |

---

## 2. サバイバル操作ワークフロー
1. **チャージ**: 80% ($S > 0.8$) 以上蓄力 (わずか 3.2 Tick)。
2. **攻撃**: 左クリックで攻撃を実行。
3. **パーティクル**: 標的中心に 10 個の会心粒子と 5 個の魔法粒子が発生。
4. **ピッチシフト**: `SoundEvents.PLAYER_ATTACK_STRONG` が最大 $1.4\times$ の高音で再生。
5. **設定**: `/gamerule ig:enable_combat_juice false` でいつでも無効化可能。

---

## 3. 数学公式と計算式

### 発動条件式
$$\text{JuiceEnabled} = \text{true} \quad \land \quad S_{\text{strength}} > 0.8$$

### 動的ピッチ計算式
$$\text{Pitch} = 1.0 + (S_{\text{strength}} - 0.8) \times 2.0$$

境界値解析:
* 下限 ($S = 0.8$): $\text{{Pitch}} = 1.00$
* 中間 ($S = 0.9$): $\text{{Pitch}} = 1.20$
* 最大 ($S = 1.0$): $\text{{Pitch}} = 1.40$

### パーティクル発生仕様
* **`ParticleTypes.CRIT`**: Count $N = 10$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.
* **`ParticleTypes.ENCHANTED_HIT`**: Count $N = 5$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.

---

## 4. ASCII 状態遷移図

```
                 [ Player Attacks Target Entity ]
                                |
                                v
                   PlayerMixin.cca$applyJuice
                                |
                                v
                   CCAHooks.applyCombatJuice
                                |
                Is ig:enable_combat_juice true?
                       /                 \
                     YES                  NO
                     /                     \
        Get attackStrengthScale(0.5f)     (Return silently)
                     |
            Is attackStrength > 0.8?
                   /         \
                 YES          NO
                 /             \
      [ Emit Visual & Audio ]  (Return silently)
         |
         +--> ServerLevel.sendParticles(CRIT, count=10)
         +--> ServerLevel.sendParticles(ENCHANTED_HIT, count=5)
         +--> Calculate Pitch = 1.0 + (attackStrength - 0.8) * 2.0
         +--> Level.playSound(PLAYER_ATTACK_STRONG, volume=1.0, pitch)
```

---

## 5. SNBT とアイテムタグ仕様

```json
{
  "sound": "minecraft:entity.player.attack.strong",
  "source": "PLAYERS",
  "volume": 1.0,
  "pitch_min": 1.0,
  "pitch_max": 1.4
}
```

---

## 6. 総合リファレンスマトリクス

| Charge Scale ($S$) | Charge Status | Particle Burst | Sound Event | Audio Pitch |
| :---: | :--- | :--- | :--- | :---: |
| **$0.00 - 0.80$** | Incomplete Charge | None | Default vanilla hit | Vanilla |
| **$0.81$** | Threshold Reached | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.02\times$ |
| **$0.85$** | Strong Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.10\times$ |
| **$0.90$** | High-Power Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.20\times$ |
| **$0.95$** | Near-Max Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.30\times$ |
| **$1.00$** | Full Max Strike | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.40\times$ |

---

## 7. 開発者向け Mixin フック

```java
public static void applyCombatJuice(Player player, Entity target) {
    if (!CombatRules.getBoolean(player.level(), CombatRules.ENABLE_JUICE)) return;

    float attackStrength = player.getAttackStrengthScale(0.5f);
    if (attackStrength > 0.8f) {
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY(0.5), target.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
            serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, target.getX(), target.getY(0.5), target.getZ(), 5, 0.1, 0.1, 0.1, 0.1);
        }
        
        float pitch = 1.0f + (attackStrength - 0.8f) * 2.0f;
        player.level().playSound(null, target.getX(), target.getY(), target.getZ(), 
            SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1.0f, pitch);
    }
}
```

---

## 🔗 関連ドキュメントリンク
* [[Minecraft 26.2 ポータルに戻る|ja_jp-26.2-Home]]
* [[26.2 武器クールダウンメカニクス|ja_jp-26.2-Weapon-Cooldown-Mechanics]]
* [[26.2 設定とゲームルール (GameRules) マトリクス|ja_jp-26.2-Configuration-and-GameRules]]
* [[26.2 アーキテクチャ設計と Mixin 解析|ja_jp-26.2-Architecture-and-Mixins]]
