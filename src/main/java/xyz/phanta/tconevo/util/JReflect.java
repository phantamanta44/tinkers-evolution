package xyz.phanta.tconevo.util;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class JReflect {

    private static final Field fLinkedHashMap_head;
    private static final Field fLinkedHashMap_tail;
    private static final Field fLinkedHashMap$Entry_before;
    private static final Field fLinkedHashMap$Entry_after;
    private static final Field fHashMap$Node_key;

    static {
        try {
            fLinkedHashMap_head = LinkedHashMap.class.getDeclaredField("head");
            fLinkedHashMap_head.setAccessible(true);
            fLinkedHashMap_tail = LinkedHashMap.class.getDeclaredField("tail");
            fLinkedHashMap_tail.setAccessible(true);
            Class<?> tLinkedHashMap$Entry = Class.forName("java.util.LinkedHashMap$Entry");
            fLinkedHashMap$Entry_before = tLinkedHashMap$Entry.getDeclaredField("before");
            fLinkedHashMap$Entry_before.setAccessible(true);
            fLinkedHashMap$Entry_after = tLinkedHashMap$Entry.getDeclaredField("after");
            fLinkedHashMap$Entry_after.setAccessible(true);
            Class<?> tHashMap$Entry = Class.forName("java.util.HashMap$Node");
            fHashMap$Node_key = tHashMap$Entry.getDeclaredField("key");
            fHashMap$Node_key.setAccessible(true);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to initialize Java reflection hacks!", e);
        }
    }

    public static <K, V> void moveLinkedHashMapEntryToFront(LinkedHashMap<K, V> materials, K expectedKey) {
        try {
            Object entry = fLinkedHashMap_tail.get(materials);
            if (entry == null || !expectedKey.equals(fHashMap$Node_key.get(entry))) {
                return;
            }
            Object entryBefore = fLinkedHashMap$Entry_before.get(entry);
            if (entryBefore == null) { // entry is at both head and tail, so we're done
                return;
            }
            // fix new tail
            fLinkedHashMap_tail.set(materials, entryBefore);
            fLinkedHashMap$Entry_after.set(entryBefore, null);
            // fix old head
            Object oldHead = fLinkedHashMap_head.get(materials);
            fLinkedHashMap$Entry_before.set(oldHead, entry);
            // fix new head
            fLinkedHashMap_head.set(materials, entry);
            fLinkedHashMap$Entry_before.set(entry, null);
            fLinkedHashMap$Entry_after.set(entry, oldHead);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to perform linked hash map hack!", e);
        }
    }

}
