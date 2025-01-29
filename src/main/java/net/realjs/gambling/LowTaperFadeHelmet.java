package net.realjs.gambling;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.server.command.ServerCommandSource;

public class LowTaperFadeHelmet extends ArmorItem {
    private static final float HELMET_SCALE = 3.0f;
    private static final float DEFAULT_SCALE = 1.0f;

    public LowTaperFadeHelmet(RegistryEntry<ArmorMaterial> material, Settings settings) {
        super(material, Type.HELMET, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, net.minecraft.entity.Entity entity, int slot, boolean selected) {
        if (!world.isClient && entity instanceof ServerPlayerEntity player) {
            boolean isWearingHelmet = player.getEquippedStack(EquipmentSlot.HEAD).isOf(this);
            float targetScale = isWearingHelmet ? HELMET_SCALE : DEFAULT_SCALE;

            if (shouldUpdateScale(player, targetScale)) {
                executeScaleCommand(player, targetScale);
                player.calculateDimensions(); // Force hitbox update
            }
        }
    }

    private boolean shouldUpdateScale(ServerPlayerEntity player, float targetScale) {
        return player.getScale() != targetScale;
    }

    private void executeScaleCommand(ServerPlayerEntity player, float scale) {
        if (player.getServer() != null) {
            String command = String.format("/attribute @s minecraft:generic.scale base set %.1f", scale);

            // Get the correct server world
            ServerWorld serverWorld = (ServerWorld) player.getWorld();

            // Create the command source
            ServerCommandSource source = new ServerCommandSource(
                    player.getServer(),
                    player.getPos(),
                    player.getRotationClient(),
                    serverWorld,
                    4,
                    player.getName().getString(),
                    player.getName(),
                    player.getServer(),
                    player
            );

            // Execute the command
            player.getServer().getCommandManager().executeWithPrefix(source, command);

            System.out.println("Executed command: " + command);
        }
    }
}