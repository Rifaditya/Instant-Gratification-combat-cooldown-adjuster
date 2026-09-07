# ⚔️ Portal de Documentação do Minecraft 26.2

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Isenção de Responsabilidade do Código Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das versões públicas no CurseForge e Modrinth.

Bem-vindo ao portal técnico de documentação do **Instant Gratification: Combat Cooldown Adjuster** no **Minecraft 26.2** (alvo `26.1.2`)! Todas as informações refletem o código real desta versão.

---

## 🧭 Matriz de Navegação do Minecraft 26.2

| Recurso / Subsistema | Descrição Detalhada | Página Dedicada da Wiki |
| :--- | :--- | :--- |
| **Mecânicas de Tempo de Recarga de Armas** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.2 Mecânicas de Tempo de Recarga de Armas|pt_br-26.2-Weapon-Cooldown-Mechanics]] |
| **Impacto de Combate e Retorno de Áudio** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.2 Impacto de Combate e Retorno de Áudio|pt_br-26.2-Combat-Juice-and-Audio-Feedback]] |
| **Configuração e Matriz de GameRules** | Complete reference matrix of all 9 namespaced GameRules | [[26.2 Configuração e Matriz de GameRules|pt_br-26.2-Configuration-and-GameRules]] |
| **Arquitetura e Análise de Mixins** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.2 Arquitetura e Análise de Mixins|pt_br-26.2-Architecture-and-Mixins]] |
| **Ambiente de Desenvolvimento e Toolchain** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.2 Ambiente de Desenvolvimento e Toolchain|pt_br-26.2-Developer-Setup-and-Building]] |

---

## 📊 Informações Técnicas Oficiais

| Parâmetro | Especificação Técnica |
| :--- | :--- |
| **Minecraft Release Target** | `26.1.2` |
| **Mod SemVer Release** | `1.0.1+26.2` |
| **Fabric Loader Requirement** | `0.19.1` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.6.9+build.24` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ Destaques dos Subsistemas Principais

1. **Tempo de Arma Sub-Tick (Sub-Tick Weapon Timing)**:
Substituição do atraso por ticks inteiros diretos ($T_{\text{{delay}}}$). Espadas: 4 ticks (5.0 ataques/s), machados: 8 ticks (2.5 ataques/s), enxadas: 1 tick (20.0 ataques/s). Com $T = 0$, ativa-se o clique contínuo instantâneo da versão 1.8.

2. **Agilidade de Troca na Barra Rápida (Hotbar Swap Agility)**:
Ativar `ig:prevent_item_swap_cooldown` elimina o reinício forçado da barra de ataque ao alternar itens.

3. **Impacto de Combate Multissensorial (Multi-Sensory Combat Juice)**:
Ataques com mais de 80% de carga geram partículas críticas duplas e um som com tom ascendente dinâmico (de $1.0\times$ até $1.4\times$).

---

## 🔗 Portais Globais
* [[Voltar ao Portal Principal da Wiki|pt_br-Home]]
* [[Portal de Documentação do Minecraft 26.3|pt_br-26.3-Home]]
* [[Matriz de Compatibilidade de Versões|pt_br-Version-Compatibility]]
* [[Solução de Problemas e FAQ|pt_br-Troubleshooting-and-FAQ]]
