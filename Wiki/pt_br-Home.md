# ⚔️ Instant Gratification: Combat Cooldown Adjuster — Wiki em Português

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Aviso Legal da Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das versões públicas no CurseForge e Modrinth.

Bem-vindo à documentação oficial do **Combat Cooldown Adjuster** para Minecraft Fabric! Este mod restaura o combate ágil e visceral ao remover o atraso artificial de ataque imposto desde o Minecraft 1.9+, proporcionando controle em ticks e retorno audiovisual dinâmico.

---

## 🧭 Portais por Versão

Seguindo o padrão **1 Jar 1 Version**, cada versão possui documentação dedicada:

| Versão do Minecraft | SemVer do Mod | Fabric Loader | Portal de Documentação |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 Portal Minecraft 26.2|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 Portal Minecraft 26.3|26.3-Home]] |

---

## ⚡ Recursos Principais

1. **Substituição Categórica de Ticks de Ataque**:
   Defina diretamente os ticks de recarga por categoria (`#minecraft:swords`, `#minecraft:axes`, `#c:spears`). Espadas: 4 ticks (5 golpes/s), Machados: 8 ticks, Enxadas: 1 tick. Definir como `0` restaura os cliques ilimitados do Minecraft 1.8!
2. **Agilidade de Troca de Itens (Swap Agility)**:
   A regra `ig:prevent_item_swap_cooldown` impede que o medidor de ataque seja zerado ao alternar itens na hotbar.
3. **Retorno de Combate Dinâmico (Combat Juice)**:
   Golpes com mais de 80% de carga ($S > 0.8$) liberam partículas críticas e aumentam o tom do som de ataque entre $1.0\times$ e $1.4\times$.
4. **GameRules Dinâmicos via DasikLibrary**:
   Todas as 9 opções são GameRules nativos ajustáveis em tempo real via `/gamerule`.

---

## 📊 Tabela de GameRules

| Regra | Tipo | Padrão | Descrição |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | Inteiro | `4` | Recarga de ataque para espadas em ticks (0.2 s) |
| `ig:axe_cooldown_ticks` | Inteiro | `8` | Recarga de ataque para machados (0.4 s) |
| `ig:pickaxe_cooldown_ticks` | Inteiro | `4` | Recarga de ataque para picaretas |
| `ig:shovel_cooldown_ticks` | Inteiro | `2` | Recarga de ataque para pás |
| `ig:hoe_cooldown_ticks` | Inteiro | `1` | Recarga de ataque para enxadas |
| `ig:spear_cooldown_ticks` | Inteiro | `6` | Recarga de ataque para lanças (`#c:spears`) |
| `ig:generic_cooldown_ticks` | Inteiro | `4` | Recarga para punhos e itens sem categoria |
| `ig:prevent_item_swap_cooldown` | Booleano | `true` | Não zerar medidor ao trocar de item |
| `ig:enable_combat_juice` | Booleano | `true` | Ativar partículas e modulação de áudio |

---

## ⚖️ Licença & Autoria
* **Autor**: Dasik (Rifaditya)
* **Licença**: GNU General Public License v3.0 (GPLv3)
* **Página Inicial**: [[Voltar ao Portal em Inglês|Home]]
