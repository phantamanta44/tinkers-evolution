package xyz.phanta.tconevo.artifact.type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.phantamanta44.libnine.util.helper.JsonUtils9;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import slimeknights.tconstruct.tools.TinkerModifiers;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ArtifactTypeTool implements ArtifactType<ArtifactTypeTool.Spec> {

    @Override
    public Spec parseArtifactSpec(JsonObject dto) throws BuildingException {
        // parse modifiers
        List<IPair<String, Integer>> modifiers;
        if (dto.has("mods")) {
            modifiers = new ArrayList<>();
            for (JsonElement modDto : dto.getAsJsonArray("mods")) {
                if (modDto.isJsonObject()) { // { id: String, level: Int }
                    JsonObject modDtoObj = modDto.getAsJsonObject();
                    modifiers.add(IPair.of(modDtoObj.get("id").getAsString(),
                            modDtoObj.has("level") ? modDtoObj.get("level").getAsInt() : 1));
                } else { // otherwise, it's just a string for modifier id
                    modifiers.add(IPair.of(modDto.getAsString(), 1));
                }
            }
        } else {
            modifiers = Collections.emptyList();
        }

        // parse lore
        List<String> lore;
        if (dto.has("lore")) {
            JsonElement loreDto = dto.get("lore");
            if (loreDto.isJsonArray()) { // many lines of lore
                lore = new ArrayList<>();
                for (JsonElement loreLineDto : loreDto.getAsJsonArray()) {
                    lore.add(loreLineDto.getAsString());
                }
            } else { // just one string of lore
                lore = Collections.singletonList(loreDto.getAsString());
            }
        } else {
            lore = Collections.emptyList();
        }

        return new Spec(
                dto.get("name").getAsString(),
                lore,
                dto.get("tool").getAsString(),
                JsonUtils9.stream(dto.getAsJsonArray("materials")).map(JsonElement::getAsString).collect(Collectors.toList()),
                dto.has("free_mods") ? dto.get("free_mods").getAsInt() : 0,
                modifiers);
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
        Material[] materials = new Material[componentTypes.size()];
        for (int i = 0; i < materials.length; i++) {
            String matId = spec.materials.get(i);
            materials[i] = TinkerRegistry.getMaterial(matId);
            if (materials[i] == Material.UNKNOWN) {
                throw new BuildingException("Unknown material \"%s\"", matId);
            }
        }

        // build component stacks
        NonNullList<ItemStack> components = NonNullList.create();
        for (int i = 0; i < materials.length; i++) {
            Set<IToolPart> parts = componentTypes.get(i).getPossibleParts();
            if (parts.isEmpty()) {
                throw new BuildingException("Unsatisfiable part %d for tool type \"%s\"", i, toolType.getIdentifier());
            }
            components.add(parts.iterator().next().getItemstackWithMaterial(materials[i]));
        }

        // build tool
        components.add(ItemStack.EMPTY); // tool building fails if there isn't a trail empty slot... for some reason
        ItemStack stack;
        try {
            stack = ToolBuilder.tryBuildTool(components, ARTIFACT_FMT + spec.name, Collections.singleton(toolType));
            if (stack.isEmpty()) {
                throw new BuildingException("Tool building failed for tool type \"%s\"", toolType.getIdentifier());
            }
            TinkerCraftingEvent.ToolCraftingEvent.fireEvent(stack, null, components);
        } catch (TinkerGuiException e) {
            throw new BuildingException("Tool building produced error: %s", e.getMessage());
        }
        ItemStack stackPreMods = stack.copy();

        // add additional free modifiers
        for (int i = spec.freeMods; i > 0; i--) {
            TinkerModifiers.modCreative.apply(stack);
        }

        // apply modifiers
        for (IPair<String, Integer> modEntry : spec.modifiers) {
            IModifier mod = TinkerRegistry.getModifier(modEntry.getA());
            if (mod == null) {
                throw new BuildingException("Unknown modifier \"%s\"", modEntry.getA());
            }
            for (int i = modEntry.getB(); i > 0; i--) {
                mod.apply(stack);
            }
        }

        // finalize the tool
        try {
            TinkerCraftingEvent.ToolModifyEvent.fireEvent(stack, null, stackPreMods);
            // apply the artifact modifier after modify event because otherwise the modifier would cancel the event
            TconEvoTraits.MOD_ARTIFACT.apply(stack);
            ToolUtils.rebuildToolStack(stack);
        } catch (TinkerGuiException e) {
            throw new BuildingException("Tool modification produced error: %s", e.getMessage());
        }

        // add lore
        if (!spec.lore.isEmpty()) {
            NBTTagCompound tag = ToolUtils.getOrCreateTag(stack);
            if (tag.hasKey("display")) {
                tag = tag.getCompoundTag("display");
            } else {
                tag = new NBTTagCompound();
                tag.setTag("display", tag);
            }
            NBTTagList loreTag = new NBTTagList();
            loreTag.appendTag(new NBTTagString()); // empty line for padding
            for (String line : spec.lore) {
                loreTag.appendTag(new NBTTagString(LORE_FMT + line));
            }
            tag.setTag("Lore", loreTag);
        }

        return stack; // done!
    }

    public static class Spec {

        public final String name;
        public final List<String> lore;
        public final String toolType;
        public final List<String> materials;
        public final int freeMods;
        public final List<IPair<String, Integer>> modifiers;

        public Spec(String name, List<String> lore, String toolType, List<String> materials,
                    int freeMods, List<IPair<String, Integer>> modifiers) {
            this.name = name;
            this.lore = Collections.unmodifiableList(lore);
            this.toolType = toolType;
            this.materials = Collections.unmodifiableList(materials);
            this.freeMods = freeMods;
            this.modifiers = Collections.unmodifiableList(modifiers);
        }

    }

}
