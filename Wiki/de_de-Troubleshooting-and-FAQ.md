# 🔧 Fehlerbehebung & FAQ

> 📌 **Quellcode-Repository-Haftungsausschluss**: Die Dokumentation in diesem Wiki spiegelt den **aktuellen Quellcode-Zustand im Repository** wider, der neuere, noch unveröffentlichte Commits oder Entwicklungsfunktionen vor den öffentlichen Release-Builds auf CurseForge und Modrinth enthalten kann.

## 1. Offizielle technische Spezifikation
| Parameter | Diagnosedetails |
| :--- | :--- |
| **Ziel-Subsystem** | Combat Timing, Item Swap, Particles, Audio, GameRules |
| **Steuernde GameRules** | `ig:*_cooldown_ticks`, `ig:prevent_item_swap_cooldown`, `ig:enable_combat_juice` |
| **Hauptinjektionspunkt** | `net.minecraft.world.entity.player.Player` (`PlayerMixin`) |
| **Hauptabhängigkeit** | `dasik-library` (Dynamic GameRule Manager) |
| **Log-Namespace** | `combat-cooldown-adjuster` |

---

## 2. Diagnose-Ablaufdiagramm

```
           [ Combat Timing Issue Detected ]
                          |
                          v
         Is weapon delay different from expected?
               /                         \
             YES                          NO
             /                             \
    Check GameRule:                Does item swap reset ticker?
    /gamerule ig:<item>_ticks             /             \
    Verify item tag match                YES             NO
    (e.g. #minecraft:swords)             /                \
                                  Check GameRule:     Are particles / audio missing?
                                  ig:prevent_item_          /             \
                                  swap_cooldown = true    YES             NO
                                                          /                \
                                                Attack charge > 80%?     All systems
                                                Check ig:enable_juice    functioning!
```

---

## 3. Schritt-für-Schritt-Diagnoseanleitung

### Phase 1: Waffen-Ticks kalibrieren
1. Chat im Spiel oder Serverkonsole öffnen.
2. Aktuellen Tick-Wert für den Waffentyp abfragen:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks
   ```
3. Wenn der Standardwert `4` zurückgegeben wird, beträgt die Angriffsrate:
   $$f = \frac{{20}}{{4}} = 5.0 \text{{ Angriffe/Sek}}
4. Um 1.8-Klick-Spam zu aktivieren, ausführen:
   ```mcfunction
   /gamerule ig:sword_cooldown_ticks 0
   ```
5. Angriffe erfolgen nun verzögerungsfrei.

### Phase 2: Schnellen Slot-Wechsel diagnostizieren
1. Schwert in Slot 1 und Axt in Slot 2 legen.
2. Mit dem Schwert zuschlagen.
3. Sofort Taste `2` drücken, um zur Axt zu wechseln.
4. Wenn `ig:prevent_item_swap_cooldown` auf `true` steht, wird der Ladebalken **nicht** auf 0 zurückgesetzt.
5. Falls er zurücksetzt, ausführen:
   ```mcfunction
   /gamerule ig:prevent_item_swap_cooldown true
   ```

### Phase 3: Partikel- und Audio-Optimierung
1. Combat Juice löst ab einer Aufladung von über $0.8$ (80%) aus.
2. Bei schwächerer Hardware kann der Effekt auf dem Server deaktiviert werden:
   ```mcfunction
   /gamerule ig:enable_combat_juice false
   ```
3. Dies überspringt die Aufrufe von `sendParticles` und `playSound` vollständig.

---

## 4. Mathematische Kalibrierungstabelle

| Tick Setting ($T$) | Attack Frequency ($f = 20/T$) | Attack Interval (ms) | Combat Feel Style |
| :---: | :---: | :---: | :--- |
| **`0`** | $\infty$ (20 TPS Engine Bound) | $0\text{ ms}$ (Instantaneous) | Pure 1.8 Click-Spam Combat |
| **`1`** | $20.0\text{ attacks/sec}$ | $50\text{ ms}$ | Ultra Hyper-Speed (Hoe Default) |
| **`2`** | $10.0\text{ attacks/sec}$ | $100\text{ ms}$ | Turbo Agility (Shovel Default) |
| **`4`** | $5.0\text{ attacks/sec}$ | $200\text{ ms}$ | Snappy Balanced Melee (Sword/Pickaxe Default) |
| **`6`** | $3.33\text{ attacks/sec}$ | $300\text{ ms}$ | Tactical Reach Cadence (Spear Default) |
| **`8`** | $2.5\text{ attacks/sec}$ | $400\text{ ms}$ | Heavy Impact Cleaving (Axe Default) |
| **`16`+** | $\le 1.25\text{ attacks/sec}$ | $\ge 800\text{ ms}$ | Vanilla 1.9+ Style Slow Paced Combat |

---

## 5. Häufig gestellte Fragen (FAQ)

### Q1: Zerstört ein Wert von 0 Ticks die Schadensberechnung von Vanilla?
**Nein.** Vanilla berechnet $S(t) = \min\left(1.0, \frac{{t + 0.5}}{{T}}\right)$. Bei $T = 0$ gibt unser Mixin $0.0$ zurück, wodurch $S(t) = 1.0$ sofort erreicht wird. Jeder Treffer verursacht 100% Basisschaden.

### Q2: Warum greifen Mod-Waffen ohne Tags mit 4 Ticks an?
Wenn ein Gegenstand keine bekannten Tags oder `#c:spears` besitzt, greift `CCAHooks.getCooldownTicks` auf `CombatRules.GENERIC_TICKS` (Standard: 4 Ticks) zurück. Anpassbar über:
```mcfunction
/gamerule ig:generic_cooldown_ticks <Wert>
```

### Q3: Verursacht diese Mod Lags im Mehrspielermodus?
Combat Cooldown Adjuster läuft autoritativ auf dem Server. `PlayerMixin` injiziert in die serverseitige Entität, wodurch alle Berechnungen synchron und verzögerungsfrei bleiben.

### Q4: Werden die GameRules in der Welt gespeichert?
**Ja.** Alle 9 Regeln werden direkt in der `level.dat` der Welt gespeichert.

---

## 6. Weiterführende Links
* [[Zurück zum Hauptportal des Wikis|de_de-Home]]
* [[26.2 Konfiguration & GameRules-Matrix|de_de-26.2-Configuration-and-GameRules]]
* [[26.3 Konfiguration & GameRules-Matrix|de_de-26.3-Configuration-and-GameRules]]
* [[Entwickler-Setup & Build-Anleitung|de_de-Developer-Setup-and-Building]]
