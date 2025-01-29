package net.realjs.gambling;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class BalkanRagePotionItem extends PotionItem {
    public BalkanRagePotionItem(Settings settings) {
        super(settings);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32; // Drinking time (same as normal potions)
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            // Apply the Balkan Rage effects
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 600, 2));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 600, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 600, 0));
        }

        // Handle empty bottle and stack size
        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            stack.decrement(1);
            return new ItemStack(Items.GLASS_BOTTLE);
        }

        return stack;
    }
}