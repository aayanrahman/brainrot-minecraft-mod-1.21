package net.realjs.gambling;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item LUCKY_POTION = registerItem("lucky_potion",
            new LuckyPotionItem(new Item.Settings().maxCount(16)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of("gambling-potions", name), item);
    }

    public static void registerItems() {
        System.out.println("[Gambling Potions Mod] Registering Mod Items...");

        // Add item to the Creative tab (Minecraft 1.21)
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(LUCKY_POTION);
        });
    }
}
