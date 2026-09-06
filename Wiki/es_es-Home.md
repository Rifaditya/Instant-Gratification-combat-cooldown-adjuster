# ⚔️ Instant Gratification: Combat Cooldown Adjuster — Wiki en Español

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Descargo de responsabilidad sobre la fuente del repositorio**: La documentación de esta Wiki refleja el **estado actual del código fuente en el repositorio**, que puede incluir confirmaciones recientes no publicadas o características en desarrollo antes de las versiones públicas en CurseForge y Modrinth.

¡Bienvenido a la documentación técnica oficial de **Combat Cooldown Adjuster** para Minecraft Fabric! Este mod restaura la velocidad y el dinamismo del combate cuerpo a cuerpo eliminando los retrasos artificiales impuestos desde la versión 1.9+, ofreciendo un control preciso de ticks y una respuesta audiovisual impactante.

---

## 🧭 Portales de Versiones

Bajo la política **1 Jar 1 Version**, cada versión objetivo cuenta con un árbol de documentación exclusivo:

| Versión de Minecraft | SemVer del Mod | Fabric Loader | Portal de Documentación |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 Portal de Minecraft 26.2|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 Portal de Minecraft 26.3|26.3-Home]] |

---

## ⚡ Mecánicas Principales

1. **Ajuste Categórico de Ticks de Ataque**:
   Asigna directamente los ticks de enfriamiento por etiqueta de objeto (`#minecraft:swords`, `#minecraft:axes`, `#c:spears`, etc.). La espada tiene 4 ticks (5 ataques/segundo), el hacha 8 ticks y la azada 1 tick (20 ataques/segundo). ¡Configurar el valor en `0` activa el clásico combate de clics rápidos de la 1.8!
2. **Agilidad al Cambiar de Objeto (Swap Agility)**:
   La regla `ig:prevent_item_swap_cooldown` evita que el medidor de ataque se reinicie a cero al cambiar de ranura en la barra de acceso rápido.
3. **Sensación de Combate Dinámica (Combat Juice)**:
   Los impactos con más del 80% de carga ($S > 0.8$) generan partículas de golpe crítico y elevan el tono del sonido de ataque de $1.0\times$ a $1.4\times$.
4. **Integración con Dynamic GameRules**:
   Gestionado por **DasikLibrary**, los 9 ajustes son GameRules nativos modificables en tiempo real con `/gamerule`.

---

## 📊 Matriz de GameRules

| Regla | Tipo | Por Defecto | Descripción |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | Entero | `4` | Enfriamiento para espadas en ticks (0.2 s) |
| `ig:axe_cooldown_ticks` | Entero | `8` | Enfriamiento para hachas en ticks (0.4 s) |
| `ig:pickaxe_cooldown_ticks` | Entero | `4` | Enfriamiento para picos |
| `ig:shovel_cooldown_ticks` | Entero | `2` | Enfriamiento para palas |
| `ig:hoe_cooldown_ticks` | Entero | `1` | Enfriamiento para azadas |
| `ig:spear_cooldown_ticks` | Entero | `6` | Enfriamiento para lanzas (`#c:spears`) |
| `ig:generic_cooldown_ticks` | Entero | `4` | Enfriamiento genérico para otros objetos y puños |
| `ig:prevent_item_swap_cooldown` | Booleano | `true` | No reiniciar medidor de ataque al cambiar objeto |
| `ig:enable_combat_juice` | Booleano | `true` | Activar partículas de impacto y tono acústico dinámico |

---

## ⚖️ Licencia y Autoría
* **Autor**: Dasik (Rifaditya)
* **Licencia**: GNU General Public License v3.0 (GPLv3)
* **Portal Principal**: [[Volver al Inicio en Inglés|Home]]
