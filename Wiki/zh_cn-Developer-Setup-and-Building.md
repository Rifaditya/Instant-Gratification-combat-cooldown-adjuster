# 🛠️ 开发者配置与统一构建指南

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

## 1. 官方技术信息
| 参数 | 技术规范 |
| :--- | :--- |
| **构建工具链** | Gradle 9.3+ with Fabric Loom |
| **Java 运行平台** | OpenJDK 25 (Hotspot 64-bit) |
| **模组系列** | Multi-Version Anchor Subprojects (`26.2` & `26.3`) |
| **Bytecode Injector** | SpongePowered Mixin 0.8+ |
| **核心依赖** | Fabric API, DasikLibrary (`DynamicGameRuleManager`) |
| **Compilation Flag** | `./gradlew build --no-daemon` |

---

## 2. 开发工作区与环境准备

Building Combat Cooldown Adjuster from source requires an active JDK 25 installation.

```bash
# Verify Java version
java -version
# Expected output: openjdk version "25" ...
```

---

## 3. 分步构建工作流

```
[ Clone Repository ]
         |
         v
[ Navigate to Target Subproject Directory ]
  - Combat Cooldown Adjuster v26.2/combat-cooldown-adjuster/
  - Combat Cooldown Adjuster v26.3/combat-cooldown-adjuster/
         |
         v
[ Verify gradle.properties Configuration ]
         |
         v
[ Execute Unified Build Command ]
  ./gradlew build --no-daemon
         |
         v
[ Inspect Output Artifacts in build/libs/ ]
```

```bash
# For Linux / macOS:
./gradlew build --no-daemon

# For Windows PowerShell:
.\gradlew build --no-daemon
```

Artifacts are generated in `build/libs/`:
* Standard binary: `combat-cooldown-adjuster-1.0.1+<mc_version>.jar`
* Sources jar: `combat-cooldown-adjuster-1.0.1+<mc_version>-sources.jar`

---

## 4. 子项目架构与 Loom 配置

```properties
org.gradle.parallel=false
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true
```

### Dependency Matrix across Anchors:
* **Version 26.2 (`gradle.properties`)**:
  ```properties
  minecraft_version=26.1.2
  fabric_version=0.145.4+26.1.2
  fabric_loader_version=0.19.1
  dasik_library_version=1.6.9+build.24
  ```
* **Version 26.3 (`gradle.properties`)**:
  ```properties
  minecraft_version=26.3-snapshot-6
  fabric_version=0.156.1+26.3
  fabric_loader_version=0.19.3
  dasik_library_version=1.8.36
  ```

---

## 5. Mixin 架构与纯度标准
Combat Cooldown Adjuster 严格遵循 **Mixin 纯度标准**：
1. **Mixin 类零逻辑**：`PlayerMixin.java` 绝不包含任何业务运算、字符串拼接或粒子循环。
2. **静态工具委托**：全部数学运算、标签检测及粒子音效均封装于 `CCAHooks.java`。
3. **取消安全性**：注入点采用明确的 `@At("HEAD")` 并在覆盖返回值时声明 `cancellable = true`。

### Injected Target Signatures in `PlayerMixin.java`:
```java
// 1. Override weapon cooldown delay
@Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("HEAD"), cancellable = true)
private void cca$overrideAttackDelay(CallbackInfoReturnable<Float> cir) {
    Player player = (Player) (Object) this;
    ItemStack stack = this.getMainHandItem();
    int ticks = CCAHooks.getCooldownTicks(player, stack);
    if (ticks >= 0) {
        cir.setReturnValue((float) ticks);
    }
}

// 2. Prevent attack meter reset on hotbar item switch
@Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"), cancellable = true)
private void cca$preventSwapReset(CallbackInfo ci) {
    Player player = (Player) (Object) this;
    if (CombatRules.getBoolean(player.level(), CombatRules.PREVENT_SWAP_RESET)) {
        ci.cancel();
    }
}

// 3. Inject Combat Juice particle and sound feedback
@Inject(method = "attack", at = @At("HEAD"))
private void cca$applyJuice(Entity target, CallbackInfo ci) {
    Player player = (Player) (Object) this;
    CCAHooks.applyCombatJuice(player, target);
}
```

---

## 6. 附属开发者与数据驱动扩展接口
通过 `#c:spears` 接入矛类武器：
在 `data/c/tags/item/spears.json` 中声明物品 ID 即可自动归类并应用矛类冷却规则。

```json
{
  "replace": false,
  "values": [
    "examplemod:iron_spear",
    "examplemod:diamond_spear"
  ]
}
```

---

## 7. 相关文档链接
* [[返回主维基门户|zh_cn-Home]]
* [[26.2 架构设计与 Mixin 解析|zh_cn-26.2-Architecture-and-Mixins]]
* [[26.3 架构设计与 Mixin 解析|zh_cn-26.3-Architecture-and-Mixins]]
* [[版本兼容性矩阵|zh_cn-Version-Compatibility]]
