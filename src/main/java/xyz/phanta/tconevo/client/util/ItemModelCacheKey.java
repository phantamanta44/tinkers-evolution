package xyz.phanta.tconevo.client.util;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.Objects;

// adapted from tinkers' construct's BakedToolModel$CacheKey, which is under MIT
// why is that class not visible???
public class ItemModelCacheKey {

    private final IBakedModel model;
    @Nullable
    private final ItemStack stack;

    public ItemModelCacheKey(IBakedModel model, @Nullable ItemStack stack) {
        this.model = model;
        this.stack = stack;
    }

    public IBakedModel getModel() {
        return model;
    }

    @Nullable
    public ItemStack getStack() {
        return stack;
    }

    @Nullable
    public NBTTagCompound getStackData() {
        return stack != null ? stack.getTagCompound() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemModelCacheKey)) {
            return false;
        }
        ItemModelCacheKey otherKey = (ItemModelCacheKey)o;
        return model == otherKey.model && Objects.equals(getStackData(), otherKey.getStackData());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(model) * 31 + Objects.hashCode(getStackData());
    }

}
