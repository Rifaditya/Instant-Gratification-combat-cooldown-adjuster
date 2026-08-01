# [PAT-20260510-001] Categorical Combat Cooldowns
**Trigger**: When implementing weapon speed modifications in Minecraft 26.x.
**Template**: 
- Extract logic to `CCAHooks.java`.
- Use `ItemTags` for categorization.
- Return exact ticks (float) in `getCurrentItemAttackStrengthDelay`.
**Constraints**: 
- Must use `GameRules` for per-category configuration.
- Must support generic fallback.

# [PAT-20260510-002] Combat Juice (High-Charge Feedback)
**Trigger**: To improve "Instant Gratification" feel during combat.
**Template**:
- Inject at `Player#attack` HEAD.
- Check `getAttackStrengthScale(0.5f) > 0.8f`.
- Play `PLAYER_ATTACK_STRONG` with pitch shifting based on charge.
- Spawn `CRIT` and `ENCHANTED_HIT` particles on target.
**Constraints**: 
- Must be toggleable via GameRule `ig:enable_combat_juice`.

# [PAT-20260510-003] Swap Agility (No-Reset Item Swapping)
**Trigger**: To remove friction when switching between hotbar weapons.
**Template**:
- Inject at `Player#resetAttackStrengthTicker` HEAD.
- Cancel if Rule `ig:prevent_item_swap_cooldown` is true.
**Constraints**: 
- Ensure `onAttack` calls a *different* method (e.g., `resetOnlyAttackStrengthTicker`) that isn't canceled, so normal attacks still reset the cooldown.
