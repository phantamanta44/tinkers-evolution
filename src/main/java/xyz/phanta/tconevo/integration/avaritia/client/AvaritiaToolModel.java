package xyz.phanta.tconevo.integration.avaritia.client;

import codechicken.lib.render.item.IItemRenderer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import morph.avaritia.client.render.item.WrappedItemRenderer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.mantle.client.model.BakedWrapper;
import slimeknights.tconstruct.library.client.model.*;
import slimeknights.tconstruct.library.client.model.format.AmmoPosition;
import slimeknights.tconstruct.library.client.model.format.ToolModelOverride;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.client.util.*;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class AvaritiaToolModel extends ToolModel {

    public AvaritiaToolModel(ImmutableList<ResourceLocation> defaultTextures,
                             List<MaterialModel> parts,
                             List<MaterialModel> brokenPartBlocks,
                             Float[] layerRotations,
                             ModifierModel modifiers,
                             ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                             ImmutableList<ToolModelOverride> overrides, AmmoPosition ammoPosition) {
        super(defaultTextures, parts, brokenPartBlocks, layerRotations, modifiers, transforms, overrides, ammoPosition);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        IBakedModel delegate = super.bake(state, format, bakedTextureGetter);
        return new BakedAvaritiaToolModel(delegate, delegate instanceof BakedToolModel
                ? new SimpleModelState(TconReflectClient.getTransforms((BakedToolModel)delegate)) : state);
    }

    private static class BakedAvaritiaToolModel extends BakedWrapper.Perspective
            implements DelegatingModel<ItemModelCacheKey, BakedAvaritiaToolModel>, IItemRenderer, CodeChickenModel {

        private final IBakedModel delegateModel;
        private final IModelState state;
        private final DelegateModelCache<ItemModelCacheKey, BakedAvaritiaToolModel> delegateCache;
        @Nullable
        private PartRenderer cachedPartRenderer = null;

        public BakedAvaritiaToolModel(IBakedModel parent, IModelState state) {
            super(parent, PerspectiveMapWrapper.getTransforms(state));
            this.delegateModel = parent;
            this.state = state;
            this.delegateCache = new ToolModelCache(this);
        }

        private BakedAvaritiaToolModel(IBakedModel delegateModel, IBakedModel parent, IModelState state,
                                       DelegateModelCache<ItemModelCacheKey, BakedAvaritiaToolModel> delegateCache) {
            super(parent, PerspectiveMapWrapper.getTransforms(state));
            this.delegateModel = delegateModel;
            this.state = state;
            this.delegateCache = delegateCache;
        }

        @Override
        public IBakedModel getParentModel() {
            return delegateModel;
        }

        @Override
        public IModelState getTransforms() {
            return state;
        }

        private PartRenderer getPartRenderer(ItemStack stack) {
            if (cachedPartRenderer == null) {
                cachedPartRenderer = delegateModel instanceof BakedToolModel
                        ? new ToolPartRenderer((BakedToolModel)delegateModel, stack)
                        : new PartRenderer.Noop();
            }
            return cachedPartRenderer;
        }

        @Override
        public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {
            PartRenderer partRenderer = getPartRenderer(stack);
            partRenderer.renderUnderlays(transformType);
            WrappedItemRenderer.renderModel(parent, stack);
            partRenderer.renderOverlays(transformType);
        }

        @Override
        public ItemOverrideList getOverrides() {
            return delegateCache;
        }

        @Override
        public BakedAvaritiaToolModel wrapDelegate(ItemModelCacheKey delegateKey) {
            IBakedModel delegateModel = delegateKey.getModel();
            ItemStack stack = delegateKey.getStack();
            if (stack != null) {
                return new BakedAvaritiaToolModel(delegateModel, delegateModel.getOverrides()
                        .handleItemState(delegateModel, stack, delegateCache.getCachedWorld(), delegateCache.getCachedEntity()),
                        state, delegateCache);
            } else {
                return new BakedAvaritiaToolModel(delegateModel, delegateModel, state, delegateCache);
            }
        }

        private static class ToolModelCache extends DelegateModelCache<ItemModelCacheKey, BakedAvaritiaToolModel> {

            public ToolModelCache(BakedAvaritiaToolModel rootModel) {
                super(rootModel);
            }

            @Override
            protected ItemModelCacheKey extractDelegateKey(IBakedModel parent, ItemStack stack,
                                                           @Nullable World world, @Nullable EntityLivingBase entity) {
                // we can't directly get the parent override because it "bakes" the composite model into a single collection...
                // ...of quads, whereas we want to grab the actual composite model so we can extract the parts
                // tcon model code does this ridiculous thing where it iterates over the overrides and keeps a variable tracking...
                // ...the last override that matched. this is equivalent to simply iterating in reverse, so we'll do that instead
                for (BakedToolModelOverride override : TconReflectClient.getOverrides((BakedToolModel)parent).reverse()) {
                    if (override.matches(stack, world, entity)) {
                        return new ItemModelCacheKey(override.bakedToolModel, stack);
                    }
                }
                return new ItemModelCacheKey(parent, stack);
            }

        }

        private interface PartRenderer {

            void renderUnderlays(ItemCameraTransforms.TransformType transType);

            void renderOverlays(ItemCameraTransforms.TransformType transType);

            class Noop implements PartRenderer {

                @Override
                public void renderUnderlays(ItemCameraTransforms.TransformType transType) {
                    // NO-OP
                }

                @Override
                public void renderOverlays(ItemCameraTransforms.TransformType transType) {
                    // NO-OP
                }

            }

        }

        private static class ToolPartRenderer implements PartRenderer {

            private final List<Material> materials;
            private final int renderPartCount;
            private final AvaritiaMaterialModel.BakedAvaritiaMaterialModel[] renderablePartModels;

            ToolPartRenderer(BakedToolModel baseModel, ItemStack stack) {
                this.materials = ToolUtils.getToolMaterials(stack);
                BakedMaterialModel[] parts = TconReflectClient.getParts(baseModel);
                this.renderPartCount = Math.min(parts.length, materials.size());
                this.renderablePartModels = new AvaritiaMaterialModel.BakedAvaritiaMaterialModel[renderPartCount];
                if (ToolHelper.isBroken(stack)) {
                    BakedMaterialModel[] brokenParts = TconReflectClient.getBrokenParts(baseModel);
                    for (int i = 0; i < renderPartCount; i++) {
                        BakedMaterialModel part = brokenParts[i];
                        if (part == null) {
                            part = parts[i];
                        }
                        IBakedModel matModel = part.getModelByIdentifier(materials.get(i).identifier);
                        if (matModel instanceof AvaritiaMaterialModel.BakedAvaritiaMaterialModel) {
                            renderablePartModels[i] = (AvaritiaMaterialModel.BakedAvaritiaMaterialModel)matModel;
                        }
                    }
                } else {
                    for (int i = 0; i < renderPartCount; i++) {
                        IBakedModel matModel = parts[i].getModelByIdentifier(materials.get(i).identifier);
                        if (matModel instanceof AvaritiaMaterialModel.BakedAvaritiaMaterialModel) {
                            renderablePartModels[i] = (AvaritiaMaterialModel.BakedAvaritiaMaterialModel)matModel;
                        }
                    }
                }
            }

            @Override
            public void renderUnderlays(ItemCameraTransforms.TransformType transType) {
                for (int i = 0; i < renderPartCount; i++) {
                    if (renderablePartModels[i] != null) {
                        renderablePartModels[i].renderUnderlay(ItemStack.EMPTY, transType, materials.get(i));
                    }
                }
            }

            @Override
            public void renderOverlays(ItemCameraTransforms.TransformType transType) {
                for (int i = 0; i < renderPartCount; i++) {
                    if (renderablePartModels[i] != null) {
                        renderablePartModels[i].renderOverlay(ItemStack.EMPTY, transType, materials.get(i));
                    }
                }
            }

        }

    }

}
