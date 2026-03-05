package net.instantgratification.combatcooldownadjuster.mixin;

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.combatcooldownadjuster.CombatCooldownRules;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Verified against: LivingEntity.java (26.1 Snapshot 10)
// Path: E:\Minecraft Project\Minecraft Decomplide code for reference only\26.1 Snapshot 10 Decompiled\Client\src_decompiled\net\minecraft\world\entity\LivingEntity.java

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "getCurrentSwingDuration", at = @At("HEAD"), cancellable = true)
    private void combatcooldownadjuster$overrideSwingDuration(CallbackInfoReturnable<Integer> cir) {
        if ((Object) this instanceof Player player) {
            int configMs = DynamicGameRuleManager.getInt(player.level(), CombatCooldownRules.ATTACK_COOLDOWN_MS);
            if (configMs > 0) {
                // Convert ms to ticks. Minimum 1 tick so animation plays.
                int ticks = Math.max(1, configMs / 50);
                cir.setReturnValue(ticks);
            } else if (configMs == 0) {
                // If 0, snap the animation instantly
                cir.setReturnValue(1);
            }
        }
    }
}
