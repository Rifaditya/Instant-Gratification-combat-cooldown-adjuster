# 📊 Matriz de Compatibilidade de Versões

> 📌 **Isenção de Responsabilidade do Código Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das versões públicas no CurseForge e Modrinth.

## 1. Informações Técnicas Oficiais
| Parâmetro | Especificação Técnica |
| :--- | :--- |
| **Identificador do Mod** | `combat-cooldown-adjuster` |
| **Coleção do Mod** | Instant Gratification (IG) |
| **Versões Suportadas do Fabric** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Plataforma Java** | OpenJDK 25 (Hotspot 64-bit) |
| **Ferramentas de Compilação** | Gradle 9.3+ with Fabric Loom |
| **Padrão de Arquitetura** | Política de 1 Jar 1 Versão (1 Jar 1 Version Policy) |
| **Status da API Pública** | Mod Independente (Consome API do DasikLibrary) |

---

## 2. Matriz de Compatibilidade de Versões
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 Versão Padrão Estável |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 Ramo Snapshot Moderno |

---

## 3. Política 1 Jar 1 Versão vs Limites de Bibliotecas Universais

### Binários Dedicados do Mod
Ao contrário de bibliotecas genéricas, mods que injetam bytecode diretamente em `net.minecraft.world.entity.player.Player` requerem verificação estrita em tempo de compilação em relação aos mapeamentos oficiais da Mojang.
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### Integração Universal com DasikLibrary
O Combat Cooldown Adjuster utiliza o **DasikLibrary** para o registro dinâmico de GameRules (`DynamicGameRuleManager`):
1. Compatibilidade completa com a serialização de regras do servidor.
2. Autocompletar dinâmico com Tab no jogo para `/gamerule`.
3. Segurança do classloader no cliente prevenindo travamentos.

---

## 4. Fluxo de Instalação e Pré-requisitos

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

### Lista de Verificação:
1. Certifique-se de que o runtime do Java seja o OpenJDK 25 Hotspot (ex.: Eclipse Adoptium `jdk-25.0.3+`).
2. Verifique se o `fabric-api` está na pasta `mods`.
3. Verifique se o `dasik-library` está na pasta `mods`.
4. Inicie o jogo e confirme no log:
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. Links de Documentação Relacionados
* [[Voltar ao Portal Principal da Wiki|pt_br-Home]]
* [[26.2 Mecânicas de Tempo de Recarga de Armas|pt_br-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 Mecânicas de Tempo de Recarga de Armas|pt_br-26.3-Weapon-Cooldown-Mechanics]]
* [[Solução de Problemas e FAQ|pt_br-Troubleshooting-and-FAQ]]
