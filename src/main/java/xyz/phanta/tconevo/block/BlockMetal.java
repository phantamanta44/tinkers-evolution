package xyz.phanta.tconevo.block;

import io.github.phantamanta44.libnine.block.L9BlockStated;
import io.github.phantamanta44.libnine.client.model.ParameterizedItemModel;
import io.github.phantamanta44.libnine.item.L9ItemBlock;
import io.github.phantamanta44.libnine.item.L9ItemBlockStated;
import io.github.phantamanta44.libnine.util.collection.Accrue;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.item.ItemMetal;

public class BlockMetal extends L9BlockStated {

    public static final IProperty<ItemMetal.Type> TYPE = PropertyEnum.create("type", ItemMetal.Type.class);

    public BlockMetal() {
        super(NameConst.BLOCK_METAL, Material.IRON);
        setHardness(5F);
        setResistance(10F);
    }

    @Override
    protected void accrueProperties(Accrue<IProperty<?>> props) {
        props.accept(TYPE);
    }

    @Override
    protected L9ItemBlock initItemBlock() {
        return new ItemBlockMetal(this);
    }

    @Override
    public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
        return true;
    }

    public ItemMetal.Type getTypeFromMeta(int meta) {
        return (meta >= 0 && meta < ItemMetal.Type.VALUES.length) ? ItemMetal.Type.VALUES[meta] : ItemMetal.Type.WYVERN_METAL;
    }

    public ItemMetal.Type getTypeFromStack(ItemStack stack) {
        return getTypeFromMeta(stack.getMetadata());
    }

    public ItemStack newStack(ItemMetal.Type type, int count) {
        return new ItemStack(getItemBlock(), count, type.ordinal());
    }

    public static class ItemBlockMetal extends L9ItemBlockStated implements ParameterizedItemModel.IParamaterized {

        private final BlockMetal block;

        ItemBlockMetal(BlockMetal block) {
            super(block);
            this.block = block;
        }

        @Override
        public void getModelMutations(ItemStack stack, ParameterizedItemModel.Mutation m) {
            m.mutate("type", block.getTypeFromStack(stack).name());
        }

    }

}
