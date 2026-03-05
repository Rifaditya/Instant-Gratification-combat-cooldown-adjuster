package net.instantgratification.combatcooldownadjuster;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombatCooldownAdjusterFabric implements ModInitializer {
    public static final String MOD_ID = "combatcooldownadjuster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        CombatCooldownRules.init();
        LOGGER.info("Instant Gratification: Combat Cooldown Adjuster Initialized");
    }
}
