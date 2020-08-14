package xyz.phanta.tconevo.integration.natura;

import com.progwml6.natura.nether.NaturaNether;
import com.progwml6.natura.nether.block.planks.BlockNetherPlanks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

@Reflected
public class NaturaHooksImpl implements NaturaHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("planksBloodwood",
                new ItemStack(NaturaNether.netherPlanks, 1, BlockNetherPlanks.PlankType.BLOODWOOD.meta));
        OreDictionary.registerOre("planksDarkwood",
                new ItemStack(NaturaNether.netherPlanks, 1, BlockNetherPlanks.PlankType.DARKWOOD.meta));
        OreDictionary.registerOre("planksFusewood",
                new ItemStack(NaturaNether.netherPlanks, 1, BlockNetherPlanks.PlankType.FUSEWOOD.meta));
        OreDictionary.registerOre("planksGhostwood",
                new ItemStack(NaturaNether.netherPlanks, 1, BlockNetherPlanks.PlankType.GHOSTWOOD.meta));
    }

}
