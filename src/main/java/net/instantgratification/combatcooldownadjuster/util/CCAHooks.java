package net.instantgratification.combatcooldownadjuster.util;

// Verified against: Player.java (Minecraft 26.1.2)

import net.instantgratification.combatcooldownadjuster.registry.CombatRules;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CCAHooks {

    public static final TagKey<Item> SPEARS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", "spears"));

    public static int getCooldownTicks(Player player, ItemStack stack) {
        if (stack.isEmpty()) return CombatRules.getInt(player.level(), CombatRules.GENERIC_TICKS);

        if (stack.is(ItemTags.SWORDS)) return CombatRules.getInt(player.level(), CombatRules.SWORD_TICKS);
        if (stack.is(ItemTags.AXES)) return CombatRules.getInt(player.level(), CombatRules.AXE_TICKS);
        if (stack.is(ItemTags.PICKAXES)) return CombatRules.getInt(player.level(), CombatRules.PICKAXE_TICKS);
        if (stack.is(ItemTags.SHOVELS)) return CombatRules.getInt(player.level(), CombatRules.SHOVEL_TICKS);
        if (stack.is(ItemTags.HOES)) return CombatRules.getInt(player.level(), CombatRules.HOE_TICKS);
        if (stack.is(SPEARS)) return CombatRules.getInt(player.level(), CombatRules.SPEAR_TICKS);

        return CombatRules.getInt(player.level(), CombatRules.GENERIC_TICKS);
    }

    public static void applyCombatJuice(Player player, Entity target) {
        if (!CombatRules.getBoolean(player.level(), CombatRules.ENABLE_JUICE)) return;

        float attackStrength = player.getAttackStrengthScale(0.5f);
        if (attackStrength > 0.8f) {
            if (player.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.CRIT, target.getX(), target.getY(0.5), target.getZ(), 10, 0.1, 0.1, 0.1, 0.1);
                serverLevel.sendParticles(ParticleTypes.ENCHANTED_HIT, target.getX(), target.getY(0.5), target.getZ(), 5, 0.1, 0.1, 0.1, 0.1);
            }
            
            // Pitch shifting logic: Higher pitch for high-charge hits
            float pitch = 1.0f + (attackStrength - 0.8f) * 2.0f; // Range: 1.0 to 1.4 approx
            player.level().playSound(null, target.getX(), target.getY(), target.getZ(), 
                SoundEvents.PLAYER_ATTACK_STRONG, SoundSource.PLAYERS, 1.0f, pitch);
        }
    }
}
