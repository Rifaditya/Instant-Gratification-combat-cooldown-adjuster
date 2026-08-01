# 🛠️ Combat Cooldown Adjuster: Developer Guide

This section is for developers looking to understand the internal mechanics or build extensions for the mod.

## 🏗️ Architecture
The mod uses a **Thin Mixin** approach. 
- **[Architecture Overview](Architecture/Architecture.md)**: Breakdown of hooks and logic providers.

## ⚙️ Registry
- **[GameRule Reference](gamerules_reference.md)**: Internal keys and data types.

## 🚀 Setting Up the Workspace
1. Clone the repository.
2. Run `./gradlew genSources` to set up mappings.
3. The project uses **Mojang Mappings** and **Java 25**.

## 🤝 Contributing
- Ensure all new logic is extracted into `CCAHooks` rather than being placed directly in Mixins.
- Maintain **Exhaustive Documentation** for any new GameRules added to `CombatRules`.
