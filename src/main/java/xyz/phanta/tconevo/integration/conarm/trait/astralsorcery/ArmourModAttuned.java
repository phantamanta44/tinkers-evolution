package xyz.phanta.tconevo.integration.conarm.trait.astralsorcery;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.astralsorcery.AstralConstellation;
import xyz.phanta.tconevo.integration.astralsorcery.AstralHooks;
import xyz.phanta.tconevo.trait.astralsorcery.ModifierAttuned;

import javax.annotation.Nullable;

public abstract class ArmourModAttuned extends ArmorModifierTrait {

    private final AstralConstellation constellation;

    public ArmourModAttuned(String identifier, AstralConstellation constellation) {
        super(identifier, ModifierAttuned.COLOUR);
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
        rootCompound.setInteger(ModifierAttuned.TAG_ATTUNEMENT, constellation.ordinal());
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armour,
                                               DamageSource source, double damage, int slot) {
        if (isConstellationInSky(player.world)) {
            mods.addEffectiveness((float)TconEvoConfig.moduleAstralSorcery.attunementBonusProtection);
        }
        return mods;
    }

    private boolean isConstellationInSky(World world) {
        return AstralHooks.INSTANCE.isConstellationInSky(world, constellation);
    }

    private static abstract class EffectAfterHit extends ArmourModAttuned {

        EffectAfterHit(String identifier, AstralConstellation constellation) {
            super(identifier, constellation);
        }

        @Override
        public float onHurt(ItemStack armour, EntityPlayer player, DamageSource source, float damage, float newDamage,
                            LivingHurtEvent event) {
            if (!player.world.isRemote) {
                Entity attacker = event.getSource().getTrueSource();
                doAttunedEffect(armour, player, attacker instanceof EntityLivingBase ? (EntityLivingBase)attacker : null, newDamage);
            }
            return newDamage;
        }

        protected abstract void doAttunedEffect(ItemStack armour, EntityPlayer player, @Nullable EntityLivingBase attacker, float damage);

    }

    private static abstract class EffectPassivePotion extends ArmourModAttuned {

        EffectPassivePotion(String identifier, AstralConstellation constellation) {
            super(identifier, constellation);
            MinecraftForge.EVENT_BUS.register(this);
        }

        // use this tick handler rather than onArmorTick so the effect only procs once for players with multiple armour pieces
        @SubscribeEvent
        public void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (!event.player.world.isRemote && event.phase == TickEvent.Phase.START && event.player.ticksExisted % 40 == 0) {
                for (ItemStack stack : event.player.inventory.armorInventory) {
                    if (isToolWithTrait(stack)) {
                        event.player.addPotionEffect(new PotionEffect(getPotionType(), getPotionDuration(), 0, false, false));
                        break;
                    }
                }
            }
        }

        protected abstract Potion getPotionType();

