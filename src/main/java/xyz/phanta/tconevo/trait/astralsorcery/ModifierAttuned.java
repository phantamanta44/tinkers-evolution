package xyz.phanta.tconevo.trait.astralsorcery;

import io.github.phantamanta44.libnine.util.helper.OptUtils;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTraits;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.astralsorcery.AstralConstellation;
import xyz.phanta.tconevo.integration.astralsorcery.AstralHooks;

import javax.annotation.Nullable;

public abstract class ModifierAttuned extends ModifierTrait {

    public static final int COLOUR = 0x0062f9;
    public static final String TAG_ATTUNEMENT = "AttunedConstellation";

    private final AstralConstellation constellation;

    public ModifierAttuned(String identifier, AstralConstellation constellation) {
        super(identifier, COLOUR);
        this.constellation = constellation;
        // slightly faster than direct remove() because freeModifier will likely be near the end of the list
        aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
    }

    public AstralConstellation getConstellation() {
        return constellation;
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        rootCompound.setInteger(TAG_ATTUNEMENT, constellation.ordinal());
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (isConstellationInSky(event.getEntityPlayer().world)) {
            event.setNewSpeed(event.getNewSpeed() + event.getOriginalSpeed()
                    * (float)TconEvoConfig.moduleAstralSorcery.attunementBonusEfficiency);
        }
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        return isConstellationInSky(player.world)
                ? (newDamage + damage * (float)TconEvoConfig.moduleAstralSorcery.attunementBonusDamage) : newDamage;
    }

    private boolean isConstellationInSky(World world) {
        return AstralHooks.INSTANCE.isConstellationInSky(world, constellation);
    }

    @Nullable
    public static AstralConstellation getAttunement(ItemStack stack) {
        return OptUtils.stackTag(stack)
                .filter(t -> t.hasKey(TAG_ATTUNEMENT, Constants.NBT.TAG_INT))
                .map(t -> AstralConstellation.VALUES[t.getInteger(TAG_ATTUNEMENT)])
                .orElse(null);
    }

    private static abstract class EffectToolData extends ModifierAttuned {

        EffectToolData(String identifier, AstralConstellation constellation) {
            super(identifier, constellation);
        }

        @Override
        public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
            super.applyEffect(rootCompound, modifierTag);
            ToolNBT toolData = TagUtil.getToolStats(rootCompound);
            doAttunedEffect(toolData, TagUtil.getOriginalToolStats(rootCompound));
            TagUtil.setToolTag(rootCompound, toolData.get());
        }

