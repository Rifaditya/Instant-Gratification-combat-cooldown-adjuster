package net.instantgratification.combatcooldownadjuster;

// Verified against: ModInitializer.java (Fabric API)

import net.instantgratification.combatcooldownadjuster.registry.CombatRules;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombatCooldownAdjuster implements ModInitializer {
    public static final String MOD_ID = "combat-cooldown-adjuster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Instant Gratification: Combat Cooldown Adjuster Initialized");
        CombatRules.register();
    }
}
