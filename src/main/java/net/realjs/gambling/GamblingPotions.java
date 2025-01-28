package net.realjs.gambling;

import net.fabricmc.api.ModInitializer;

public class GamblingPotions implements ModInitializer {
	public static final String MOD_ID = "gambling-potions";

	@Override
	public void onInitialize() {
		System.out.println("[Gambling Potions Mod] Initializing...");
		ModItems.registerItems();
		ModPotions.registerPotions();
		OhioMode.register(); // Registers Ohio Mode
		FanumTax.register(); // Registers Fanum Tax
		System.out.println("[Gambling Potions Mod] Initialization Complete!");
	}
}
