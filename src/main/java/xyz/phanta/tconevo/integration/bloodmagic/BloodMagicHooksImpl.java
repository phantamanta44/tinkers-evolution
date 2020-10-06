package xyz.phanta.tconevo.integration.bloodmagic;

import WayofTime.bloodmagic.api.impl.BloodMagicAPI;
import WayofTime.bloodmagic.api.impl.BloodMagicRecipeRegistrar;
import WayofTime.bloodmagic.core.RegistrarBloodMagic;
import WayofTime.bloodmagic.core.RegistrarBloodMagicItems;
import WayofTime.bloodmagic.core.data.SoulTicket;
import WayofTime.bloodmagic.item.soul.ItemSentientSword;
import WayofTime.bloodmagic.soul.EnumDemonWillType;
import WayofTime.bloodmagic.soul.IDemonWill;
import WayofTime.bloodmagic.soul.PlayerDemonWillHandler;
import WayofTime.bloodmagic.util.BooleanResult;
import WayofTime.bloodmagic.util.DamageSourceBloodMagic;
import WayofTime.bloodmagic.util.helper.NetworkHelper;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import io.github.phantamanta44.libnine.util.world.WorldUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoItems;
import xyz.phanta.tconevo.item.ItemMaterial;
import xyz.phanta.tconevo.item.ItemMetal;
import xyz.phanta.tconevo.trait.bloodmagic.TraitSentient;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;
import java.util.Optional;

@Reflected
public class BloodMagicHooksImpl implements BloodMagicHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        BloodMagicRecipeRegistrar recipes = BloodMagicAPI.INSTANCE.getRecipeRegistrar();
        recipes.addBloodAltar(
                Ingredient.fromStacks(ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1)),
                TconEvoItems.METAL.newStack(ItemMetal.Type.BOUND_METAL, ItemMetal.Form.INGOT, 1),
                3, 30000, 50, 100);
        recipes.addTartaricForge(
                TconEvoItems.METAL.newStack(ItemMetal.Type.SENTIENT_METAL, ItemMetal.Form.INGOT, 1),
                16, 6,
                new ItemStack(RegistrarBloodMagicItems.SOUL_GEM),
                ItemMaterial.Type.COALESCENCE_MATRIX.newStack(1));
    }

    @Override
    public Optional<ItemStack> getItemWeakBloodShard() {
        if (RegistrarBloodMagicItems.BLOOD_SHARD == Items.AIR) {
            return Optional.empty();
        }
        return Optional.of(new ItemStack(RegistrarBloodMagicItems.BLOOD_SHARD, 1, 0));
    }

    @Override
    public int extractLifePoints(EntityPlayer player, int amount, ItemStack consumer) {
        BooleanResult<Integer> result = NetworkHelper.getSoulNetwork(player)
                .syphonAndDamage(player, SoulTicket.item(consumer, player.world, player, amount));
        return result.isSuccess() ? result.getValue() : 0;
    }

    @Override
    public boolean isSoulDamage(DamageSource dmgSrc) {
        return dmgSrc == DamageSourceBloodMagic.INSTANCE;
    }

    @Override
    public void handleDemonWillDrops(EntityLivingBase attacker, EntityLivingBase target, ItemStack weapon) {
        if (RegistrarBloodMagicItems.SENTIENT_SWORD == Items.AIR) {
            return;
        }
        ItemSentientSword itemSword = (ItemSentientSword)RegistrarBloodMagicItems.SENTIENT_SWORD;
        ItemStack dummyWeapon = new ItemStack(itemSword);
        if (ToolUtils.hasTrait(weapon, NameConst.TRAIT_SENTIENT)) {
            TraitSentient.WillPower willPower = TraitSentient.getWillPower(weapon);
            itemSword.setCurrentType(dummyWeapon, unwrap(willPower.getWillType()));
            itemSword.setStaticDropOfActivatedSword(dummyWeapon, willPower.getStaticDropRate());
            itemSword.setDropOfActivatedSword(dummyWeapon, willPower.getBonusDropRate());
        } else {
            itemSword.setStaticDropOfActivatedSword(dummyWeapon, TconEvoConfig.moduleBloodMagic.willfulStaticDropBase);
            itemSword.setDropOfActivatedSword(dummyWeapon, TconEvoConfig.moduleBloodMagic.willfulBonusDropBase);
        }
        List<ItemStack> drops = itemSword.getRandomDemonWillDrop(target, attacker, dummyWeapon, 0);
        if (drops.isEmpty()) {
            return;
        }
        // adapted from blood magic's WillHandler#onLivingDrops
        Vec3d targetPos = target.getPositionVector();
        for (ItemStack stack : drops) {
            if (attacker instanceof EntityPlayer) {
                stack = PlayerDemonWillHandler.addDemonWill((EntityPlayer)attacker, stack);
            }
            if (!stack.isEmpty()) {
                IDemonWill willItem = (IDemonWill)stack.getItem();
                if (willItem.getWill(willItem.getType(stack), stack) >= 0.0001D) {
                    WorldUtils.dropItem(target.world, targetPos, stack);
                }
            }
        }
        if (attacker instanceof EntityPlayer) {
            ((EntityPlayer)attacker).inventoryContainer.detectAndSendChanges();
        }
    }

    @Override
    public DemonWillType getLargestWillType(EntityPlayer player) {
        return wrap(PlayerDemonWillHandler.getLargestWillType(player));
    }

    @Override
    public double getTotalDemonWill(EntityPlayer player, DemonWillType type) {
        return PlayerDemonWillHandler.getTotalDemonWill(unwrap(type), player);
    }

    @Override
    public double extractDemonWill(EntityPlayer player, DemonWillType type, double amount) {
        return PlayerDemonWillHandler.consumeDemonWill(unwrap(type), player, amount);
    }

    @Override
    public void applySoulSnare(EntityLivingBase entity, int duration) {
        entity.addPotionEffect(new PotionEffect(RegistrarBloodMagic.SOUL_SNARE, duration, 0));
    }

    @Override
    public int getSoulFrayLevel(EntityLivingBase entity) {
        PotionEffect effect = entity.getActivePotionEffect(RegistrarBloodMagic.SOUL_FRAY);
        return effect != null ? effect.getAmplifier() : -1;
    }

    private static EnumDemonWillType unwrap(DemonWillType type) {
        switch (type) {
            case RAW:
                return EnumDemonWillType.DEFAULT;
            case CORROSIVE:
                return EnumDemonWillType.CORROSIVE;
            case DESTRUCTIVE:
                return EnumDemonWillType.DESTRUCTIVE;
            case VENGEFUL:
                return EnumDemonWillType.VENGEFUL;
            case STEADFAST:
                return EnumDemonWillType.STEADFAST;
            default:
                throw new IllegalArgumentException("Bad demon will type: " + type);
        }
    }

    private static DemonWillType wrap(EnumDemonWillType type) {
        switch (type) {
            case DEFAULT:
                return DemonWillType.RAW;
            case CORROSIVE:
                return DemonWillType.CORROSIVE;
            case DESTRUCTIVE:
                return DemonWillType.DESTRUCTIVE;
            case VENGEFUL:
                return DemonWillType.VENGEFUL;
            case STEADFAST:
                return DemonWillType.STEADFAST;
            default:
                throw new IllegalArgumentException("Bad demon will type: " + type);
        }
    }

}
