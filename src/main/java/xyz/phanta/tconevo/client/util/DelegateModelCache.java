package xyz.phanta.tconevo.client.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class DelegateModelCache<K, M extends DelegatingModel<K, M>> extends ItemOverrideList {

    private final M rootModel;
    private final Cache<K, M> delegateCache = CacheBuilder.newBuilder()
            .maximumSize(1000) // borrowed parameters from tinkers' construct's BakedToolModel#ToolItemOverrideList
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();
    // gross, but necessary for weird avaritia rendering
    @Nullable
    private World cachedRenderWorld = null;
    @Nullable
    private EntityLivingBase cachedRenderEntity = null;
    @Nullable
    private BlockPos cachedRenderEntityPos = null;

    public DelegateModelCache(M rootModel) {
        this.rootModel = rootModel;
    }

    public M getRootModel() {
        return rootModel;
    }

    protected abstract K extractDelegateKey(IBakedModel parent, ItemStack stack,
                                            @Nullable World world, @Nullable EntityLivingBase entity);

    @Override
    public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack,
                                       @Nullable World world, @Nullable EntityLivingBase entity) {
        cachedRenderWorld = world;
        cachedRenderEntity = entity;
        cachedRenderEntityPos = entity != null ? entity.getPosition() : null;
        return getWrappedDelegate(extractDelegateKey(rootModel.getParentModel(), stack, world, entity));
    }

    public IBakedModel getWrappedDelegate(K delegateKey) {
        try {
            return delegateCache.get(delegateKey, () -> rootModel.wrapDelegate(delegateKey));
        } catch (ExecutionException e) {
            throw new IllegalStateException("Delegate model override cache lookup failed!", e.getCause());
        }
    }

    @Nullable
    public World getCachedWorld() {
        return cachedRenderWorld;
    }

    @Nullable
    public EntityLivingBase getCachedEntity() {
        return cachedRenderEntity;
    }

    @Nullable
    public BlockPos getCachedEntityPos() {
        return cachedRenderEntityPos;
    }

    public static class Overriding<M extends DelegatingModel<IBakedModel, M>> extends DelegateModelCache<IBakedModel, M> {

        public Overriding(M rootModel) {
            super(rootModel);
        }

        @Override
        protected IBakedModel extractDelegateKey(IBakedModel parent, ItemStack stack,
                                                 @Nullable World world, @Nullable EntityLivingBase entity) {
            return parent.getOverrides().handleItemState(parent, stack, world, entity);
        }

    }

}
