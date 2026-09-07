# 💥 Impacto de Combate e Retorno de Áudio (Minecraft 26.3)

> 📌 **Isenção de Responsabilidade do Código Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das versões públicas no CurseForge e Modrinth.

## 1. Informações Técnicas Oficiais
| Parâmetro | Especificação Técnica |
| :--- | :--- |
| **Subsystem Name** | Sistema Dinâmico de Retorno Sensorial de Combate |
| **Controlling GameRule** | `ig:enable_combat_juice` (Default: `true`) |
| **Trigger Threshold** | Attack Strength Scale $S > 0.8$ ($80\%$) |
| **Particle Types** | `ParticleTypes.CRIT`, `ParticleTypes.ENCHANTED_HIT` |
| **Sound Event** | `SoundEvents.PLAYER_ATTACK_STRONG` |
| **Sound Category** | `SoundSource.PLAYERS` |
| **Audio Pitch Range** | $1.0\times$ to $1.4\times$ (Dynamic Charge Shift) |
| **Bytecode Hook** | `PlayerMixin.cca$applyJuice` $\to$ `CCAHooks.applyCombatJuice` |

---

## 2. Fluxo de Sobrevivência do Jogador
1. **Carregar**: Deixe o medidor ultrapassar 80% ($S > 0.8$, leva apenas 3.2 ticks).
2. **Golpear**: Clique com o botão esquerdo para atacar o alvo.
3. **Partículas**: 10 partículas Crit e 5 partículas Enchanted Hit emitidas no centro do alvo.
4. **Tom do Áudio**: O som `SoundEvents.PLAYER_ATTACK_STRONG` atinge um tom de até $1.4\times$.
5. **Ajuste**: Pode ser desativado com `/gamerule ig:enable_combat_juice false`.

---

## 3. Fórmulas Matemáticas e Equações

### Condição de Disparo
$$\text{JuiceEnabled} = \text{true} \quad \land \quad S_{\text{strength}} > 0.8$$

### Fórmula de Variação de Tom do Áudio
$$\text{Pitch} = 1.0 + (S_{\text{strength}} - 0.8) \times 2.0$$

Análise de Valores Limites:
* Limite inferior ($S = 0.8$): $\text{{Pitch}} = 1.00$
* Carga média ($S = 0.9$): $\text{{Pitch}} = 1.20$
* Carga máxima ($S = 1.0$): $\text{{Pitch}} = 1.40$

### Parâmetros de Emissão de Partículas
* **`ParticleTypes.CRIT`**: Count $N = 10$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.
* **`ParticleTypes.ENCHANTED_HIT`**: Count $N = 5$, spatial delta $(\Delta x = 0.1, \Delta y = 0.1, \Delta z = 0.1)$, particle speed $0.1$.

---

## 4. Diagramas ASCII e Máquina de Estados

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

## 5. Esquemas de Tags de Itens e SNBT

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

## 6. Matriz de Referência Completa

| Charge Scale ($S$) | Charge Status | Particle Burst | Sound Event | Audio Pitch |
| :---: | :--- | :--- | :--- | :---: |
| **$0.00 - 0.80$** | Incomplete Charge | None | Default vanilla hit | Vanilla |
| **$0.81$** | Threshold Reached | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.02\times$ |
| **$0.85$** | Strong Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.10\times$ |
| **$0.90$** | High-Power Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.20\times$ |
| **$0.95$** | Near-Max Hit | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.30\times$ |
| **$1.00$** | Full Max Strike | 10 Crit + 5 Enchanted Hit | `PLAYER_ATTACK_STRONG` | $1.40\times$ |

---

## 7. Ganchos e Injeções de Mixin

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

## 🔗 Links de Documentação Relacionados
* [[Voltar ao Portal do Minecraft 26.3|pt_br-26.3-Home]]
* [[26.3 Mecânicas de Tempo de Recarga de Armas|pt_br-26.3-Weapon-Cooldown-Mechanics]]
* [[26.3 Configuração e Matriz de GameRules|pt_br-26.3-Configuration-and-GameRules]]
* [[26.3 Arquitetura e Análise de Mixins|pt_br-26.3-Architecture-and-Mixins]]
