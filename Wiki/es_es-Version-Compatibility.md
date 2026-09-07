# 📊 Matriz de Compatibilidad de Versiones

> 📌 **Descargo de Responsabilidad del Código Fuente del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, el cual puede incluir commits recientes no publicados o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

## 1. Ficha Técnica Oficial
| Parámetro | Especificación Técnica |
| :--- | :--- |
| **Identificador del Mod** | `combat-cooldown-adjuster` |
| **Colección de Mods** | Instant Gratification (IG) |
| **Versiones de Fabric Compatibles** | `26.2` (MC 26.1.2), `26.3` (MC 26.3-snapshot-6) |
| **Plataforma Java** | OpenJDK 25 (Hotspot 64-bit) |
| **Herramientas de Compilación** | Gradle 9.3+ with Fabric Loom |
| **Estándar de Arquitectura** | Política 1 Jar 1 Versión (1 Jar 1 Version Policy) |
| **Estado de la API Pública** | Mod Independiente (Consume API de DasikLibrary) |

---

## 2. Matriz de Compatibilidad de Versiones
Combat Cooldown Adjuster enforces the **1 Jar 1 Version Policy**: every major Minecraft version anchor receives a discrete, dedicated binary compiled specifically against that target's obfuscation mapping, bytecode structure, and Fabric API lifecycle.

| Minecraft Target | Mod SemVer | Fabric Loader | Java Requirement | Fabric API Version | DasikLibrary Dependency | Distribution Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **MC 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | JDK 25 (`>=25`) | `0.145.4+26.1.2` | `>=1.6.9+` | 🟢 Versión Estable Estándar |
| **MC 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | JDK 25 (`>=25`) | `0.156.1+26.3` | `>=1.8.36` | 🟢 Rama Moderna Snapshot |

---

## 3. Política 1 Jar 1 Versión frente a Librerías Universales

### Binarios de Mod Dedicados
A diferencia de las librerías compartidas que ofrecen APIs matemáticas abiertas, los mods que inyectan código directamente en `net.minecraft.world.entity.player.Player` requieren verificación estricta en tiempo de compilación frente a los mapeos exactos de Mojang.
* `combat-cooldown-adjuster-1.0.1+26.2.jar`: Target anchor for stable MC 26.1.2 and MC 26.2 installations.
* `combat-cooldown-adjuster-1.0.1+26.3.jar`: Target anchor for developmental snapshot environments (MC 26.3-snapshot-6 and beyond).

### Integración con DasikLibrary Universal
Combat Cooldown Adjuster confía en **DasikLibrary** para el registro dinámico de GameRules (`DynamicGameRuleManager`), garantizando:
1. Compatibilidad total con la serialización de reglas del servidor.
2. Autocompletado dinámico mediante tabulador para `/gamerule`.
3. Seguridad del classloader en el cliente sin riesgo de caídas.

---

## 4. Flujo de Instalación y Requisitos

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

### Lista de Verificación:
1. Asegurar que el entorno Java sea OpenJDK 25 Hotspot (ej. Eclipse Adoptium `jdk-25.0.3+`).
2. Verificar la presencia de `fabric-api` en la carpeta `mods`.
3. Verificar la presencia de `dasik-library` en la carpeta `mods`.
4. Iniciar el juego y confirmar en los registros:
   `[combat-cooldown-adjuster] Instant Gratification: Combat Cooldown Adjuster Initialized`

---

## 5. Enlaces de Documentación Relacionados
* [[Volver al Portal Principal de la Wiki|es_es-Home]]
* [[26.2 Mecánicas de Enfriamiento de Armas|es_es-26.2-Weapon-Cooldown-Mechanics]]
* [[26.3 Mecánicas de Enfriamiento de Armas|es_es-26.3-Weapon-Cooldown-Mechanics]]
* [[Solución de Problemas y FAQ|es_es-Troubleshooting-and-FAQ]]
