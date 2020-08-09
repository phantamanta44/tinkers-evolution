package xyz.phanta.tconevo.integration.avaritia.client;

import codechicken.lib.render.item.IItemRenderer;
import com.google.common.collect.ImmutableList;
import morph.avaritia.client.render.item.WrappedItemRenderer;
import morph.avaritia.client.render.shader.CosmicShaderHelper;
import morph.avaritia.init.AvaritiaTextures;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import slimeknights.tconstruct.library.client.model.BakedMaterialModel;
import slimeknights.tconstruct.library.client.model.MaterialModel;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.IMaterialItem;
import xyz.phanta.tconevo.client.render.material.CosmicMaterialRenderInfo;
import xyz.phanta.tconevo.client.util.CodeChickenModel;
import xyz.phanta.tconevo.client.util.DelegateModelCache;
import xyz.phanta.tconevo.client.util.DelegatingModel;
import xyz.phanta.tconevo.client.util.TconReflectClient;
import xyz.phanta.tconevo.init.TconEvoMaterials;

import javax.annotation.Nullable;
import java.util.function.Function;

class AvaritiaMaterialModel extends MaterialModel {

    private final boolean renderHalo;

    AvaritiaMaterialModel(ImmutableList<ResourceLocation> textures, int offsetX, int offsetY, boolean renderHalo) {
        super(textures, offsetX, offsetY);
        this.renderHalo = renderHalo;
    }

    @Override
    public BakedMaterialModel bakeIt(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        BakedMaterialModel delegate = super.bakeIt(state, format, bakedTextureGetter);
        return renderHalo
                ? new BakedAvaritiaMaterialModel.WithHalo(delegate, new SimpleModelState(TconReflectClient.getTransforms(delegate)))
                : new BakedAvaritiaMaterialModel.WithoutHalo(delegate, new SimpleModelState(TconReflectClient.getTransforms(delegate)));
    }

