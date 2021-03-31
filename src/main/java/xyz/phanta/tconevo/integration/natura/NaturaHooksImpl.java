package xyz.phanta.tconevo.integration.natura;

import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.logs.BlockNetherLog;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;

@Reflected
public class NaturaHooksImpl implements NaturaHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("planksBloodwood",
                new ItemStack(NaturaNether.netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.meta));
        OreDictionary.registerOre("logBloodwood",
                new ItemStack(NaturaNether.netherLog2, 1, OreDictionary.WILDCARD_VALUE));

        OreDictionary.registerOre("planksDarkwood",
                new ItemStack(NaturaNether.netherPlanks, 1, BlockNetherPlanks.PlankType.DARKWOOD.meta));
        OreDictionary.registerOre("logDarkwood",
                new ItemStack(NaturaNether.netherLog, 1, BlockNetherLog.LogType.DARKWOOD.meta));

        OreDictionary.registerOre("planksFusewood",
                new ItemStack(NaturaNether.netherPlanks, 1, BlockNetherPlanks.PlankType.FUSEWOOD.meta));
        OreDictionary.registerOre("logFusewood",
                new ItemStack(NaturaNether.netherLog, 1, BlockNetherLog.LogType.FUSEWOOD.meta));

        OreDictionary.registerOre("planksGhostwood",
                new ItemStack(NaturaNether.netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.meta));
        OreDictionary.registerOre("logGhostwood",
                new ItemStack(NaturaNether.netherLog, 1, BlockNetherLog.LogType.GHOSTWOOD.meta));
    }

}
