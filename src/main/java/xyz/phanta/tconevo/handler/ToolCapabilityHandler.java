package xyz.phanta.tconevo.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.TconEvoConsts;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class ToolCapabilityHandler {

    private final ResourceLocation TINKERS_EVO_CAP = new ResourceLocation(TconEvoConsts.MOD_ID, "multi_capability");
    private final Map<String, Function<ItemStack, ICapabilityProvider>> modifierCaps = new HashMap<>();

    public void addModifierCap(Modifier mod, Function<ItemStack, ICapabilityProvider> capFactory) {
        addModifierCap(mod.getIdentifier(), capFactory);
    }

    public void addModifierCap(String modifierId, Function<ItemStack, ICapabilityProvider> capFactory) {
        modifierCaps.put(modifierId, capFactory);
    }

    @SubscribeEvent
    public void onItemCapAttach(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (stack.getItem() instanceof ITinkerable) {
            event.addCapability(TINKERS_EVO_CAP, new TconEvoCapProvider(stack, modifierCaps));
        }
    }

    private static class TconEvoCapProvider implements ICapabilityProvider {

        private final ItemStack stack;
        private final Map<String, Function<ItemStack, ICapabilityProvider>> capFactories;
        private final Map<String, Optional<ICapabilityProvider>> capCache = new HashMap<>();
        // not useful to try to cache the capability -> provider mappings wholesale, since we'd have to check to see if
        // the corresponding modifier is still on the tool, which requires linear search of the modifier list anyways

        public TconEvoCapProvider(ItemStack stack, Map<String, Function<ItemStack, ICapabilityProvider>> capFactories) {
            this.stack = stack;
            this.capFactories = capFactories;
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return getCapability(capability, facing) != null;
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            for (NBTBase tag : TagUtil.getModifiersTagList(stack)) {
                if (tag instanceof NBTTagCompound) {
                    String modId = ((NBTTagCompound)tag).getString("identifier");
                    ICapabilityProvider capProvider = null;
                    Optional<ICapabilityProvider> capProviderOpt = capCache.get(modId);
                    if (capProviderOpt != null) {
                        if (capProviderOpt.isPresent()) {
                            capProvider = capProviderOpt.get();
                        }
                    } else {
                        Function<ItemStack, ICapabilityProvider> capFactory = capFactories.get(modId);
                        if (capFactory != null) {
                            capProvider = capFactory.apply(stack);
                            capCache.put(modId, Optional.of(capProvider));
                        } else {
                            capCache.put(modId, Optional.empty());
                        }
                    }
                    if (capProvider != null && capProvider.hasCapability(capability, facing)) {
                        return Objects.requireNonNull(capProvider.getCapability(capability, facing));
                    }
                }
            }
            return null;
        }

    }

}