    public static abstract class BakedAvaritiaMaterialModel extends BakedMaterialModel
            implements DelegatingModel<IBakedModel, BakedAvaritiaMaterialModel>, IItemRenderer, CodeChickenModel {

        protected final IModelState state;
        protected final DelegateModelCache<IBakedModel, BakedAvaritiaMaterialModel> delegateCache;

        BakedAvaritiaMaterialModel(IBakedModel parent, IModelState state) {
            super(parent, PerspectiveMapWrapper.getTransforms(state));
            this.state = state;
            this.delegateCache = new DelegateModelCache.Overriding<>(this);
        }

        protected BakedAvaritiaMaterialModel(IBakedModel parent, IModelState state,
                                             DelegateModelCache<IBakedModel, BakedAvaritiaMaterialModel> delegateCache) {
            super(parent, PerspectiveMapWrapper.getTransforms(state));
            this.state = state;
            this.delegateCache = delegateCache;
        }

        @Override
        public IBakedModel getParentModel() {
            return parent;
        }

        @Override
        public IModelState getTransforms() {
            return state;
        }

        @Override
        public void renderItem(ItemStack stack, ItemCameraTransforms.TransformType transformType) {
            Item item = stack.getItem();
            if (item instanceof IMaterialItem) {
                Material mat = ((IMaterialItem)item).getMaterial(stack);
                renderUnderlay(stack, transformType, mat);
                WrappedItemRenderer.renderModel(parent, stack);
                renderOverlay(stack, transformType, mat);
            } else {
                WrappedItemRenderer.renderModel(parent, stack);
            }
        }

        public void renderUnderlay(ItemStack stack, ItemCameraTransforms.TransformType transType, Material mat) {
            // NO-OP
        }

        public void renderOverlay(ItemStack stack, ItemCameraTransforms.TransformType transType, Material mat) {
            // NO-OP
        }

        @Override
        public IBakedModel getModelByIdentifier(String identifier) {
            return delegateCache.getWrappedDelegate(parent instanceof BakedMaterialModel
                    ? ((BakedMaterialModel)parent).getModelByIdentifier(identifier) : parent);
        }

        @Override
        public ItemOverrideList getOverrides() {
            return delegateCache;
        }

        static class WithHalo extends BakedAvaritiaMaterialModel {

            WithHalo(IBakedModel parent, IModelState state) {
                super(parent, state);
            }

            protected WithHalo(IBakedModel parent, IModelState state,
                               DelegateModelCache<IBakedModel, BakedAvaritiaMaterialModel> delegateCache) {
                super(parent, state, delegateCache);
            }

            @Override
            public BakedAvaritiaMaterialModel wrapDelegate(IBakedModel delegateKey) {
                return new WithHalo(delegateKey, state, delegateCache);
            }

            @Override
            public void renderUnderlay(ItemStack stack, ItemCameraTransforms.TransformType transType, Material mat) {
                if (transType == ItemCameraTransforms.TransformType.GUI) {
                    if (mat == TconEvoMaterials.NEUTRONIUM) {
                        AvaritiaRenderUtils.renderHaloInGui(parent, stack, AvaritiaTextures.HALO_NOISE, 0.4D, 0x99FFFFFF, false);
                    } else if (mat == TconEvoMaterials.INFINITY_METAL) {
                        AvaritiaRenderUtils.renderHaloInGui(parent, stack, AvaritiaTextures.HALO, 0.5D, 0xFF000000, true);
                    }
                }
            }

        }

        static class WithoutHalo extends BakedAvaritiaMaterialModel {

            WithoutHalo(IBakedModel parent, IModelState state) {
                super(parent, state);
            }

            private WithoutHalo(IBakedModel parent, IModelState state,
                                DelegateModelCache<IBakedModel, BakedAvaritiaMaterialModel> delegateCache) {
                super(parent, state, delegateCache);
            }

            @Override
            public BakedAvaritiaMaterialModel wrapDelegate(IBakedModel delegateKey) {
                return new WithoutHalo(delegateKey, state, delegateCache);
            }

            @Override
            public void renderOverlay(ItemStack stack, ItemCameraTransforms.TransformType transType, Material mat) {
                if (mat == TconEvoMaterials.INFINITY_METAL) {
                    TextureAtlasSprite maskSprite = getCosmicMask(parent);
                    if (maskSprite != null) {
                        handleCosmicLighting(transType);
                        if (transType == ItemCameraTransforms.TransformType.GUI) {
                            AvaritiaRenderUtils.renderCosmicOverlayInGui(maskSprite, stack, 1F);
                        } else {
                            AvaritiaRenderUtils.renderCosmicOverlayInWorld(maskSprite, stack, 1F);
                        }
                    }
                }
            }

            @Nullable
            private static TextureAtlasSprite getCosmicMask(IBakedModel itemModel) {
                TextureAtlasSprite baseSprite = itemModel.getParticleTexture();
                if (baseSprite instanceof CosmicMaterialRenderInfo.CosmicTexture) {
                    return ((CosmicMaterialRenderInfo.CosmicTexture)baseSprite).getMaskTexture();
                }
                return null;
            }

            // adapted from avaritia's CosmicItemRender#processLightLevel, which is under MIT
            // https://github.com/Morpheus1101/Avaritia/blob/master/src/main/java/morph/avaritia/client/render/item/CosmicItemRender.java
            private void handleCosmicLighting(ItemCameraTransforms.TransformType transType) {
                switch (transType) {
                    case GROUND:
                    case THIRD_PERSON_LEFT_HAND:
                    case THIRD_PERSON_RIGHT_HAND:
                    case FIRST_PERSON_LEFT_HAND:
                    case FIRST_PERSON_RIGHT_HAND:
                    case HEAD:
                        BlockPos entityPos = delegateCache.getCachedEntityPos();
                        if (entityPos != null) {
                            CosmicShaderHelper.setLightFromLocation(delegateCache.getCachedWorld(), entityPos);
                            return;
                        }
                        break;
                    case GUI:
                        CosmicShaderHelper.setLightLevel(1.2F);
                        return;
                }
                CosmicShaderHelper.setLightLevel(1F);
            }

        }

    }

}
