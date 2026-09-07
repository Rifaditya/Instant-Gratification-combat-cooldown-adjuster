# 🔧 Solução de Problemas e FAQ

> 📌 **Isenção de Responsabilidade do Código Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das versões públicas no CurseForge e Modrinth.

## 1. Informações Técnicas Oficiais
| Parâmetro | Detalhes de Diagnóstico |
| :--- | :--- |
| **Subsistema Alvo** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **GameRules de Controle** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **Ponto de Injeção Principal** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **Dependência Principal** | `dasik-library` (Dynamic GameRule Manager) |
| **Namespace de Log** | `combat-cooldown-adjuster` |

---

## 2. Fluxograma de Diagnóstico

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

## 3. Guia de Diagnóstico Passo a Passo

### Fase 1: Calibrar os Ticks das Armas
1. Abra o bate-papo do jogo ou o console do servidor.
2. Consulte o valor de ticks ativo para seu tipo de arma:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. Se o valor retornado for o padrão `4`, a taxa de ataque é:
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ ataques/seg}}
4. Para testar cliques contínuos instantâneos (estilo Minecraft 1.8), execute:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. Seus ataques serão registrados instantaneamente sem recarga.

### Fase 2: Diagnosticar a Agilidade de Troca na Barra Rápida
1. Coloque uma Espada no slot 1 e um Machado no slot 2.
2. Ataque com a espada para acionar o medidor.
3. Pressione imediatamente `2` para trocar para o machado.
4. Se `ig:prevent_item_swap_cooldown` for `true`, o medidor de ataque **não** será zerado.
5. Se ele zerar, execute:
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### Fase 3: Otimização de Partículas e Áudio
1. O Combat Juice é ativado quando a carga de ataque é maior que $0.8$ (80%).
2. Se computadores mais fracos sofrerem travamentos, desative no servidor:
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. Isso desativa completamente as chamadas a `sendParticles` e `playSound` em `CCAHooks.applyCombatJuice`.

---

## 4. Tabela de Calibração Matemática

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

## 5. Perguntas Frequentes (FAQ)

### Q1: Definir o atraso de ticks para 0 corrompe o cálculo de dano vanilla?
**Não.** O vanilla calcula a barra como $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$. Quando $T = 0$, nosso Mixin retorna $0.0$, forçando o motor a avaliar $S(t) = 1.0$, aplicando 100% do dano base da arma.

### Q2: Por que armas de outros mods sem tags atacam com 4 ticks?
Se um item de terceiros não implementar as tags vanilla nem `#c:spears`, o `CCAHooks.getCooldownTicks` usará o `CombatRules.GENERIC_TICKS` (padrão: 4 ticks). Ajuste com:
```mcfunction
/gamerule ig:generic_cooldown_ticks <valor>
```

### Q3: O mod causa atrasos ou problemas de sincronia no multijogador?
O Combat Cooldown Adjuster opera com autoridade do servidor. Em servidores dedicados, o `PlayerMixin` é injetado na entidade do servidor, mantendo as avaliações seguras e sem lag de rede.

### Q4: As GameRules são salvas nos mundos?
**Sim.** Todas as 9 regras são salvas diretamente no arquivo `level.dat` do mundo através do DasikLibrary.

---

## 6. Links de Documentação Relacionados
* [[Voltar ao Portal Principal da Wiki|pt_br-Home]]
* [[26.2 Configuração e Matriz de GameRules|pt_br-26.2-Configuration-and-GameRules]]
* [[26.3 Configuração e Matriz de GameRules|pt_br-26.3-Configuration-and-GameRules]]
* [[Configuração de Desenvolvedor e Compilação|pt_br-Developer-Setup-and-Building]]
