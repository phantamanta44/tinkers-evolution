package xyz.phanta.tconevo.client.book;

import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.PageData;
import slimeknights.mantle.client.book.data.SectionData;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.gui.book.GuiBook;
import slimeknights.tconstruct.library.book.content.ContentListing;
import slimeknights.tconstruct.library.book.sectiontransformer.SectionTransformer;
import xyz.phanta.tconevo.client.util.TconReflectClient;

import java.util.List;

public class BookTransformerListingOverflow extends SectionTransformer {

    public BookTransformerListingOverflow(String sectionName) {
        super(sectionName);
    }

    @Override
    public void transform(BookData book, SectionData section) {
        if (!section.pages.isEmpty()) { // just to be safe
            PageData page = section.pages.get(0);
            if (page.content instanceof ContentListing) {
                ContentListing listing = (ContentListing)page.content;
                List<TextData> entries = TconReflectClient.getEntries(listing);
                // this math relies on some hardcoded constants in ContentListing; hopefully they don't change
                int pageSizeFirst = 2 * (int)Math.ceil((GuiBook.PAGE_HEIGHT - (listing.title != null ? 49F : 29F)) / 9F);
                if (entries.size() > pageSizeFirst) {
                    int pageSize = 2 * (int)Math.ceil((GuiBook.PAGE_HEIGHT - 29F) / 9F);
                    int index = 0;
                    for (int entryNdx = pageSizeFirst; entryNdx < entries.size(); entryNdx += pageSize) {
                        addListingPage(section, index++, entries, entryNdx, Math.min(pageSize, entries.size() - entryNdx));
                    }
                    entries.subList(pageSizeFirst, entries.size()).clear();
                }
            }
        }
    }

    private void addListingPage(SectionData section, int index, List<TextData> entries, int from, int count) {
        ContentListing listing = new ContentListing();
        List<TextData> newEntries = TconReflectClient.getEntries(listing);
        for (int i = 0; i < count; i++) {
            newEntries.add(entries.get(from + i));
        }
        PageData page = new PageData(true);
        page.source = section.source;
        page.parent = section;
        page.name = "listing_ext_" + index;
        page.content = listing;
        page.load();
        section.pages.add(index + 1, page);
    }

}
