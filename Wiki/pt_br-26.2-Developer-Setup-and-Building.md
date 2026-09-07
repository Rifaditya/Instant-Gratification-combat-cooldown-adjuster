# 🛠️ Ambiente de Desenvolvimento e Toolchain (Minecraft 26.2)

> 📌 **Isenção de Responsabilidade do Código Fonte do Repositório**: A documentação nesta Wiki reflete o **estado atual do código-fonte no repositório**, que pode incluir commits recentes não lançados ou recursos em desenvolvimento antes das versões públicas no CurseForge e Modrinth.

## 1. Informações Técnicas Oficiais
| Parâmetro | Especificação Técnica |
| :--- | :--- |
| **Minecraft Target** | `26.1.2` |
| **Target Subproject** | `Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster/` |
| **Mod SemVer** | `1.0.1+26.2` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Build Automation** | Gradle 9.3+ with Fabric Loom |
| **Fabric Loader** | `0.19.1` |
| **Fabric API** | `0.145.4+26.1.2` |
| **DasikLibrary** | `1.6.9+build.24` |

---

## 2. Requisitos do Ambiente de Desenvolvimento

1. **Install OpenJDK 25**: Download and install Eclipse Adoptium Temurin OpenJDK 25.
2. **Verify Installation**:
   ```bash
   java -version
   ```

---

## 3. Fluxo de Compilação Passo a Passo

```bash
# Navigate to subproject directory
cd "Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster"

# Run non-daemon build
./gradlew build --no-daemon
```

### Output Binaries:
* `combat-cooldown-adjuster-1.0.1+26.2.jar`
* `combat-cooldown-adjuster-1.0.1+26.2-sources.jar`

---

## 4. Arquitetura de Subprojetos e Configuração do Loom

```properties
minecraft_version=26.1.2
fabric_version=0.145.4+26.1.2
fabric_loader_version=0.19.1
dasik_library_version=1.6.9+build.24
```

---

## 🔗 Links de Documentação Relacionados
* [[Voltar ao Portal do Minecraft 26.2|pt_br-26.2-Home]]
* [[26.2 Arquitetura e Análise de Mixins|pt_br-26.2-Architecture-and-Mixins]]
* [[Configuração de Desenvolvedor e Compilação|pt_br-Developer-Setup-and-Building]]
