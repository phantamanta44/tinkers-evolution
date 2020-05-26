package xyz.phanta.tconevo.integration.botania;

import c4.conarm.lib.armor.ArmorCore;
import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.botania.api.mana.ManaDiscountEvent;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.core.handler.ModSounds;
import vazkii.botania.common.core.handler.PixieHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.entity.EntityManaBurst;
import vazkii.botania.common.entity.EntityPixie;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.armor.elementium.ItemElementiumHelm;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.conarm.trait.botania.ArmourTraitManaAffinity;
import xyz.phanta.tconevo.util.ToolUtils;

public class BotaniaHooksImpl implements BotaniaHooks {

    private static final MirrorUtils.IField<Potion[]> fPixieHandler_potions
            = MirrorUtils.reflectField(PixieHandler.class, "potions");

    @Override
    public void doRegistration() {
        new ItemManaGiverImpl();
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean requestManaExactDiscounted(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
        return ManaItemHandler.requestManaExactForTool(stack, player, amount, commit);
    }

    @Override
    public int dispatchMana(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
        return ManaItemHandler.dispatchMana(stack, player, amount, commit);
    }

    @Override
    public boolean dispatchManaExact(ItemStack stack, EntityPlayer player, int amount, boolean commit) {
        return ManaItemHandler.dispatchManaExact(stack, player, amount, commit);
    }

    @SubscribeEvent
    public void onManaDiscount(ManaDiscountEvent event) {
        float discount = 0F;
        for (ItemStack stack : event.getEntityPlayer().inventory.armorInventory) {
            if (stack.getItem() instanceof ArmorCore) {
                int level = ToolUtils.getTraitLevel(stack, NameConst.ARMOUR_TRAIT_MANA_AFFINITY);
                if (level > 0) {
                    discount += ArmourTraitManaAffinity.getDiscount(stack, level);
                }
            }
        }
        if (discount > 0F) {
            event.setDiscount(Math.min(event.getDiscount() + discount, 1F));
        }
    }

    // adapted from botania's PixieHandler::onDamageTaken
    @Override
    public void spawnPixie(EntityPlayer player, EntityLivingBase target) {
        EntityPixie pixie = new EntityPixie(player.world);
        pixie.setPosition(player.posX, player.posY + 2D, player.posZ);
        if (((ItemElementiumHelm)ModItems.elementiumHelm).hasArmorSet(player)) {
            Potion[] potions = fPixieHandler_potions.get(null);
            pixie.setApplyPotionEffect(new PotionEffect(potions[player.world.rand.nextInt(potions.length)], 40, 0));
        }
        ItemStack stack = player.getHeldItemMainhand();
        pixie.setProps(target, player, 0, (!stack.isEmpty() && stack.getItem() == ModItems.elementiumSword) ? 6F : 4F);
        pixie.onInitialSpawn(player.world.getDifficultyForLocation(new BlockPos(pixie)), null);
        player.world.spawnEntity(pixie);
    }

    // adapted from botania's ItemTerraSword#trySpawnBurst and ItemTerraSword#getBurst
    @Override
    public void spawnGaiaWrathBeam(EntityPlayer player, EnumHand hand) {
        EntityManaBurst burst = new EntityManaBurst(player, hand);
        burst.setColor(0x20ff20);
        burst.setMana(TconEvoConfig.moduleBotania.manaInfusedCost);
        burst.setStartingMana(TconEvoConfig.moduleBotania.manaInfusedCost);
        burst.setMinManaLoss(40);
        burst.setManaLossPerTick(4F);
        burst.setGravity(0F);
        burst.motionX *= 7D;
        burst.motionY *= 7D;
        burst.motionZ *= 7D;
        ItemStack fakeSword = new ItemStack(ModItems.terraSword);
        ItemNBTHelper.setString(fakeSword, "attackerUsername", player.getName());
        burst.setSourceLens(fakeSword);
        player.world.spawnEntity(burst);
        player.world.playSound(null, player.posX, player.posY, player.posZ, ModSounds.terraBlade, SoundCategory.PLAYERS, 0.4F, 1.4F);
    }

}
