package xyz.phanta.tconevo.integration.jei;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.plugin.jei.interpreter.ToolSubtypeInterpreter;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ModSensitiveToolSubtypeInterepreter extends ToolSubtypeInterpreter {

    public static final ModSensitiveToolSubtypeInterepreter INSTANCE = new ModSensitiveToolSubtypeInterepreter();

    @Override
    public String apply(ItemStack stack) {
        NBTTagList modTags = TagUtil.getModifiersTagList(stack);
        return super.apply(stack) + ":" + IntStream.range(0, modTags.tagCount())
                .mapToObj(modTags::getCompoundTagAt)
                .filter(t -> t.hasKey("identifier", Constants.NBT.TAG_STRING))
                .map(t -> t.getString("identifier"))
                .collect(Collectors.joining(","));
    }

}
