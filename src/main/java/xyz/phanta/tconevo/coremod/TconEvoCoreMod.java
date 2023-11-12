package xyz.phanta.tconevo.coremod;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.SortingIndex(1540)
public class TconEvoCoreMod implements IFMLLoadingPlugin {

    public static final Logger LOGGER = LogManager.getLogger("tconevo-core");

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { TconEvoCoreMod.class.getPackage().getName() + ".TconEvoClassTransformer" };
    }

    @Nullable
    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
        // NO-OP
    }

    @Nullable
    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
