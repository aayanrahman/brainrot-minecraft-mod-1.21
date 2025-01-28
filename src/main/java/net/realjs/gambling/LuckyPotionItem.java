package net.realjs.gambling;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.Random;

public class LuckyPotionItem extends PotionItem {  // Extending PotionItem ensures animation
    public LuckyPotionItem(Settings settings) {
        super(settings);
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32; // Drinking time (same as normal potions)
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK; // Ensure drinking animation works
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient) {
            Random random = new Random();
            StatusEffectInstance effect;

            // Randomized status effects
            StatusEffectInstance[] effects = {
                    new StatusEffectInstance(StatusEffects.SPEED, 400, 1),
                    new StatusEffectInstance(StatusEffects.STRENGTH, 400, 1),
                    new StatusEffectInstance(StatusEffects.POISON, 400, 0),
                    new StatusEffectInstance(StatusEffects.BLINDNESS, 400, 0)
            };

            effect = effects[random.nextInt(effects.length)];
            user.addStatusEffect(effect);
            System.out.println("[Lucky Potion] Applied effect: " + effect.getEffectType().toString());
        }

        if (user instanceof PlayerEntity player && !player.getAbilities().creativeMode) {
            stack.decrement(1);
            return new ItemStack(Items.GLASS_BOTTLE); // Returns glass bottle
        }

        return stack;
    }
}
