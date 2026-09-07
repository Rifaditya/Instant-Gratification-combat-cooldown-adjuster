# 🛠️ Entorno de Desarrollo y Herramientas (Minecraft 26.2)

> 📌 **Descargo de Responsabilidad del Código Fuente del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, el cual puede incluir commits recientes no publicados o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

## 1. Ficha Técnica Oficial
| Parámetro | Especificación Técnica |
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

## 2. Requisitos del Espacio de Trabajo

1. **Install OpenJDK 25**: Download and install Eclipse Adoptium Temurin OpenJDK 25.
2. **Verify Installation**:
   ```bash
   java -version
   ```

---

## 3. Flujo de Compilación Paso a Paso

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

## 4. Arquitectura de Subproyectos y Configuración de Loom

```properties
minecraft_version=26.1.2
fabric_version=0.145.4+26.1.2
fabric_loader_version=0.19.1
dasik_library_version=1.6.9+build.24
```

---

## 🔗 Enlaces de Documentación Relacionados
* [[Volver al Portal de Minecraft 26.2|es_es-26.2-Home]]
* [[26.2 Arquitectura y Análisis de Mixins|es_es-26.2-Architecture-and-Mixins]]
* [[Configuración de Desarrollador y Compilación|es_es-Developer-Setup-and-Building]]
