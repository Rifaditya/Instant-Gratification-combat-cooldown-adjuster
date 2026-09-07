# ⚔️ Minecraft 26.3 Dokumentationsportal

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Quellcode-Repository-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Release-Builds auf CurseForge und Modrinth enthalten kann.

Willkommen im Dokumentationsportal für **Instant Gratification: Combat Cooldown Adjuster** unter **Minecraft 26.3** (Ziel: `26.3-snapshot-6`)! Alle Angaben entsprechen exakt dieser Version.

---

## 🧭 Minecraft 26.3 Navigationsmatrix

| Funktion / Subsystem | Beschreibung | Zugehörige Wiki-Seite |
| :--- | :--- | :--- |
| **Waffen-Abklingzeit-Mechanik** | Delay overrides, item tags, attack rate math, hotbar swap agility | [[26.3 Waffen-Abklingzeit-Mechanik|de_de-26.3-Weapon-Cooldown-Mechanics]] |
| **Kampffeedback & Audioeffekte** | Dynamic pitch shifting ($1.0\times - 1.4\times$), critical particle bursts | [[26.3 Kampffeedback & Audioeffekte|de_de-26.3-Combat-Juice-and-Audio-Feedback]] |
| **Konfiguration & GameRules-Matrix** | Complete reference matrix of all 9 namespaced GameRules | [[26.3 Konfiguration & GameRules-Matrix|de_de-26.3-Configuration-and-GameRules]] |
| **Architektur & Mixin-Analyse** | Bytecode injection analysis, `PlayerMixin`, `CCAHooks` design | [[26.3 Architektur & Mixin-Analyse|de_de-26.3-Architecture-and-Mixins]] |
| **Entwickler-Setup & Toolchain** | JDK 25 environment, Gradle 9.3+ build commands, Loom setup | [[26.3 Entwickler-Setup & Toolchain|de_de-26.3-Developer-Setup-and-Building]] |

---

## 📊 Offizielle technische Spezifikation

| Parameter | Technische Spezifikation |
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

## ⚔️ Highlights der Subsysteme

1. **Sub-Tick-Waffengeschwindigkeit (Sub-Tick Weapon Timing)**:
   Überschreibt Verzögerungen direkt mit ganzzahligen Ticks ($T_{\text{{delay}}}$). Schwerter: 4 Ticks (5.0 Angriffe/s), Äxte: 8 Ticks (2.5 Angriffe/s), Hacken: 1 Tick (20.0 Angriffe/s). Mit $T = 0$ wird schnelles 1.8-Klicken möglich.

2. **Schnelligkeit beim Slot-Wechsel (Hotbar Swap Agility)**:
   `ig:prevent_item_swap_cooldown` verhindert das Zurücksetzen des Ladebalkens beim Wechseln von Gegenständen.

3. **Multisensorisches Kampffeedback (Multi-Sensory Combat Juice)**:
   Angriffe mit über 80% Aufladung erzeugen doppelte Partikel und einen dynamisch erhöhten Ton (von $1.0\times$ bis $1.4\times$).

---

## 🔗 Zentrale Portale
* [[Zurück zum Hauptportal des Wikis|de_de-Home]]
* [[Minecraft 26.2 Dokumentationsportal|de_de-26.2-Home]]
* [[Versionskompatibilitätsmatrix|de_de-Version-Compatibility]]
* [[Fehlerbehebung & FAQ|de_de-Troubleshooting-and-FAQ]]
