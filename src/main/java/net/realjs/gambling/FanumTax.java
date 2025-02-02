package net.realjs.gambling;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FanumTax {
    private static int taxTimer = 6000; // 30 seconds instead of 5-10 minutes

    public static void register() {
        // Register a command to trigger Fanum Tax instantly
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("fanum_tax")
                    .executes(context -> {
                        applyTax((ServerWorld) context.getSource().getWorld()); // Cast World to ServerWorld
                        return 1;
                    })
            );
        });

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world instanceof ServerWorld serverWorld) { // Ensure it's ServerWorld
                taxTimer--;
                if (taxTimer <= 0) {
                    taxTimer = 6000; // Reset timer to 30 seconds
                    applyTax(serverWorld);
                }
            }
        });
    }

    private static void applyTax(ServerWorld world) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            DefaultedList<ItemStack> inventory = player.getInventory().main;

            // Get only valid (non-empty) items
            List<ItemStack> validItems = inventory.stream()
                    .filter(stack -> stack != null && !stack.isEmpty() && stack.getItem() != net.minecraft.item.Items.AIR) // Double-check Air
                    .collect(Collectors.toList());

            if (validItems.isEmpty()) {
                player.sendMessage(Text.literal("Fanum checked your inventory... You're broke LMAO!"), false);
                return; // No items to take
            }

            // Shuffle and select a valid item
            Collections.shuffle(validItems);
            ItemStack stack = validItems.get(0);

            // Extra check that we didn't pick "Air"
            if (stack.isEmpty() || stack.getItem() == net.minecraft.item.Items.AIR) {
                player.sendMessage(Text.literal("Fanum checked your inventory but took nothing this time."), false);
                System.out.println("[Fanum Tax] Skipped tax because no valid item was found.");
                return;
            }

            // Take random amount (1-5) from the stack
            int amountToRemove = Math.min(stack.getCount(), 1 + new Random().nextInt(5));
            String itemName = stack.getItem().getName().getString(); // Proper item name

            stack.decrement(amountToRemove);

            player.sendMessage(Text.literal("Fanum took " + amountToRemove + "x " + itemName + ". Pay your taxes."), false);
            System.out.println("[Fanum Tax] Took " + amountToRemove + "x " + itemName);
        }
    }
}
