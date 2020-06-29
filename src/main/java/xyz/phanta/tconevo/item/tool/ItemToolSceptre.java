package xyz.phanta.tconevo.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.events.ProjectileEvent;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.tools.TinkerToolCore;
import slimeknights.tconstruct.library.tools.ranged.IProjectile;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.library.utils.TooltipBuilder;
import slimeknights.tconstruct.tools.TinkerTools;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.entity.EntityMagicMissile;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.init.TconEvoPartTypes;
import xyz.phanta.tconevo.material.stats.MagicMaterialStats;
import xyz.phanta.tconevo.network.SPacketEntitySpecialEffect;
import xyz.phanta.tconevo.util.DamageUtils;
import xyz.phanta.tconevo.util.ToolUtils;
import xyz.phanta.tconevo.util.VectorUtils;

import java.util.List;
import java.util.Optional;

public class ItemToolSceptre extends TinkerToolCore implements IProjectile {

    private static final float DURABILITY_MOD = 1.3F;
    private static final int[] REPAIR_PARTS = { 1, 2 };

    public ItemToolSceptre() {
        super(PartMaterialType.handle(TinkerTools.toughToolRod),
                TconEvoPartTypes.magic(TconEvoItems.PART_ARCANE_FOCUS),
                PartMaterialType.head(TinkerTools.largePlate),
                PartMaterialType.extra(TinkerTools.toughBinding));
        addCategory(Category.WEAPON, Category.LAUNCHER);
    }

    @Override
    public String getIdentifier() {
        return NameConst.TOOL_SCEPTRE;
    }

    @Override
    public float damagePotential() {
        return 0.8F;
    }

    @Override
    public double attackSpeed() {
        return 1.6D;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!ToolHelper.isBroken(stack) && !player.getCooldownTracker().hasCooldown(this)) {
            if (!world.isRemote) {
                if (!player.capabilities.isCreativeMode) {
                    ToolHelper.damageTool(stack, 8, player);
                }
                ProjectileLauncherNBT data = ProjectileLauncherNBT.from(stack);
                Vec3d look = player.getLookVec();
                Vec3d up = player.getVectorForRotation(player.rotationPitch - 90F, player.rotationYaw);
                int colour = ToolUtils.getToolMaterials(stack).get(1).materialTextColor;
                emitProjectile(world, player, look, data.range, stack, colour);
                emitProjectile(world, player, VectorUtils.rotate(look, up, -MathUtils.PI_F / 12F), data.range, stack, colour);
                emitProjectile(world, player, VectorUtils.rotate(look, up, MathUtils.PI_F / 12F), data.range, stack, colour);
                world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.EVOCATION_ILLAGER_CAST_SPELL,
                        SoundCategory.PLAYERS, 1.5F, 0.8F + itemRand.nextFloat() * 0.4F);
                double atkSpd = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue();
                player.getCooldownTracker().setCooldown(this, Math.max((int)Math.round(25.6D / atkSpd), 1));
            }
            if (world.isRemote) {
                player.swingArm(hand);
            }
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    private static void emitProjectile(World world, EntityLivingBase shooter, Vec3d trajectory, float velocity,
                                       ItemStack weapon, int colour) {
        EntityMagicMissile missile = new EntityMagicMissile(world, shooter, trajectory, velocity, weapon);
        missile.setColour(colour);
        if (ProjectileEvent.OnLaunch.fireEvent(missile, weapon, shooter)) {
            world.spawnEntity(missile);
        }
    }

    @Override
    public boolean dealDamage(ItemStack stack, EntityLivingBase player, Entity entity, float damage) {
        if (entity.attackEntityFrom(DamageUtils.getEntityDamageSource(player).setMagicDamage(), damage)) {
            TconEvoMod.PROXY.playEntityEffect(entity, SPacketEntitySpecialEffect.EffectType.ENTROPY_BURST);
            return true;
        }
        return false;
    }

    @Override
    public boolean dealDamageRanged(ItemStack stack, Entity projectile, EntityLivingBase player, Entity entity, float damage) {
        return entity.attackEntityFrom(
                DamageUtils.getProjectileDamageSource(entity, player, projectile).setMagicDamage(), damage);
    }

    @Override
    public Multimap<String, AttributeModifier> getProjectileAttributeModifier(ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.create();
        multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(
                ATTACK_DAMAGE_MODIFIER, "Weapon modifier", ToolHelper.getActualAttack(stack),
                Constants.AttributeModifierOperation.ADD));
        multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(
                ATTACK_SPEED_MODIFIER, "Weapon modifier", Double.MAX_VALUE, Constants.AttributeModifierOperation.ADD));
        for (ITrait trait : TinkerUtil.getTraitsOrdered(stack)) {
            trait.getAttributeModifiers(EntityEquipmentSlot.MAINHAND, stack, multimap);
        }
        return multimap;
    }

    @Override
    public int[] getRepairParts() {
        return REPAIR_PARTS;
    }

    @Override
    public float getRepairModifierForPart(int index) {
        return index == 1 ? DURABILITY_MOD : DURABILITY_MOD * 0.75F;
    }

    @Override
    protected int repairCustom(Material material, NonNullList<ItemStack> repairItems) {
        Optional<RecipeMatch.Match> matchOptional = material.matches(repairItems);
        return matchOptional.map(match -> {
            RecipeMatch.removeMatch(repairItems, match);
            return (int)(material.<MagicMaterialStats>getStats(TconEvoPartTypes.MAGIC).durability
                    * (match.amount / (float)Material.VALUE_Ingot));
        }).orElse(0);
    }

    @Override
    public ProjectileLauncherNBT buildTagData(List<Material> materials) {
        HandleMaterialStats handle = materials.get(0).getStatsOrUnknown(MaterialTypes.HANDLE);
        MagicMaterialStats magic = materials.get(1).getStatsOrUnknown(TconEvoPartTypes.MAGIC);
        HeadMaterialStats setting = materials.get(2).getStatsOrUnknown(MaterialTypes.HEAD);
        ExtraMaterialStats hilt = materials.get(3).getStatsOrUnknown(MaterialTypes.EXTRA);

        ProjectileLauncherNBT data = new ProjectileLauncherNBT();
        data.head(setting, magic.asHead(0F, 1F));
        data.extra(hilt);
        data.handle(handle);
        data.limb(magic.asBow(0F));

        data.durability *= DURABILITY_MOD;
        return data;
    }

    @Override
    public List<String> getInformation(ItemStack stack, boolean detailed) {
        TooltipBuilder info = new TooltipBuilder(stack);
        info.addDurability(!detailed);
        info.addAttack();
        info.addRange();
        if (ToolHelper.getFreeModifiers(stack) > 0) {
            info.addFreeModifiers();
        }
        if (detailed) {
            info.addModifierInfo();
        }
        return info.getTooltip();
    }

}
