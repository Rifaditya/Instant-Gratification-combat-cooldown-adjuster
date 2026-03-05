# Audit Helper for Moderators

This document provides a technical overview for platform moderators (CurseForge/Modrinth) to quickly verify the mod's safety and architectural integrity.

## 🛡️ Safety & Security
- **No Network Requests**: This mod does not communicate with external servers.
- **No Reflection**: All Minecraft interactions are handled via Sponge Mixins.
- **No Shading**: All dependencies (Fabric API, DasikLibrary) are handled as external mod requirements.

## 🧩 Key Classes to Review
- **`CombatCooldownRules.java`**: Standard GameRule registration using the DasikLibrary API.
- **`PlayerMixin.java`**: The core logic override. Intercepts `getAttackStrengthScale` to implement millisecond timing.
- **`LivingEntityMixin.java`**: Visual sync logic. Overrides `getCurrentSwingDuration` for animation parity.

## 🏗️ Technical Context
- **Target**: Minecraft 26.1 Snapshot 10.
- **Java Version**: 25.
- **Mappings**: Mojang Mappings.

---
*Verified for Snapshot 26.1 Snapshot 10.*
