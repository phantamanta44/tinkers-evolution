package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.common.armor.modifiers.ModMending;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import io.github.phantamanta44.libnine.capability.provider.CapabilityBroker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.client.event.ItemStackBarEvent;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.trait.ModifierFluxed;
import xyz.phanta.tconevo.trait.base.EnergeticModifier;
import xyz.phanta.tconevo.trait.base.MatchSensitiveModifier;

// mostly just delegates to ModifierFluxed because the behaviour is identical
public class ArmourModFluxed extends ArmorModifierTrait implements MatchSensitiveModifier, EnergeticModifier {

    public ArmourModFluxed() {
        super(NameConst.MOD_FLUXED, ModifierFluxed.COLOUR);
        aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
        addAspects(new ModifierAspect.FreeFirstModifierAspect(this, 1));

        TconEvoMod.PROXY.getToolCapHandler().addModifierCap(this, s -> new CapabilityBroker()
                .with(CapabilityEnergy.ENERGY, new ModifierFluxed.FluxedEnergyStore(s)));
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return !PowerWrapper.isPowered(stack) || isToolWithTrait(stack);
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        return !(otherModifier instanceof ModMending);
    }

    @Override
    public boolean canApplyCustomWithMatch(ItemStack tool, RecipeMatch.Match match) {
        return TconEvoTraits.MOD_FLUXED.canApplyCustomWithMatch(tool, match);
    }

    @Override
    public void applyEffectWithMatch(ItemStack tool, RecipeMatch.Match match) {
        TconEvoTraits.MOD_FLUXED.applyEffectWithMatch(tool, match);
    }

    @Override
    public int onArmorDamage(ItemStack armour, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        return ModifierFluxed.doDamageReduction(armour, newDamage, TconEvoConfig.general.modFluxedEnergyCostArmour);
    }

    @Override
    public int getPriority() {
        return 25;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemStackBars(ItemStackBarEvent event) {
        if (isToolWithTrait(event.stack)) {
            event.addForgeEnergyBar();
        }
    }

}
