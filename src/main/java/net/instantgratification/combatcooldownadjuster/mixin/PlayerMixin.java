package net.instantgratification.combatcooldownadjuster.mixin;

import net.dasik.social.api.gamerule.DynamicGameRuleManager;
import net.instantgratification.combatcooldownadjuster.CombatCooldownRules;
import net.minecraft.util.Util;
import net.minecraft.world.entity.player.Player;

// Verified against: Player.java (26.1 Snapshot 10)
// Path: E:\Minecraft Project\Minecraft Decomplide code for reference only\26.1 Snapshot 10 Decompiled\Client\src_decompiled\net\minecraft\world\entity\player\Player.java
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Shadow public abstract float getCurrentItemAttackStrengthDelay();

    private long combatcooldownadjuster$lastAttackTimeMs = Util.getMillis();
    private long combatcooldownadjuster$lastItemSwapTimeMs = Util.getMillis();

    @Inject(method = "getAttackStrengthScale", at = @At("HEAD"), cancellable = true)
    private void combatcooldownadjuster$overrideAttackStrengthScale(float adjustTicks, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        int configMs = DynamicGameRuleManager.getInt(player.level(), CombatCooldownRules.ATTACK_COOLDOWN_MS);
        
        long currentTime = Util.getMillis();
        long attackElapsed = currentTime - combatcooldownadjuster$lastAttackTimeMs;

        if (configMs == 0) {
            cir.setReturnValue(1.0F);
            return;
        }

        float attackProgress = Math.min(1.0F, (float) attackElapsed / configMs);
        cir.setReturnValue(attackProgress);
    }

    @Inject(method = "getCurrentItemAttackStrengthDelay", at = @At("HEAD"), cancellable = true)
    private void combatcooldownadjuster$overrideAttackStrengthDelay(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        int configMs = DynamicGameRuleManager.getInt(player.level(), CombatCooldownRules.ATTACK_COOLDOWN_MS);
        // Convert MS to ticks (float) for vanilla methods that expect a tick-based delay
        cir.setReturnValue((float)configMs / 50.0f);
    }

    @Inject(method = "cannotAttackWithItem", at = @At("HEAD"), cancellable = true)
    private void combatcooldownadjuster$overrideCannotAttackWithItem(net.minecraft.world.item.ItemStack itemStack, int tolerance, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        float requiredStrength = itemStack.getOrDefault(net.minecraft.core.component.DataComponents.MINIMUM_ATTACK_CHARGE, 0.0F);
        if (requiredStrength <= 0.0F) {
            cir.setReturnValue(false);
            return;
        }

        int configMs = DynamicGameRuleManager.getInt(player.level(), CombatCooldownRules.ATTACK_COOLDOWN_MS);
        if (configMs == 0) {
            cir.setReturnValue(false);
            return;
        }

        long currentTime = Util.getMillis();
        // Add tolerance (in ticks converted to ms) for optimistic checks
        long attackElapsed = (currentTime - combatcooldownadjuster$lastAttackTimeMs) + (tolerance * 50L);
        float attackProgress = Math.min(1.0F, (float) attackElapsed / configMs);

        cir.setReturnValue(attackProgress < requiredStrength);
    }
    
    @Inject(method = "getItemSwapScale", at = @At("HEAD"), cancellable = true)
    private void combatcooldownadjuster$overrideItemSwapScale(float adjustTicks, CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        int swapMs = DynamicGameRuleManager.getInt(player.level(), CombatCooldownRules.ITEM_SWAP_COOLDOWN_MS);
        
        long currentTime = Util.getMillis();
        long swapElapsed = currentTime - combatcooldownadjuster$lastItemSwapTimeMs;

        if (swapMs == 0) {
            cir.setReturnValue(1.0F);
            return;
        }

        float swapProgress = Math.min(1.0F, (float) swapElapsed / swapMs);
        cir.setReturnValue(swapProgress);
    }

    @Inject(method = "resetAttackStrengthTicker", at = @At("HEAD"))
    private void combatcooldownadjuster$onResetAttackStrength(CallbackInfo ci) {
        this.combatcooldownadjuster$lastAttackTimeMs = Util.getMillis();
        this.combatcooldownadjuster$lastItemSwapTimeMs = Util.getMillis();
    }
    
    @Inject(method = "resetOnlyAttackStrengthTicker", at = @At("HEAD"))
    private void combatcooldownadjuster$onResetOnlyAttackStrength(CallbackInfo ci) {
        this.combatcooldownadjuster$lastAttackTimeMs = Util.getMillis();
    }
}
