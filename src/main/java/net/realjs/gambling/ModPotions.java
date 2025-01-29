package net.realjs.gambling;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModPotions {
    public static final RegistryEntry<Potion> LUCKY_POTION = registerPotion("lucky_potion",
            new Potion(
                    new StatusEffectInstance(StatusEffects.SPEED, 600, 1),
                    new StatusEffectInstance(StatusEffects.STRENGTH, 600, 1)
            )
    );

    public static final RegistryEntry<Potion> BALKAN_RAGE_POTION = registerPotion("balkan_rage_potion",
            new Potion(
                    new StatusEffectInstance(StatusEffects.STRENGTH, 600, 2),
                    new StatusEffectInstance(StatusEffects.SPEED, 600, 1),
                    new StatusEffectInstance(StatusEffects.BLINDNESS, 600, 0)
            )
    );

    private static RegistryEntry<Potion> registerPotion(String name, Potion potion) {
        return Registry.registerReference(
                Registries.POTION,
                Identifier.of(GamblingPotions.MOD_ID, name),
                potion
        );
    }

    public static void registerPotions() {
        System.out.println("[Gambling Potions Mod] Registering Potions...");
    }
}