        protected abstract void doAttunedEffect(ToolNBT toolData, ToolNBT originalData);

    }

    private static abstract class EffectAfterHit extends ModifierAttuned {

        EffectAfterHit(String identifier, AstralConstellation constellation) {
            super(identifier, constellation);
        }

        @Override
        public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                             float damageDealt, boolean wasCritical, boolean wasHit) {
            if (target.world.isRemote || !wasHit
                    || (player instanceof EntityPlayer && ((EntityPlayer)player).getCooledAttackStrength(0.5F) < 0.95F)) {
                return;
            }
            doAttunedEffect(player, target, damageDealt);
        }

        protected abstract void doAttunedEffect(EntityLivingBase attacker, EntityLivingBase target, float damage);

    }

    private static abstract class EffectEnchant extends ModifierAttuned {

        EffectEnchant(String identifier, AstralConstellation constellation) {
            super(identifier, constellation);
        }

        @Override
        public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
            super.applyEffect(rootCompound, modifierTag);
            IPair<Enchantment, Integer> ench = getAttunedEnchantment();
            while (ToolBuilder.getEnchantmentLevel(rootCompound, ench.getA()) < ench.getB()) {
                ToolBuilder.addEnchantment(rootCompound, ench.getA());
            }
        }

        protected abstract IPair<Enchantment, Integer> getAttunedEnchantment();

    }

    public static class Aevitas extends EffectAfterHit {

        public Aevitas() {
            super(NameConst.MOD_ATTUNED_AEVITAS, AstralConstellation.AEVITAS);
        }

        @Override
        protected void doAttunedEffect(EntityLivingBase attacker, EntityLivingBase target, float damage) {
            attacker.addPotionEffect(new PotionEffect(
                    MobEffects.REGENERATION, TconEvoConfig.moduleAstralSorcery.toolAevitasRegenDuration, 1));
        }

    }

    public static class Armara extends EffectAfterHit {

        public Armara() {
            super(NameConst.MOD_ATTUNED_ARMARA, AstralConstellation.ARMARA);
        }

        @Override
        protected void doAttunedEffect(EntityLivingBase attacker, EntityLivingBase target, float damage) {
            attacker.addPotionEffect(new PotionEffect(
                    MobEffects.RESISTANCE, TconEvoConfig.moduleAstralSorcery.toolArmaraResistanceDuration, 1));
        }

    }

    public static class Discidia extends EffectToolData {

        public Discidia() {
            super(NameConst.MOD_ATTUNED_DISCIDIA, AstralConstellation.DISCIDIA);
        }

        @Override
        protected void doAttunedEffect(ToolNBT toolData, ToolNBT originalData) {
            toolData.attack += originalData.attack * (float)TconEvoConfig.moduleAstralSorcery.toolDiscidiaBonusDamage;
        }

    }

    public static class Evorsio extends EffectToolData {

        public Evorsio() {
            super(NameConst.MOD_ATTUNED_EVORSIO, AstralConstellation.EVORSIO);
        }

        @Override
        protected void doAttunedEffect(ToolNBT toolData, ToolNBT originalData) {
            toolData.speed += originalData.speed * (float)TconEvoConfig.moduleAstralSorcery.toolEvorsioBonusEfficiency;
        }

    }

    public static class Vicio extends EffectAfterHit {

        public Vicio() {
            super(NameConst.MOD_ATTUNED_VICIO, AstralConstellation.VICIO);
        }

        @Override
        protected void doAttunedEffect(EntityLivingBase attacker, EntityLivingBase target, float damage) {
            attacker.addPotionEffect(new PotionEffect(
                    MobEffects.SPEED, TconEvoConfig.moduleAstralSorcery.toolVicioSpeedDuration, 1));
        }

    }

    public static class Bootes extends EffectEnchant {

        public Bootes() {
            super(NameConst.MOD_ATTUNED_BOOTES, AstralConstellation.BOOTES);
        }

        @Override
        protected IPair<Enchantment, Integer> getAttunedEnchantment() {
            return IPair.of(Enchantments.SILK_TOUCH, 1);
        }

    }

    public static class Fornax extends EffectAfterHit {

        public Fornax() {
            super(NameConst.MOD_ATTUNED_FORNAX, AstralConstellation.FORNAX);
        }

        @Override
        protected void doAttunedEffect(EntityLivingBase attacker, EntityLivingBase target, float damage) {
            target.setFire(TconEvoConfig.moduleAstralSorcery.toolFornaxFireDuration);
        }

    }

    public static class Horologium extends EffectAfterHit {

        public Horologium() {
            super(NameConst.MOD_ATTUNED_HOROLOGIUM, AstralConstellation.HOROLOGIUM);
        }

        @Override
        protected void doAttunedEffect(EntityLivingBase attacker, EntityLivingBase target, float damage) {
            AstralHooks.INSTANCE.freezeTime(target.world, target.getPosition(), attacker, 1F,
                    TconEvoConfig.moduleAstralSorcery.toolHorologiumFreezeDuration, false);
        }

    }

    public static class Lucerna extends EffectAfterHit {

        public Lucerna() {
            super(NameConst.MOD_ATTUNED_LUCERNA, AstralConstellation.LUCERNA);
        }

        @Override
        protected void doAttunedEffect(EntityLivingBase attacker, EntityLivingBase target, float damage) {
            target.addPotionEffect(new PotionEffect(
                    MobEffects.GLOWING, TconEvoConfig.moduleAstralSorcery.toolLucernaGlowingDuration, 1));
        }

    }

    public static class Mineralis extends EffectEnchant {

        public Mineralis() {
            super(NameConst.MOD_ATTUNED_MINERALIS, AstralConstellation.MINERALIS);
        }

        @Override
        protected IPair<Enchantment, Integer> getAttunedEnchantment() {
            return IPair.of(Enchantments.FORTUNE, TconEvoConfig.moduleAstralSorcery.toolMineralisFortuneLevel);
        }

    }

    public static class Octans extends ModifierAttuned {

        public Octans() {
            super(NameConst.MOD_ATTUNED_OCTANS, AstralConstellation.OCTANS);
        }

        @Override
        public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
            super.miningSpeed(tool, event);
            TinkerTraits.aquadynamic.miningSpeed(tool, event); // cheating!!!
        }

    }

    public static class Pelotrio extends ModifierAttuned {

        public Pelotrio() {
            super(NameConst.MOD_ATTUNED_PELOTRIO, AstralConstellation.PELOTRIO);
        }

        @Override
        public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
            if (!world.isRemote && !isSelected && entity instanceof EntityLivingBase && entity.ticksExisted % 20 == 0
                    && tool.getItemDamage() > 0 && !ToolHelper.isBroken(tool)) {
                double odds = TconEvoConfig.moduleAstralSorcery.toolPelotrioRepairProbability;
                if (odds > 0D && (odds >= 1D || world.rand.nextDouble() <= odds)) {
                    ToolHelper.healTool(tool, 1, (EntityLivingBase)entity);
                }
            }
        }

    }

}
