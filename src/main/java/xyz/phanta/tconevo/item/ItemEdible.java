package xyz.phanta.tconevo.item;

import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemSubs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoItems;

public class ItemEdible extends L9ItemSubs implements ParameterizedItemModel.IParamaterized {

    public static final DamageSource DMG_MEAT_INGOT = new DamageSource("meat_ingot")
            .setDamageBypassesArmor().setDamageIsAbsolute();

    public ItemEdible() {
        super(NameConst.ITEM_EDIBLE, Type.VALUES.length);
    }

    @Override
    public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
        m.mutate("type", Type.getForStack(stack).name());
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.EAT;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.canEat(Type.getForStack(stack).alwaysEdible)) {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return super.onItemRightClick(world, player, hand);
    }

    // adapted from minecraft's ItemFood#onItemUseFinish
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase eater) {
        if (eater instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)eater;
            Type type = Type.getForStack(stack);
            player.getFoodStats().addStats(type.foodPoints, type.satMultiplier);
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP,
                    SoundCategory.PLAYERS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
            type.onEaten(stack, world, player);
            //noinspection ConstantConditions
            player.addStat(StatList.getObjectUseStats(this));
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)player, stack);
            }
        }
        stack.shrink(1);
        return stack;
    }

    public enum Type {

        MEAT_INGOT_RAW(3, 0.6667F, false) {
            @Override
            public void onEaten(ItemStack stack, World world, EntityPlayer player) {
                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_BREAK,
                        SoundCategory.PLAYERS, 1F, 1.5F + world.rand.nextFloat() * 0.25F);
                player.attackEntityFrom(DMG_MEAT_INGOT, 1F);
            }
        },
        MEAT_INGOT_COOKED(10, 1.2F, false) {
            @Override
            public void onEaten(ItemStack stack, World world, EntityPlayer player) {
                MEAT_INGOT_RAW.onEaten(stack, world, player);
            }
        };

        public static final Type[] VALUES = values();

        public static Type getForMeta(int meta) {
            return (meta >= 0 && meta < VALUES.length) ? VALUES[meta] : MEAT_INGOT_RAW;
        }

        public static Type getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        public final int foodPoints;
        public final float satMultiplier;
        public final boolean alwaysEdible;

        Type(int foodPoints, float satMultiplier, boolean alwaysEdible) {
            this.foodPoints = foodPoints;
            this.satMultiplier = satMultiplier;
            this.alwaysEdible = alwaysEdible;
        }

        public void onEaten(ItemStack stack, World world, EntityPlayer player) {
            // NO-OP
        }

        public int getMeta() {
            return ordinal();
        }

        public ItemStack newStack(int count) {
            return new ItemStack(TconEvoItems.EDIBLE, count, getMeta());
        }

    }

}