        protected int getPotionDuration() {
            return 60;
        }

    }

    public static class Aevitas extends EffectAfterHit {

        public Aevitas() {
            super(NameConst.MOD_ATTUNED_AEVITAS, AstralConstellation.AEVITAS);
        }

        @Override
        protected void doAttunedEffect(ItemStack armour, EntityPlayer player, @Nullable EntityLivingBase attacker, float damage) {
            player.addPotionEffect(new PotionEffect(
                    MobEffects.REGENERATION, TconEvoConfig.moduleAstralSorcery.armourAevitasRegenDuration, 1));
        }

    }

    public static class Armara extends ArmourModAttuned {

        public Armara() {
            super(NameConst.MOD_ATTUNED_ARMARA, AstralConstellation.ARMARA);
        }

        @Override
        public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
            super.applyEffect(rootCompound, modifierTag);
            float bonusProt = (float)TconEvoConfig.moduleAstralSorcery.armourArmaraBonusProtection;
            if (bonusProt > 0F) {
                ArmorNBT armourData = ArmorTagUtil.getArmorStats(rootCompound);
                ArmorNBT origData = ArmorTagUtil.getOriginalArmorStats(rootCompound);
                armourData.defense += origData.defense * bonusProt;
                armourData.toughness += origData.toughness * bonusProt;
                TagUtil.setToolTag(rootCompound, armourData.get());
            }
        }

    }

    public static class Discidia extends EffectAfterHit {

        public Discidia() {
            super(NameConst.MOD_ATTUNED_DISCIDIA, AstralConstellation.DISCIDIA);
        }

        @Override
        protected void doAttunedEffect(ItemStack armour, EntityPlayer player, @Nullable EntityLivingBase attacker, float damage) {
            if (attacker != null) {
                damage *= (float)TconEvoConfig.moduleAstralSorcery.armourDiscidiaReflectRatio;
                if (damage > 0F) {
                    attacker.attackEntityFrom(DamageSource.causeThornsDamage(player), damage);
                }
            }
        }

    }

    public static class Evorsio extends EffectPassivePotion {

        public Evorsio() {
            super(NameConst.MOD_ATTUNED_EVORSIO, AstralConstellation.EVORSIO);
        }

        @Override
        protected Potion getPotionType() {
            return MobEffects.HASTE;
        }

    }

    public static class Vicio extends EffectAfterHit {

        public Vicio() {
            super(NameConst.MOD_ATTUNED_VICIO, AstralConstellation.VICIO);
        }

        @Override
        protected void doAttunedEffect(ItemStack armour, EntityPlayer player, @Nullable EntityLivingBase attacker, float damage) {
            player.addPotionEffect(new PotionEffect(
                    MobEffects.SPEED, TconEvoConfig.moduleAstralSorcery.armourVicioSpeedDuration, 1));
        }

    }

    public static class Bootes extends EffectAfterHit {

        public Bootes() {
            super(NameConst.MOD_ATTUNED_BOOTES, AstralConstellation.BOOTES);
        }

        @Override
        protected void doAttunedEffect(ItemStack armour, EntityPlayer player, @Nullable EntityLivingBase attacker, float damage) {
            if (attacker != null) {
                double odds = TconEvoConfig.moduleAstralSorcery.armourBootesFlareProbability;
                if (odds > 0D && (odds >= 1D || player.world.rand.nextDouble() <= odds)) {
                    AstralHooks.INSTANCE.spawnFlare(player, attacker);
                }
            }
        }

    }

    public static class Fornax extends EffectPassivePotion {

        public Fornax() {
            super(NameConst.MOD_ATTUNED_FORNAX, AstralConstellation.FORNAX);
        }

        @Override
        protected Potion getPotionType() {
            return MobEffects.FIRE_RESISTANCE;
        }

    }

    public static class Horologium extends EffectAfterHit {

        public Horologium() {
            super(NameConst.MOD_ATTUNED_HOROLOGIUM, AstralConstellation.HOROLOGIUM);
        }

        @Override
        protected void doAttunedEffect(ItemStack armour, EntityPlayer player, @Nullable EntityLivingBase attacker, float damage) {
            if (!player.getCooldownTracker().hasCooldown(armour.getItem())) {
                AstralHooks.INSTANCE.freezeTime(player.world, player.getPosition(), player,
                        (float)TconEvoConfig.moduleAstralSorcery.armourHorologiumFreezeRange,
                        TconEvoConfig.moduleAstralSorcery.armourHorologiumFreezeDuration,
                        false);
                player.getCooldownTracker().setCooldown(armour.getItem(), TconEvoConfig.moduleAstralSorcery.armourHorologiumCooldown);
            }
        }

    }

    public static class Lucerna extends EffectPassivePotion {

        public Lucerna() {
            super(NameConst.MOD_ATTUNED_LUCERNA, AstralConstellation.LUCERNA);
        }

        @Override
        protected Potion getPotionType() {
            return MobEffects.NIGHT_VISION;
        }

        @Override
        protected int getPotionDuration() {
            return 300; // night vision flashes when it has less than 10 seconds left and that's bad
        }

    }

    public static class Mineralis extends ArmourModAttuned {

        public Mineralis() {
            super(NameConst.MOD_ATTUNED_MINERALIS, AstralConstellation.MINERALIS);
            MinecraftForge.EVENT_BUS.register(this);
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.phase == TickEvent.Phase.END) {
                for (ItemStack stack : event.player.inventory.armorInventory) {
                    if (isToolWithTrait(stack)) {
                        AstralHooks.INSTANCE.drawMineralisArmourEffect(event.player);
                        break;
                    }
                }
            }
        }

    }

    public static class Octans extends EffectPassivePotion {

        public Octans() {
            super(NameConst.MOD_ATTUNED_OCTANS, AstralConstellation.OCTANS);
        }

        @Override
        protected Potion getPotionType() {
            return MobEffects.WATER_BREATHING;
        }

    }

    public static class Pelotrio extends ArmourModAttuned {

        public Pelotrio() {
            super(NameConst.MOD_ATTUNED_PELOTRIO, AstralConstellation.PELOTRIO);
        }

        @Override
        public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
            if (!world.isRemote && player.ticksExisted % 20 == 0 && tool.getItemDamage() > 0 && !ToolHelper.isBroken(tool)) {
                double odds = TconEvoConfig.moduleAstralSorcery.armourPelotrioRepairProbability;
                if (odds > 0D && (odds >= 1D || world.rand.nextDouble() <= odds)) {
                    ArmorHelper.healArmor(tool, 1, player, EntityLiving.getSlotForItemStack(tool).getIndex());
                }
            }
        }

    }

}
