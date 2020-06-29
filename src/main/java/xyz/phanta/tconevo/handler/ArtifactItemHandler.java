package xyz.phanta.tconevo.handler;

import com.google.gson.*;
import io.github.phantamanta44.libnine.util.math.MathUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.conditions.RandomChance;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.tools.TinkerModifiers;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class ArtifactItemHandler {

    private static final String ARTIFACT_FMT = TextFormatting.YELLOW.toString() + TextFormatting.UNDERLINE.toString();
    private static final String LORE_FMT = TextFormatting.DARK_PURPLE.toString() + TextFormatting.ITALIC.toString();

    @Nullable
    private static Map<ResourceLocation, Double> lootProbabilities = null;

    private static Map<ResourceLocation, Double> getLootProbabilities() {
        if (lootProbabilities == null) {
            lootProbabilities = new HashMap<>();
            for (String entry : TconEvoConfig.artifacts.lootProbabilities) {
                int delim = entry.indexOf(',');
                if (delim == -1) {
                    TconEvoMod.LOGGER.warn("Bad loot probability entry (no comma): {}", entry);
                } else {
                    try {
                        lootProbabilities.put(
                                new ResourceLocation(entry.substring(0, delim)),
                                Double.parseDouble(entry.substring(delim + 1)));
                    } catch (Exception e) {
                        TconEvoMod.LOGGER.warn("Bad loot probability entry (excepted): " + entry, e);
                    }
                }
            }
        }
        return lootProbabilities;
    }

    @Nullable
    private Path artifactDir;
    @Nullable
    private Map<String, Artifact> loadedArtifacts = null;

    public void setArtifactDir(Path artifactDir) {
        if (this.artifactDir != null) {
            throw new IllegalStateException("Artifact directory is already set!");
        }
        this.artifactDir = artifactDir;
    }

    public void loadArtifacts() {
        if (artifactDir == null) {
            throw new IllegalStateException("Artifact directory is not set!");
        }
        if (!TconEvoConfig.artifacts.enabled) {
            loadedArtifacts = Collections.emptyMap();
            return;
        }
        loadedArtifacts = new HashMap<>();
        TconEvoMod.LOGGER.info("Loading artifacts...");
        try {
            JsonParser jsonParser = new JsonParser();
            Iterator<Path> iter = Files.list(artifactDir).filter(f -> f.getFileName().toString().endsWith(".json")).iterator();
            Map<String, ToolCore> toolMap = TinkerRegistry.getTools().stream() // collect to a map for fast lookup
                    .collect(Collectors.toMap(ToolCore::getIdentifier, t -> t));
            artifact_iter:
            while (iter.hasNext()) {
                Path path = iter.next();
                String artifactName = path.getFileName().toString();
                artifactName = artifactName.substring(0, artifactName.length() - 5); // strip off .json extension
                try {
                    JsonObject dto = jsonParser.parse(Files.newBufferedReader(path)).getAsJsonObject();
                    int weight = dto.get("weight").getAsInt();
                    // parse tool type
                    String toolId = dto.get("tool").getAsString();
                    ToolCore toolType = toolMap.get(toolId);
                    if (toolType == null) {
                        TconEvoMod.LOGGER.warn("Unknown tool type \"{}\" for artifact: {}", toolId, artifactName);
                        continue;
                    }
                    // parse materials
                    List<PartMaterialType> componentTypes = toolType.getRequiredComponents();
                    JsonArray materialsDto = dto.getAsJsonArray("materials");
                    if (materialsDto.size() != componentTypes.size()) {
                        TconEvoMod.LOGGER.warn("Needed {} materials but got {} for artifact: {}",
                                componentTypes.size(), materialsDto.size(), artifactName);
                        continue;
                    }
                    Material[] materials = new Material[materialsDto.size()];
                    for (int i = 0; i < materials.length; i++) {
                        String matId = materialsDto.get(i).getAsString();
                        materials[i] = TinkerRegistry.getMaterial(matId);
                        if (materials[i] == Material.UNKNOWN) {
                            TconEvoMod.LOGGER.warn("Unknown material \"{}\" for artifact: {}", matId, artifactName);
                            continue artifact_iter;
                        }
                    }
                    // build component stacks
                    NonNullList<ItemStack> components = NonNullList.create();
                    for (int i = 0; i < materials.length; i++) {
                        Set<IToolPart> parts = componentTypes.get(i).getPossibleParts();
                        if (parts.isEmpty()) {
                            TconEvoMod.LOGGER.warn("No existing tool parts satisfy part {} for artifact: {}", i, artifactName);
                            continue artifact_iter;
                        }
                        components.add(parts.iterator().next().getItemstackWithMaterial(materials[i]));
                    }
                    // build tool
                    components.add(ItemStack.EMPTY); // tool building fails if there isn't a trail empty slot... for some reason
                    ItemStack stack = ToolBuilder.tryBuildTool(
                            components, ARTIFACT_FMT + dto.get("name").getAsString(), Collections.singleton(toolType));
                    if (stack.isEmpty()) {
                        TconEvoMod.LOGGER.warn("Tool building failed for unspecified reason for artifact: {}", artifactName);
                        continue;
                    }
                    TinkerCraftingEvent.ToolCraftingEvent.fireEvent(stack, null, components);
                    ItemStack stackPreMods = stack.copy();
                    // add additional free modifiers
                    if (dto.has("free_mods")) {
                        for (int i = dto.get("free_mods").getAsInt(); i > 0; i--) {
                            TinkerModifiers.modCreative.apply(stack);
                        }
                    }
                    // apply modifiers
                    if (dto.has("mods")) {
                        for (JsonElement modDto : dto.getAsJsonArray("mods")) {
                            String modId;
                            int level;
                            if (modDto.isJsonObject()) { // { id: String, level: Int }
                                JsonObject modDtoObj = modDto.getAsJsonObject();
                                modId = modDtoObj.get("id").getAsString();
                                level = modDtoObj.has("level") ? modDtoObj.get("level").getAsInt() : 1;
                            } else { // otherwise, it's just a string for modifier id
                                modId = modDto.getAsString();
                                level = 1;
                            }
                            IModifier mod = TinkerRegistry.getModifier(modId);
                            if (mod == null) {
                                TconEvoMod.LOGGER.warn("Unknown modifier {} for artifact: {}", modId, artifactName);
                                continue artifact_iter;
                            }
                            while (level > 0) {
                                mod.apply(stack);
                                --level;
                            }
                        }
                    }
                    // finalize the tool
                    TinkerCraftingEvent.ToolModifyEvent.fireEvent(stack, null, stackPreMods);
                    // apply the artifact modifier after modify event because otherwise the modifier would cancel the event
                    TconEvoTraits.MOD_ARTIFACT.apply(stack);
                    ToolUtils.rebuildToolStack(stack);
                    // add lore
                    if (dto.has("lore")) {
                        JsonElement loreDto = dto.get("lore");
                        NBTTagCompound tag = ToolUtils.getOrCreateTag(stack);
                        if (tag.hasKey("display")) {
                            tag = tag.getCompoundTag("display");
                        } else {
                            tag = new NBTTagCompound();
                            tag.setTag("display", tag);
                        }
                        NBTTagList loreTag = new NBTTagList();
                        loreTag.appendTag(new NBTTagString()); // empty line for padding
                        if (loreDto.isJsonArray()) { // many lines of lore
                            for (JsonElement loreLineDto : loreDto.getAsJsonArray()) {
                                loreTag.appendTag(new NBTTagString(LORE_FMT + loreLineDto.getAsString()));
                            }
                        } else { // just one string of lore
                            loreTag.appendTag(new NBTTagString(LORE_FMT + loreDto.getAsString()));
                        }
                        tag.setTag("Lore", loreTag);
                    }
                    loadedArtifacts.put(artifactName, new Artifact(artifactName, stack, weight));
                } catch (Exception e) {
                    TconEvoMod.LOGGER.warn("Encountered exception while loading artifact: " + artifactName, e);
                }
            }
        } catch (IOException e) {
            TconEvoMod.LOGGER.error("Encountered exception while loading artifacts!", e);
        }
        loadedArtifacts = Collections.unmodifiableMap(loadedArtifacts);
    }

    public Map<String, Artifact> getArtifacts() {
        if (loadedArtifacts == null) {
            throw new IllegalStateException("Artifacts are not loaded yet!");
        }
        return loadedArtifacts;
    }

    @SubscribeEvent
    public void onLootTableLoaded(LootTableLoadEvent event) {
        if (TconEvoConfig.artifacts.enabled) {
            Double oddsNullable = getLootProbabilities().get(event.getName());
            if (oddsNullable != null) {
                float odds = (float)MathUtils.clamp(oddsNullable, 0D, 1D);
                event.getTable().addPool(new LootPool(
                        getArtifacts().values().toArray(new LootEntry[0]), new LootCondition[] { new RandomChance(odds) },
                        new RandomValueRange(1F), new RandomValueRange(0F), "tconevo_artifacts"));
            }
        }
    }

    public static class Artifact extends LootEntry {

        private final ItemStack artifactStack;

        public Artifact(String name, ItemStack artifactStack, int weight) {
            super(weight, 0, new LootCondition[0], name);
            this.artifactStack = artifactStack;
        }

        public ItemStack newStack() {
            return artifactStack.copy();
        }

        @Override
        public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
            stacks.add(newStack());
        }

        @Override
        protected void serialize(JsonObject json, JsonSerializationContext context) {
            throw new UnsupportedOperationException(); // there's no reason this should ever need to be serialized
        }

    }

}
