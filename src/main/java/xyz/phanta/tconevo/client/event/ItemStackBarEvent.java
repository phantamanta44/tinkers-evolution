package xyz.phanta.tconevo.client.event;

import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.Event;
import xyz.phanta.tconevo.init.TconEvoCaps;

import java.util.LinkedHashSet;
import java.util.Set;

public class ItemStackBarEvent extends Event {

    public static final String FORGE_ENERGY = "forge_energy";
    public static final int FORGE_ENERGY_FROM = 0x1a237e, FORGE_ENERGY_TO = 0x42a5f5;
    public static final String EU = "ic2_eu";
    public static final int EU_FROM = 0xb61f14, EU_TO = 0xef3425;

    public static ItemStackBarEvent post(ItemStack stack) {
        ItemStackBarEvent event = new ItemStackBarEvent(stack);
        MinecraftForge.EVENT_BUS.post(event);
        return event;
    }

    public final ItemStack stack;
    public final Set<Bar> bars = new LinkedHashSet<>();

    public ItemStackBarEvent(ItemStack stack) {
        this.stack = stack;
    }

    public void addForgeEnergyBar() {
        addForgeEnergyBar(FORGE_ENERGY, FORGE_ENERGY_FROM, FORGE_ENERGY_TO);
    }

    public void addForgeEnergyBar(String key, int colourFrom, int colourTo) {
        OptUtils.capability(stack, CapabilityEnergy.ENERGY).ifPresent(energy ->
                bars.add(new Bar(key, energy.getEnergyStored() / (float)energy.getMaxEnergyStored(), colourFrom, colourTo)));
    }

    public void addEuBar() {
        addEuBar(EU, EU_FROM, EU_TO);
    }

    public void addEuBar(String key, int colourFrom, int colourTo) {
        OptUtils.capability(stack, TconEvoCaps.EU_STORE).ifPresent(energy ->
                bars.add(new Bar(key, (float)(energy.getEuStored() / energy.getEuStoredMax()), colourFrom, colourTo)));
    }

    public static class Bar {

        public final String key;
        public final float amount;
        public final int colourFrom, colourTo;

        public Bar(String key, float amount, int colourFrom, int colourTo) {
            this.key = key;
            this.amount = amount;
            this.colourFrom = colourFrom;
            this.colourTo = colourTo;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Bar && key.equals(((Bar)obj).key);
        }

    }

}
