package xyz.phanta.tconevo.util;

import com.google.common.collect.Lists;
import net.minecraft.util.NonNullList;

import java.util.Collections;
import java.util.List;

public class CollectionUtils {

    private static final NonNullList<?> EMPTY_NN_LIST = wrapNonnull(Collections.emptyList());

    public static <T> NonNullList<T> wrapNonnull(List<T> delegate) {
        return new DelegatingNonNullList<>(delegate);
    }

    @SuppressWarnings("unchecked")
    public static <T> NonNullList<T> emptyNonnullList() {
        return (NonNullList<T>)EMPTY_NN_LIST;
    }

    public static <T> NonNullList<T> nonnullListOf(T element) {
        return wrapNonnull(Collections.singletonList(element));
    }

    public static <T> NonNullList<T> nonnullListOf(T first, T... elements) {
        return wrapNonnull(Lists.asList(first, elements)); // extremely convenient that this exists :D
    }

    private static class DelegatingNonNullList<T> extends NonNullList<T> {

        DelegatingNonNullList(List<T> delegate) {
            super(delegate, null);
        }

    }

}
