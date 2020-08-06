package xyz.phanta.tconevo.client.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.model.BakedWrapper;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.client.model.BakedMaterialModel;
import slimeknights.tconstruct.library.client.model.BakedToolModel;
import slimeknights.tconstruct.library.client.model.BakedToolModelOverride;

import java.util.List;

public class TconReflectClient {

    private static final MirrorUtils.IField<List<TextData>> fContentListing_entries
            = MirrorUtils.reflectField(ContentListing.class, "entries");
    private static final MirrorUtils.IField<BakedMaterialModel[]> fBakedToolModel_parts
            = MirrorUtils.reflectField(BakedToolModel.class, "parts");
    private static final MirrorUtils.IField<BakedMaterialModel[]> fBakedToolModel_brokenParts
            = MirrorUtils.reflectField(BakedToolModel.class, "brokenParts");
    private static final MirrorUtils.IField<ImmutableList<BakedToolModelOverride>> fBakedToolModel_overrides
            = MirrorUtils.reflectField(BakedToolModel.class, "overrides");
    private static final MirrorUtils.IField<ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>> fBakedToolModel_transforms
            = MirrorUtils.reflectField(BakedToolModel.class, "transforms"); // sometimes i wish java had type aliases
    private static final MirrorUtils.IField<ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation>> fBakedWrapper$Perspective_transforms
            = MirrorUtils.reflectField(BakedWrapper.Perspective.class, "transforms");

    public static List<TextData> getEntries(ContentListing listing) {
        return fContentListing_entries.get(listing);
    }

    public static BakedMaterialModel[] getParts(BakedToolModel model) {
        return fBakedToolModel_parts.get(model);
    }

    public static BakedMaterialModel[] getBrokenParts(BakedToolModel model) {
        return fBakedToolModel_brokenParts.get(model);
    }

    public static ImmutableList<BakedToolModelOverride> getOverrides(BakedToolModel model) {
        return fBakedToolModel_overrides.get(model);
    }

    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms(BakedToolModel model) {
        return fBakedToolModel_transforms.get(model);
    }

    public static ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms(BakedWrapper.Perspective model) {
        return fBakedWrapper$Perspective_transforms.get(model);
    }

}
