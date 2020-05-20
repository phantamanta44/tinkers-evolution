package xyz.phanta.tconevo.integration.draconicevolution.client;

import com.brandon3055.draconicevolution.client.handler.HudHandler;
import com.brandon3055.draconicevolution.items.armor.ICustomArmor;
import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import io.github.phantamanta44.libnine.util.helper.MirrorUtils.IField;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.items.IItemHandler;
import xyz.phanta.tconevo.capability.EnergyShield;
import xyz.phanta.tconevo.handler.EnergyShieldHandler;
import xyz.phanta.tconevo.integration.baubles.BaublesHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DraconicShieldHudHandler {

    private static final IField<Float> fArmorStatsFadeOut = MirrorUtils.reflectField(HudHandler.class, "armorStatsFadeOut");
    private static final IField<Boolean> fShowShieldHud = MirrorUtils.reflectField(HudHandler.class, "showShieldHud");
    private static final IField<Float> fMaxShieldPoints = MirrorUtils.reflectField(HudHandler.class, "maxShieldPoints");
    private static final IField<Float> fShieldPoints = MirrorUtils.reflectField(HudHandler.class, "shieldPoints");
    private static final IField<Integer> fShieldPercentCharge = MirrorUtils.reflectField(HudHandler.class, "shieldPercentCharge");
    private static final IField<Float> fShieldEntropy = MirrorUtils.reflectField(HudHandler.class, "shieldEntropy");
    private static final IField<Integer> fRfCharge = MirrorUtils.reflectField(HudHandler.class, "rfCharge");
    private static final IField<Long> fRfTotal = MirrorUtils.reflectField(HudHandler.class, "rfTotal");

    // stats as computed by DE
    private boolean cachedDeStats = false;
    private float deMaxShieldPoints = 0F, deShieldPoints = 0F, deShieldEntropoy = 0F;
    private long deRfTotal = 0;
    // stats as computed here
    private float augMaxShieldPoints = 0F, augShieldPoints = 0F, augShieldEntropy = 0F;
    private long augRfTotal = 0;

    // lower priority means this always runs after DE's client tick handler
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onClientTickLater(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player == null) {
                return;
            }
            List<EnergyShield> shields = EnergyShieldHandler.collectArmourShields(player.inventory);
            if (shields.isEmpty()) {
                cachedDeStats = false;
                return;
            }
            float shieldPoints = 0F, shieldCap = 0F, entropy = 0F;
            long energy = 0L, energyCap = 0L;
            List<ItemStack> deArmour = findDeArmourPieces(player);
            // if no draconic armour is equipped, the DE hud handler stops updating these vars, so they'll have stale data
            if (!deArmour.isEmpty()) {
                shieldPoints = fShieldPoints.get(null);
                shieldCap = fMaxShieldPoints.get(null);
                entropy = fShieldEntropy.get(null);
                energy = fRfTotal.get(null);
                for (ItemStack stack : deArmour) {
                    if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
                        energyCap += Objects.requireNonNull(stack.getCapability(CapabilityEnergy.ENERGY, null)).getMaxEnergyStored();
                    }
                }
            }
            // cache DE-computed values so the hud fade-out isn't messed up
            deMaxShieldPoints = shieldCap;
            deShieldPoints = shieldPoints;
            deShieldEntropoy = entropy;
            deRfTotal = energy;
            cachedDeStats = true;
            // entropy is given as an average over all pieces
            entropy *= deArmour.size();
            for (EnergyShield shield : shields) {
                shieldPoints += shield.getShieldPoints();
                shieldCap += shield.getShieldCapacity();
                entropy += shield.getEntropy();
                energy += shield.getEnergyStored();
                energyCap += shield.getMaxEnergyStored();
            }
            entropy /= deArmour.size() + shields.size();
            if (shieldCap != augMaxShieldPoints || shieldPoints != augShieldPoints
                    || entropy != augShieldEntropy || energy != augRfTotal) {
                fArmorStatsFadeOut.set(null, 5F);
                fShowShieldHud.set(null, true);
            } else if (fArmorStatsFadeOut.get(null) > 0F) {
                fShowShieldHud.set(null, true);
            }
            fMaxShieldPoints.set(null, augMaxShieldPoints = shieldCap);
            fShieldPoints.set(null, augShieldPoints = shieldPoints);
            fShieldPercentCharge.set(null, (int)(shieldPoints / shieldCap * 100F));
            fShieldEntropy.set(null, augShieldEntropy = entropy);
            fRfCharge.set(null, (int)(energy / (float)energyCap * 100F));
            fRfTotal.set(null, augRfTotal = energy);
        }
    }

    private static List<ItemStack> findDeArmourPieces(EntityPlayer player) {
        List<ItemStack> result = new ArrayList<>();
        for (ItemStack stack : player.inventory.armorInventory) {
            if (!stack.isEmpty() && stack.getItem() instanceof ICustomArmor) {
                result.add(stack);
            }
        }
        IItemHandler baublesInv = BaublesHooks.INSTANCE.getBaublesInventory(player);
        if (baublesInv != null) {
            for (int i = 0; i < baublesInv.getSlots(); i++) {
                ItemStack stack = baublesInv.getStackInSlot(i);
                if (!stack.isEmpty() && stack.getItem() instanceof ICustomArmor) {
                    result.add(stack);
                }
            }
        }
        return result;
    }

    // higher priority means this always runs before DE's handler
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onClientTickSooner(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END && cachedDeStats) {
            // restore DE's computed values before entering its handler
            // otherwise, the hud fade time computation gets messed up
            fMaxShieldPoints.set(null, deMaxShieldPoints);
            fShieldPoints.set(null, deShieldPoints);
            fShieldEntropy.set(null, deShieldEntropoy);
            fRfTotal.set(null, deRfTotal);
        }
    }

}
