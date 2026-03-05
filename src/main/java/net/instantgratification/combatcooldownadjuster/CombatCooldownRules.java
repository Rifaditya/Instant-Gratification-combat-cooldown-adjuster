package net.instantgratification.combatcooldownadjuster;

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class CombatCooldownRules {
    public static GameRule<Integer> ATTACK_COOLDOWN_MS;
    public static GameRule<Integer> ITEM_SWAP_COOLDOWN_MS;

    public static void init() {
        // Registering with the DasikLibrary DynamicGameRuleManager
        ATTACK_COOLDOWN_MS = DynamicGameRuleManager.integerRule("combatcooldownadjuster:attack_cooldown_ms", GameRuleCategory.PLAYER, 500)
            .name("Attack Cooldown (ms)")
            .description("Sets the precise millisecond delay before a weapon swing reaches full strength. When true, bypasses weapon attributes.")
            .min(0)
            .register();

        ITEM_SWAP_COOLDOWN_MS = DynamicGameRuleManager.integerRule("combatcooldownadjuster:item_swap_cooldown_ms", GameRuleCategory.PLAYER, 500)
            .name("Item Swap Cooldown (ms)")
            .description("Sets the delay in milliseconds applied when swapping an item before you can attack at full strength.")
            .min(0)
            .register();
    }
}
