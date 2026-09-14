# Architecture & Symbol Index: Combat Cooldown Adjuster

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `combat-cooldown-adjuster`
- **Main Entrypoint**: `net.instantgratification.combatcooldownadjuster.CombatCooldownAdjuster` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `net.instantgratification.combatcooldownadjuster.CombatCooldownAdjusterClient`

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `Vanilla Class` | `net.instantgratification.combatcooldownadjuster.mixin.PlayerMixin` | Core mixin hook |

## 3. Core Mechanics & Subsystems
- **Source Root**: `src/main/java/`
- **Resource Root**: `src/main/resources/`

## 4. Dynamic GameRules & Commands
- **GameRules / Commands**: Configured dynamically via namespaced keys (`combat-cooldown-adjuster:*`).

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe logic in main, client isolated in `src/client/java` or client entrypoint.
