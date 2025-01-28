package net.realjs.gambling;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

import java.util.Random;

public class OhioMode {
    private static boolean ohioModeActive = false;
    private static int ohioTimer = 0;
    private static final int OHIO_DURATION = 24000; // 1 day

    public static void register() {
        // Register a command to force Ohio Mode
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("start_ohio")
                    .executes(context -> {
                        startOhioMode((ServerWorld) context.getSource().getWorld());
                        return 1;
                    })
            );
        });

        // Apply Ohio Mode effects every tick
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (world instanceof ServerWorld serverWorld) {
                if (ohioModeActive) {
                    ohioTimer--;
                    if (ohioTimer <= 0) {
                        stopOhioMode(serverWorld);
                    }

                    // Apply Speed II to all living entities
                    for (Entity entity : serverWorld.iterateEntities()) {
                        if (entity instanceof ServerPlayerEntity player) {
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 1, true, false));
                        }
                    }

                    // Accelerate time (ticks per day)
                    serverWorld.setTimeOfDay(serverWorld.getTimeOfDay() + 2);
                }
            }
        });
    }

    private static void startOhioMode(ServerWorld world) {
        if (!ohioModeActive) {
            ohioModeActive = true;
            ohioTimer = OHIO_DURATION;
            for (ServerPlayerEntity player : world.getPlayers()) {
                player.sendMessage(Text.literal("⚡ OHIO MODE ACTIVATED! Everything is now 2x faster!"), false);
            }
            System.out.println("[Ohio Mode] Ohio Mode Activated!");
        }
    }

    private static void stopOhioMode(ServerWorld world) {
        ohioModeActive = false;
        for (ServerPlayerEntity player : world.getPlayers()) {
            player.sendMessage(Text.literal("🛑 Ohio Mode Deactivated!"), false);
        }
        System.out.println("[Ohio Mode] Ohio Mode Deactivated!");
    }
}
