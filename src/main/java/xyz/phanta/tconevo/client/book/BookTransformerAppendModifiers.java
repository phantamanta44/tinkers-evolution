package xyz.phanta.tconevo.client.book;

import io.github.phantamanta44.libnine.util.collection.Accrue;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.mantle.client.book.data.SectionData;
import slimeknights.mantle.client.book.repository.BookRepository;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.SectionTransformer;
import slimeknights.tconstruct.library.modifiers.Modifier;
import xyz.phanta.tconevo.init.TconEvoTraits;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// adapted from Tinkers' MEMES BookTransformerAppendModifiers
public class BookTransformerAppendModifiers extends SectionTransformer {

    private final BookRepository source;
    private final boolean armour;
    private final Consumer<Accrue<Modifier>> modCollector;

    public BookTransformerAppendModifiers(BookRepository source, boolean armour, Consumer<Accrue<Modifier>> modCollector) {
        super("modifiers");
        this.source = source;
        this.armour = armour;
        this.modCollector = modCollector;
    }

    @Override
    public void transform(BookData book, SectionData section) {
        ContentListing listing = (ContentListing)section.pages.get(0).content;
        List<Modifier> mods = new ArrayList<>();
        modCollector.accept(new Accrue<>(mods));
        for (Modifier mod : mods) {
            if (!TconEvoTraits.isModifierBlacklisted(mod)) {
                PageData page = new PageData();
                page.source = source;
                page.parent = section;
                page.type = armour ? "armormodifier" : "modifier";
                page.data = "modifiers/" + mod.identifier + ".json";
                section.pages.add(page);
                page.load();
                listing.addEntry(mod.getLocalizedName(), page);
            }
        }
    }

}
