package xyz.phanta.tconevo.artifact.type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.phantamanta44.libnine.util.helper.ItemUtils;
import io.github.phantamanta44.libnine.util.helper.JsonUtils9;
import io.github.phantamanta44.libnine.util.nbt.ImmutableNbt;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.tools.TinkerModifiers;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.gamestages.GameStagesHooks;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtifactTypeTool implements ArtifactType<ArtifactTypeTool.Spec> {

    @Override
    public Spec parseArtifactSpec(JsonObject dto) {
        //noinspection ConstantConditions
        return new Spec(
                JsonUtils.getString(dto, "name"),
                parseLore(dto),
                JsonUtils.getString(dto, "tool"),
                parseMaterials(dto),
                JsonUtils.getInt(dto, "free_mods", 0),
                parseModifiers(dto),
                JsonUtils.getJsonObject(dto, "data_tag", null));
    }

    public static List<String> parseLore(JsonObject dto) {
        if (!dto.has("lore")) {
            return Collections.emptyList();
        }
        JsonElement loreDto = dto.get("lore");
        if (loreDto.isJsonArray()) { // many lines of lore
            List<String> lore = new ArrayList<>();
            for (JsonElement loreLineDto : loreDto.getAsJsonArray()) {
                if (loreLineDto.isJsonPrimitive() && loreLineDto.getAsJsonPrimitive().isString()) {
                    lore.add(loreLineDto.getAsString());
                } else {
                    throw new JsonSyntaxException("Expected a string in the \"lore\" array, but got: " + loreLineDto);
                }
            }
            return lore;
        } else if (loreDto.isJsonPrimitive() && loreDto.getAsJsonPrimitive().isString()) { // just one string of lore
            return Collections.singletonList(loreDto.getAsString());
        }
        throw new JsonSyntaxException("Expected either a string array or a string for \"lore\", but got " + loreDto);
    }

    public static List<String> parseMaterials(JsonObject dto) {
        return JsonUtils9.stream(dto.getAsJsonArray("materials"))
                .map(s -> {
                    if (s.isJsonPrimitive() && s.getAsJsonPrimitive().isString()) {
                        return s.getAsString();
                    } else {
                        throw new JsonSyntaxException("Expected a string in the \"materials\" array, but got: " + s);
                    }
                })
                .collect(Collectors.toList());
    }

    public static List<IPair<String, Integer>> parseModifiers(JsonObject dto) {
        if (!dto.has("mods")) {
            return Collections.emptyList();
        }
        List<IPair<String, Integer>> modifiers = new ArrayList<>();
        for (JsonElement modDto : JsonUtils.getJsonArray(dto, "mods")) {
            if (modDto.isJsonObject()) { // { id: String, level: Int }
                JsonObject modDtoObj = modDto.getAsJsonObject();
                modifiers.add(IPair.of(JsonUtils.getString(modDtoObj, "id"), JsonUtils.getInt(modDtoObj, "level", 1)));
            } else if (modDto.isJsonPrimitive() && modDto.getAsJsonPrimitive().isString()) { // just id string
                modifiers.add(IPair.of(modDto.getAsString(), 1));
            } else {
                throw new JsonSyntaxException("Expected either a modifier object or a string in \"mods\", but got " + modDto);
            }
        }
        return modifiers;
    }

    @Override
    public ItemStack buildArtifact(Spec spec) throws BuildingException {
        // get tool type
        // this is bad and slow, but tcon doesn't offer any better way to look up tool types
        ToolCore toolType = TinkerRegistry.getTools().stream()
                .filter(t -> t.getIdentifier().equals(spec.toolType))
                .findAny().orElseThrow(() -> new BuildingException("Unknown tool type \"%s\"", spec.toolType));

        // get material types
        List<PartMaterialType> componentTypes = toolType.getRequiredComponents();
        if (spec.materials.size() != componentTypes.size()) {
            throw new BuildingException("Needed %d materials but got %d for tool type \"%s\"",
                    componentTypes.size(), spec.materials.size(), toolType.getIdentifier());
        }
        List<Material> materials = resolveMaterials(spec.materials);

        // build component stacks
        NonNullList<ItemStack> components = NonNullList.create();
        for (int i = 0; i < materials.size(); i++) {
            Set<IToolPart> parts = componentTypes.get(i).getPossibleParts();
            if (parts.isEmpty()) {
                throw new BuildingException("Unsatisfiable part %d for tool type \"%s\"", i, toolType.getIdentifier());
            }
            components.add(parts.iterator().next().getItemstackWithMaterial(materials.get(i)));
        }

        // build tool
        ItemStack stack = toolType.buildItem(materials);
        stack.setStackDisplayName(ARTIFACT_FMT + spec.name);
        try {
            GameStagesHooks.INSTANCE.startBypass();
            TinkerCraftingEvent.ToolCraftingEvent.fireEvent(stack, null, components);
        } catch (TinkerGuiException e) {
            throw new BuildingException("Tool building produced error: %s", e.getMessage());
        } finally {
            GameStagesHooks.INSTANCE.endBypass();
        }
        ItemStack stackPreMods = stack.copy();

        // add additional free modifiers
        for (int i = spec.freeMods; i > 0; i--) {
            TinkerModifiers.modCreative.apply(stack);
        }

        // apply modifiers
        applyModifiers(spec.modifiers, stack);

        // finalize the tool
        try {
            GameStagesHooks.INSTANCE.startBypass();
            TinkerCraftingEvent.ToolModifyEvent.fireEvent(stack, null, stackPreMods.copy());
            // apply the artifact modifier after modify event because otherwise the modifier would cancel the event
            TconEvoTraits.MOD_ARTIFACT.apply(stack);
            ToolUtils.rebuildToolStack(stack);
        } catch (TinkerGuiException e) {
            throw new BuildingException("Tool modification produced error: %s", e.getMessage());
        } finally {
            GameStagesHooks.INSTANCE.endBypass();
        }

        // add lore and other NBT
        addExtraItemData(stack, spec.lore, spec.dataTag);

        return stack; // done!
    }

    public static List<Material> resolveMaterials(List<String> materialIds) throws BuildingException {
        List<Material> materials = new ArrayList<>();
        for (String materialId : materialIds) {
            Material material = TinkerRegistry.getMaterial(materialId);
            if (material == Material.UNKNOWN) {
                throw new ArtifactType.BuildingException("Unknown material \"%s\"", materialId);
            }
            materials.add(material);
        }
        return materials;
    }

    public static void applyModifiers(List<IPair<String, Integer>> modEntries, ItemStack stack) throws BuildingException {
        for (IPair<String, Integer> modEntry : modEntries) {
            IModifier mod = TinkerRegistry.getModifier(modEntry.getA());
            if (mod == null) {
                throw new ArtifactType.BuildingException("Unknown modifier \"%s\"", modEntry.getA());
            }
            for (int i = modEntry.getB(); i > 0; i--) {
                mod.apply(stack);
            }
        }
    }

    public static void addExtraItemData(ItemStack stack, List<String> lore, @Nullable ImmutableNbt<NBTTagCompound> dataTag) {
        NBTTagCompound tag = ItemUtils.getOrCreateTag(stack);
        if (!lore.isEmpty()) {
            NBTTagCompound displayTag;
            if (tag.hasKey("display")) {
                displayTag = tag.getCompoundTag("display");
            } else {
                displayTag = new NBTTagCompound();
                tag.setTag("display", displayTag);
            }
            NBTTagList loreTag = new NBTTagList();
            loreTag.appendTag(new NBTTagString()); // empty line for padding
            for (String line : lore) {
                loreTag.appendTag(new NBTTagString(LORE_FMT + line));
            }
            displayTag.setTag("Lore", loreTag);
        }

        if (dataTag != null) {
            stack.setTagCompound(dataTag.write(tag));
        }
    }

    public static class Spec {

        public final String name;
        public final List<String> lore;
        public final String toolType;
        public final List<String> materials;
        public final int freeMods;
        public final List<IPair<String, Integer>> modifiers;
        @Nullable
        public final ImmutableNbt<NBTTagCompound> dataTag;

        public Spec(String name, List<String> lore, String toolType, List<String> materials,
                    int freeMods, List<IPair<String, Integer>> modifiers, @Nullable JsonObject dataTag) {
            this.name = name;
            this.lore = Collections.unmodifiableList(lore);
            this.toolType = toolType;
            this.materials = Collections.unmodifiableList(materials);
            this.freeMods = freeMods;
            this.modifiers = Collections.unmodifiableList(modifiers);
            this.dataTag = dataTag != null ? ImmutableNbt.parseObject(dataTag) : null;
        }

    }

}
