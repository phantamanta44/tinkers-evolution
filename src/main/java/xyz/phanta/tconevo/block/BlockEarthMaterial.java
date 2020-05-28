package xyz.phanta.tconevo.block;

import io.github.phantamanta44.libnine.block.L9BlockStated;
import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemBlock;
import io.github.phantamanta44.libnine.item.L9ItemBlockStated;
import io.github.phantamanta44.libnine.util.collection.Accrue;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import slimeknights.tconstruct.world.TinkerWorld;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoBlocks;

public class BlockEarthMaterial extends L9BlockStated {

    public static final IProperty<Type> TYPE = PropertyEnum.create("type", Type.class);

    public BlockEarthMaterial() {
        super(NameConst.BLOCK_EARTH_MATERIAL, Material.SAND);
        setHardness(3F);
        setDefaultSlipperiness(0.8F);
        setSoundType(SoundType.SAND);
        setHarvestLevel("shovel", -1);
    }

    @Override
    protected void accrueProperties(Accrue<IProperty<?>> props) {
        props.accept(TYPE);
    }

    @Override
    protected L9ItemBlock initItemBlock() {
        return new ItemBlockEarthMaterial(this);
    }

    @Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity) {
        // add a switch here later, once there are more types
        entity.motionX *= 0.4;
        entity.motionZ *= 0.4;
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1));
        }
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        // add a switch here later, once there are more types
        return plantable.getPlantType(world, pos) == TinkerWorld.slimePlantType;
    }

    public enum Type implements IStringSerializable {

        PINK_SLIMY_MUD;

        public static final Type[] VALUES = values();

        public static Type getForMeta(int meta) {
            return (meta >= 0 && meta < VALUES.length) ? VALUES[meta] : PINK_SLIMY_MUD;
        }

        public static Type getForStack(ItemStack stack) {
            return getForMeta(stack.getMetadata());
        }

        @Override
        public String getName() {
            return name().toLowerCase();
        }

        public int getMeta() {
            return ordinal();
        }

        public ItemStack newStack(int count) {
            return new ItemStack(TconEvoBlocks.EARTH_MATERIAL, count, getMeta());
        }

    }

    private static class ItemBlockEarthMaterial extends L9ItemBlockStated implements ParameterizedItemModel.IParamaterized {

        ItemBlockEarthMaterial(BlockEarthMaterial block) {
            super(block);
        }

        @Override
        public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
            m.mutate("type", Type.getForStack(stack).name());
        }

    }

}
