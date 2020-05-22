package xyz.phanta.tconevo.util;

import io.github.phantamanta44.libnine.util.collection.Accrue;

import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
public interface LazyAccum<T> {

    void accumulate(Accrue<T> collector);

    default void collectTo(List<T> list) {
        accumulate(new Accrue<>(list));
    }

    default List<T> collect() {
        List<T> list = new ArrayList<>();
        collectTo(list);
        return list;
    }

}
