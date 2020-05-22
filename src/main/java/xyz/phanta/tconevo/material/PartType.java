package xyz.phanta.tconevo.material;

import slimeknights.tconstruct.library.materials.MaterialTypes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PartType {

    HEAD(MaterialTypes.HEAD),
    HANDLE(MaterialTypes.HANDLE),
    EXTRA(MaterialTypes.EXTRA),

    BOW(MaterialTypes.BOW),
    BOWSTRING(MaterialTypes.BOWSTRING),

    PROJECTILE(MaterialTypes.PROJECTILE),
    SHAFT(MaterialTypes.SHAFT),
    FLETCHING(MaterialTypes.FLETCHING),

    DEFAULT((String)null),
    TOOL(HEAD, HANDLE, EXTRA, BOW, BOWSTRING, PROJECTILE, SHAFT, FLETCHING);

    public final List<String> typeKeys;

    PartType(String... typeKeys) {
        this.typeKeys = Arrays.asList(typeKeys);
    }

    PartType(PartType... types) {
        this.typeKeys = Arrays.stream(types).flatMap(t -> t.typeKeys.stream()).collect(Collectors.toList());
    }

    }
