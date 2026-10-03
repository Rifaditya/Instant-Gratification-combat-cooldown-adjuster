package net.instantgratification.combatcooldownadjuster.registry;

// Verified against: DurabilityRules.java (DasikLibrary Pattern)

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class CombatRules {

    public static final GameRuleCategory COMBAT_COOLDOWN = GameRuleCategory
            .register(Identifier.fromNamespaceAndPath("combat-cooldown-adjuster", "combat_cooldown"));

    public static GameRule<Integer> SWORD_TICKS;
    public static GameRule<Integer> AXE_TICKS;
    public static GameRule<Integer> PICKAXE_TICKS;
    public static GameRule<Integer> SHOVEL_TICKS;
    public static GameRule<Integer> HOE_TICKS;
    public static GameRule<Integer> SPEAR_TICKS;
    public static GameRule<Integer> GENERIC_TICKS;

    public static GameRule<Boolean> PREVENT_SWAP_RESET;
    public static GameRule<Boolean> ENABLE_JUICE;

    public static int getInt(Level level, GameRule<Integer> rule) {
        if (level.isClientSide()) return 0;
        return DynamicGameRuleManager.getInt((ServerLevel) level, rule);
    }

    public static boolean getBoolean(Level level, GameRule<Boolean> rule) {
        if (level.isClientSide()) return false;
        return DynamicGameRuleManager.getBoolean((ServerLevel) level, rule);
    }

    public static void register() {
        SWORD_TICKS = DynamicGameRuleManager.registerInteger("ig:sword_cooldown_ticks", COMBAT_COOLDOWN, 4);
        AXE_TICKS = DynamicGameRuleManager.registerInteger("ig:axe_cooldown_ticks", COMBAT_COOLDOWN, 8);
        PICKAXE_TICKS = DynamicGameRuleManager.registerInteger("ig:pickaxe_cooldown_ticks", COMBAT_COOLDOWN, 4);
        SHOVEL_TICKS = DynamicGameRuleManager.registerInteger("ig:shovel_cooldown_ticks", COMBAT_COOLDOWN, 2);
        HOE_TICKS = DynamicGameRuleManager.registerInteger("ig:hoe_cooldown_ticks", COMBAT_COOLDOWN, 1);
        SPEAR_TICKS = DynamicGameRuleManager.registerInteger("ig:spear_cooldown_ticks", COMBAT_COOLDOWN, 6);
        GENERIC_TICKS = DynamicGameRuleManager.registerInteger("ig:generic_cooldown_ticks", COMBAT_COOLDOWN, 4);

        PREVENT_SWAP_RESET = DynamicGameRuleManager.registerBoolean("ig:prevent_item_swap_cooldown", COMBAT_COOLDOWN, true);
        ENABLE_JUICE = DynamicGameRuleManager.registerBoolean("ig:enable_combat_juice", COMBAT_COOLDOWN, true);
    }
}
