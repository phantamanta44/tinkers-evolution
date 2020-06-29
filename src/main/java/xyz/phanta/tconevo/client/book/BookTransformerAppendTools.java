package xyz.phanta.tconevo.client.book;

import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.mantle.client.book.data.SectionData;
import slimeknights.mantle.client.book.repository.BookRepository;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.content.ContentTool;
import slimeknights.tconstruct.library.book.sectiontransformer.SectionTransformer;
import slimeknights.tconstruct.library.tools.ToolCore;
import xyz.phanta.tconevo.util.LazyAccum;

// adapted from Tinkers' MEMES BookTransformerAppendModifiers
public class BookTransformerAppendTools extends SectionTransformer {

    private final BookRepository source;
    private final LazyAccum<ToolCore> toolCollector;

    public BookTransformerAppendTools(BookRepository source, LazyAccum<ToolCore> toolCollector) {
        super("tools");
        this.source = source;
        this.toolCollector = toolCollector;
    }

    @Override
    public void transform(BookData book, SectionData section) {
        ContentListing listing = (ContentListing)section.pages.get(0).content;
        for (ToolCore tool : toolCollector.collect()) {
            PageData page = new PageData();
            page.source = source;
            page.parent = section;
            page.type = ContentTool.ID;
            page.data = "tools/" + tool.getIdentifier() + ".json";
            section.pages.add(page);
            page.load();
            listing.addEntry(tool.getLocalizedName(), page);
        }
    }

}
