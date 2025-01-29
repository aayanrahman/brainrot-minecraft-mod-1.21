package net.realjs.gambling;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item LUCKY_POTION = registerItem("lucky_potion",
            new LuckyPotionItem(new Item.Settings().maxCount(16)));

    public static final Item BALKAN_RAGE_POTION = registerItem("balkan_rage_potion",
            new BalkanRagePotionItem(new Item.Settings().maxCount(16)));  // Changed this line to use BalkanRagePotionItem

    public static final Item LOW_TAPER_FADE_HELMET = registerItem("low_taper_fade_helmet",
            new LowTaperFadeHelmet(ArmorMaterials.NETHERITE, new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of("gambling-potions", name), item);
    }

    public static void registerItems() {
        System.out.println("[Gambling Potions Mod] Registering Mod Items...");

        // Register all items to creative mode inventory
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(LUCKY_POTION);
            entries.add(BALKAN_RAGE_POTION);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(LOW_TAPER_FADE_HELMET);
        });
    }
}