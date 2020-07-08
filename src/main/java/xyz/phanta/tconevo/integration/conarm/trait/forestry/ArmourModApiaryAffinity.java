package xyz.phanta.tconevo.integration.conarm.trait.forestry;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.forestry.ForestryHooks;

public class ArmourModApiaryAffinity extends ArmorModifierTrait {

    public ArmourModApiaryAffinity() {
        super(NameConst.MOD_APIARY_AFFINITY, 0xf4e8a6);
        if (ForestryHooks.isLoaded()) {
            TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, s -> {
                CapabilityBroker broker = new CapabilityBroker();
                ForestryHooks.INSTANCE.registerApiaristArmourCap(broker::with);
                return broker;
            });
        }
    }

}
