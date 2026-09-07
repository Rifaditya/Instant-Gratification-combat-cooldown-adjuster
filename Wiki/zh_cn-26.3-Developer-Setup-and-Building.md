# 🛠️ 开发者配置与构建 (Minecraft 26.3)

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

## 1. 官方技术信息
| 参数 | 技术规范 |
| :--- | :--- |
| **Minecraft Target** | `26.3-snapshot-6` |
| **Target Subproject** | `Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster/` |
| **Mod SemVer** | `1.0.1+26.3` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Build Automation** | Gradle 9.3+ with Fabric Loom |
| **Fabric Loader** | `0.19.3` |
| **Fabric API** | `0.156.1+26.3` |
| **DasikLibrary** | `1.8.36` |

---

## 2. 开发工作区与环境准备

1. **Install OpenJDK 25**: Download and install Eclipse Adoptium Temurin OpenJDK 25.
2. **Verify Installation**:
   ```bash
   java -version
   ```

---

## 3. 分步构建工作流

```bash
# Navigate to subproject directory
cd "Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster"

# Run non-daemon build
./gradlew build --no-daemon
```

### Output Binaries:
* `combat-cooldown-adjuster-1.0.1+26.3.jar`
* `combat-cooldown-adjuster-1.0.1+26.3-sources.jar`

---

## 4. 子项目架构与 Loom 配置

```properties
minecraft_version=26.3-snapshot-6
fabric_version=0.156.1+26.3
fabric_loader_version=0.19.3
dasik_library_version=1.8.36
```

---

## 🔗 相关文档链接
* [[返回 Minecraft 26.3 门户|zh_cn-26.3-Home]]
* [[26.3 架构设计与 Mixin 解析|zh_cn-26.3-Architecture-and-Mixins]]
* [[开发者配置与统一构建指南|zh_cn-Developer-Setup-and-Building]]
