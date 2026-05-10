# 📜 Version History: Combat Cooldown Adjuster

## [1.0.1+build.3] - 2026-05-10
### Fixed
- **Pack Metadata Validator**: Resolved `JsonParseException` on Minecraft 26.1.2+ by adding mandatory `min_format` and `max_format` fields to `pack.mcmeta`.

## [1.0.0] - 2026-05-10
### Added
- **Categorical Tick Overrides**: Exact tick delay control for Swords, Axes, Pickaxes, Shovels, Hoes, and Spears.
- **Swap Agility**: Bypass the attack cooldown reset when switching items in the hotbar.
- **Combat Juice**: Multi-sensory feedback with particles and pitch-shifted audio for high-charge hits.
- **Dynamic GameRules**: Full integration with DasikLibrary for in-game configuration.

### Concept Coverage ⭐
- Features implemented: 5/5 (100%)


## [1.0.0+build.2] - 2026-05-10
### Added
- **Categorical Tick System**: Support for Sword, Axe, Pickaxe, Shovel, Hoe, Spear, and Generic categories.
- **Swap Agility**: Toggleable bypass for item-swap attack resets.
- **Combat Juice**: High-charge particles and pitch-shifted audio feedback.
- **Sovereign Documentation**: Complete Doc suite for Modrinth, CurseForge, and Developers.

### Fixed
- Missing `refmap` in mixin configuration causing production crashes.
- Broadened Minecraft dependency range to `>=26.1.2` for future-proofing.

## [1.0.0+build.1] - 2026-05-10
- Initial implementation of tick-based categorical overrides.
- Basic Mixin structure for `Player`.
- Initial registry for `CombatRules`.
