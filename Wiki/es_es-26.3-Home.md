# ⚔️ Portal de Documentación Minecraft 26.3

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Descargo de Responsabilidad del Código Fuente del Repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, el cual puede incluir commits recientes no publicados o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

¡Bienvenido al portal técnico de **Instant Gratification: Combat Cooldown Adjuster** para **Minecraft 26.3** (objetivo `26.3-snapshot-6`)! Toda la documentación refleja con exactitud el código y configuración de esta versión.

---

## 🧭 Matriz de Navegación de Minecraft 26.3

| Característica / Subsistema | Descripción | Página Dedicada de la Wiki |
| :--- | :--- | :--- |
| **Mecánicas de Enfriamiento de Armas** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.3 Mecánicas de Enfriamiento de Armas|es_es-26.3-Weapon-Cooldown-Mechanics]] |
| **Retroalimentación de Combate y Efectos de Audio** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.3 Retroalimentación de Combate y Efectos de Audio|es_es-26.3-Combat-Juice-and-Audio-Feedback]] |
| **Configuración y Matriz de GameRules** | Complete reference matrix of all 9 namespaced GameRules | [[26.3 Configuración y Matriz de GameRules|es_es-26.3-Configuration-and-GameRules]] |
| **Arquitectura y Análisis de Mixins** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.3 Arquitectura y Análisis de Mixins|es_es-26.3-Architecture-and-Mixins]] |
| **Entorno de Desarrollo y Herramientas** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.3 Entorno de Desarrollo y Herramientas|es_es-26.3-Developer-Setup-and-Building]] |

---

## 📊 Ficha Técnica Oficial

| Parámetro | Especificación Técnica |
| :--- | :--- |
| **Minecraft Release Target** | `26.3-snapshot-6` |
| **Mod SemVer Release** | `1.0.1+26.3` |
| **Fabric Loader Requirement** | `0.19.3` |
| **Java Platform** | OpenJDK 25 (Hotspot 64-bit) |
| **Fabric API Dependency** | Target Anchor API |
| **DasikLibrary Dependency** | `1.8.36` |
| **Mixin Configuration** | `combat-cooldown-adjuster.mixins.json` |
| **Item Tag Conventions** | `#minecraft:*` and `#c:spears` |

---

## ⚔️ Aspectos Destacados de los Subsistemas

1. **Tiempos de Ataque Sub-Tick (Sub-Tick Weapon Timing)**:
   Reemplazo de retrasos por ticks enteros ($T_{\text{{delay}}}$). Espadas: 4 ticks (5.0 golpes/s), hachas: 8 ticks (2.5 golpes/s), azadas: 1 tick (20.0 golpes/s). Con $T = 0$ se desbloquea el spam click de 1.8.

2. **Agilidad en Cambio de Casilla (Hotbar Swap Agility)**:
   Activar `ig:prevent_item_swap_cooldown` evita el molesto reinicio de carga al cambiar de objeto en la barra rápida.

3. **Sensación de Combate Multisensorial (Multi-Sensory Combat Juice)**:
   Ataques con más del 80% de carga liberan partículas de impacto crítico y sonidos con tono ascendente (de $1.0\times$ a $1.4\times$).

---

## 🔗 Portales Globales
* [[Volver al Portal Principal de la Wiki|es_es-Home]]
* [[Portal de Documentación Minecraft 26.2|es_es-26.2-Home]]
* [[Matriz de Compatibilidad de Versiones|es_es-Version-Compatibility]]
* [[Solución de Problemas y FAQ|es_es-Troubleshooting-and-FAQ]]
