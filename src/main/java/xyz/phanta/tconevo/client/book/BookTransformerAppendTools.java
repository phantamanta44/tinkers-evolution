//package xyz.phanta.tconevo.client.book;
//
//import slimeknights.mantle.client.book.data.BookData;
//import slimeknights.mantle.client.book.data.PageData;
//import slimeknights.mantle.client.book.data.SectionData;
//import slimeknights.mantle.client.book.repository.BookRepository;
//import slimeknights.tconstruct.library.book.content.ContentListing;
//import slimeknights.tconstruct.library.book.content.ContentTool;
//import slimeknights.tconstruct.library.book.sectiontransformer.SectionTransformer;
//import slimeknights.tconstruct.library.modifiers.Modifier;
//import slimeknights.tconstruct.library.tools.ToolCore;
//import xyz.phanta.tconevo.init.TconEvoTraits;
//import xyz.phanta.tconevo.integration.conarm.ConArmHooks;
//import xyz.phanta.tconevo.util.LazyAccum;
//import xyz.phanta.tconevo.util.TconReflect;
//
//// adapted from Tinkers' MEMES BookTransformerAppendModifiers
//public class BookTransformerAppendTools extends SectionTransformer {
//
//    private final BookRepository source;
//    private final LazyAccum<ToolCore> toolCollector;
//
//    public BookTransformerAppendTools(BookRepository source, boolean armour, LazyAccum<ToolCore> toolCollector) {
//        super("tools");
//        this.source = source;
//        this.toolCollector = toolCollector;
//    }
//
//    @Override
//    public void transform(BookData book, SectionData section) {
//        ContentListing listing = (ContentListing)section.pages.get(0).content;
//        for (ToolCore tool : toolCollector.collect()) {
//            if (TconEvoTraits.isModifierEnabled(mod)) {
//                PageData page = new PageData();
//                page.source = source;
//                page.parent = section;
//                page.type = ContentTool.ID;
//                page.data = "modifiers/" + mod.identifier + ".json";
//                section.pages.add(page);
//                page.load();
//                listing.addEntry(mod.getLocalizedName(), page);
//            }
//        }
//    }
//
//}
