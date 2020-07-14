package xyz.phanta.tconevo.client.util;

import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.tconstruct.library.book.content.ContentListing;

import java.util.List;

public class TconReflectClient {

    private static final MirrorUtils.IField<List<TextData>> fContentListing_entries
            = MirrorUtils.reflectField(ContentListing.class, "entries");

    public static List<TextData> getEntries(ContentListing listing) {
        return fContentListing_entries.get(listing);
    }

}
