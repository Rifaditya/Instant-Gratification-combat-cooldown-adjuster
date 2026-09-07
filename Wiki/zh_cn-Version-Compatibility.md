# 📊 版本兼容性矩阵

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

## 1. 官方技术信息
| 参数 | 技术规范 |
| :--- | :--- |
| **模组标识符** | `combat-cooldown-adjuster` |
| **模组系列** | Instant Gratification (IG) |
| **支持的 Fabric 锚点版本** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Java 运行平台** | OpenJDK 25 (Hotspot 64-bit) |
| **构建工具链** | Gradle 9.3+ with Fabric Loom |
| **架构规范** | 1 Jar 1 Version（单版本单 Jar 规范） |
| **公共 API 状态** | 独立模组（调用 DasikLibrary API） |

---

## 2. 版本兼容性矩阵
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 标准锚点 |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 现代前沿分支 |

---

## 3. 1 Jar 1 Version 规范与通用库依赖边界

### 专属模组二进制构建
与通过宽泛版本范围导出计算 API 的共享库不同，直接向 `net.minecraft.world.entity.player.Player` 注入字节码的模组必须在编译期严格对接目标版本的 Mojang 混淆映射与中间层方法描述符。
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### 通用 DasikLibrary 集成
Combat Cooldown Adjuster 依赖 **DasikLibrary** 实现运行期动态游戏规则注册 (`DynamicGameRuleManager`)。DasikLibrary 遵循开放版本边界架构 (`>=26.1.2-`)，确保：
1. 与服务端游戏规则序列化完全向前与向后兼容。
2. 游戏内 `/gamerule` 动态 Tab 补全覆盖所有游戏环境。
3. 客户端类加载安全防护（纯服务端执行彻底杜绝客户端崩溃）。

---

## 4. 安装与前置准备工作流

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

### 安装验证核对清单：
1. 确保 Java 运行时为 OpenJDK 25 Hotspot（例如 Eclipse Adoptium `jdk-25.0.3+`）。
2. 验证 `mods` 文件夹中已存在 `fabric-api`。
3. 验证 `mods` 文件夹中已存在 `dasik-library`。
4. 启动游戏并检查日志输出：
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. 相关文档链接
* [[返回主维基门户|zh_cn-Home]]
* [[26.2 武器冷却机制|zh_cn-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 武器冷却机制|zh_cn-26.3-Weapon-Cooldown-Mechanics]]
* [[故障排除与常见问题|zh_cn-Troubleshooting-and-FAQ]]
