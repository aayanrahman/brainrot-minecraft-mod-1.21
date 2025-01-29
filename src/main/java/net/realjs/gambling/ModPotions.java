package net.realjs.gambling;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModPotions {
    // Register the potions themselves
    public static final Potion LUCKY_POTION = new Potion(
            new StatusEffectInstance(StatusEffects.SPEED, 400, 1),
            new StatusEffectInstance(StatusEffects.STRENGTH, 400, 1)
    );

    public static final Potion BALKAN_RAGE_POTION = new Potion(
            new StatusEffectInstance(StatusEffects.STRENGTH, 600, 2),
            new StatusEffectInstance(StatusEffects.SPEED, 600, 1),
            new StatusEffectInstance(StatusEffects.BLINDNESS, 600, 0)
    );

    public static void registerPotions() {
        Registry.register(Registries.POTION, Identifier.of(GamblingPotions.MOD_ID, "lucky_potion"), LUCKY_POTION);
        Registry.register(Registries.POTION, Identifier.of(GamblingPotions.MOD_ID, "balkan_rage_potion"), BALKAN_RAGE_POTION);

        System.out.println("[Gambling Potions Mod] Registering Potions...");
    }
}