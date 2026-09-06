# ⚔️ Instant Gratification: Combat Cooldown Adjuster — Deutsches Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

> 📌 **Haftungsausschluss zur Repository-Quelle**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den offiziellen Veröffentlichungen auf CurseForge und Modrinth enthalten kann.

Willkommen im offiziellen technischen Wiki für **Combat Cooldown Adjuster** für Minecraft Fabric! Diese Mod entfernt die künstliche Angriffsverzögerung aus Minecraft 1.9+ und ermöglicht rasante Nahkämpfe mit anpassbaren Tick-Verzögerungen und dynamischem Treffer-Feedback.

---

## 🧭 Versionsportale

Gemäß unserer **1 Jar 1 Version**-Architektur verfügt jeder Versionsanker über ein separates Dokumentationsportal:

| Minecraft-Version | Mod-Version | Fabric Loader | Dokumentationsportal |
| :--- | :---: | :---: | :--- |
| **Minecraft 26.2** (`26.1.2`) | `1.0.1+26.2` | `>=0.19.1` | [[👉 Minecraft 26.2 Portal|26.2-Home]] |
| **Minecraft 26.3** (`26.3-snapshot-6`) | `1.0.1+26.3` | `>=0.19.3` | [[👉 Minecraft 26.3 Portal|26.3-Home]] |

---

## ⚡ Kernfunktionen

1. **Kategorische Tick-Überschreibungen**:
   Individuelle Verzögerung in Ticks (20 Ticks = 1 Sekunde) nach Gegenstands-Tags. Schwerter: 4 Ticks (5 Angriffe/s), Äxte: 8 Ticks, Hauen: 1 Tick. Ein Wert von `0` reaktiviert das beliebte 1.8-Spam-Klick-System!
2. **Schnellleisten-Agilität (Swap Agility)**:
   Mit `ig:prevent_item_swap_cooldown` wird die Angriffsaufladung beim Wechseln von Gegenständen in der Hotbar **nicht** zurückgesetzt.
3. **Dynamisches Treffer-Feedback (Combat Juice)**:
   Angriffe mit über 80% Aufladung ($S > 0.8$) erzeugen kritische Partikel und erhöhen die Tonhöhe des Treffersounds dynamisch von $1.0\times$ bis $1.4\times$.
4. **Echtzeit-GameRules via DasikLibrary**:
   Alle 9 Spielregeln können ohne Serverneustart per `/gamerule` im Chat angepasst werden.

---

## 📊 GameRules-Übersicht

| GameRule | Typ | Standard | Beschreibung |
| :--- | :---: | :---: | :--- |
| `ig:sword_cooldown_ticks` | Ganzzahl | `4` | Angriffsabklingzeit für Schwerter in Ticks |
| `ig:axe_cooldown_ticks` | Ganzzahl | `8` | Angriffsabklingzeit für Äxte |
| `ig:pickaxe_cooldown_ticks` | Ganzzahl | `4` | Angriffsabklingzeit für Spitzhacken |
| `ig:shovel_cooldown_ticks` | Ganzzahl | `2` | Angriffsabklingzeit für Schaufeln |
| `ig:hoe_cooldown_ticks` | Ganzzahl | `1` | Angriffsabklingzeit für Hauen |
| `ig:spear_cooldown_ticks` | Ganzzahl | `6` | Angriffsabklingzeit für Speere (`#c:spears`) |
| `ig:generic_cooldown_ticks` | Ganzzahl | `4` | Abklingzeit für sonstige Gegenstände und Fäuste |
| `ig:prevent_item_swap_cooldown` | Boolean | `true` | Verhindert Zurücksetzen beim Hotbar-Wechsel |
| `ig:enable_combat_juice` | Boolean | `true` | Aktiviert Trefferpartikel und Tonhöhen-Feedback |

---

## ⚖️ Lizenz & Urheber
* **Autor**: Dasik (Rifaditya)
* **Lizenz**: GNU General Public License v3.0 (GPLv3)
* **Hauptseite**: [[Zurück zum englischen Portal|Home]]
