package xyz.phanta.tconevo.integration.conarm.material;

import c4.conarm.lib.materials.ArmorMaterialType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ArmourPartType {

    CORE(ArmorMaterialType.CORE),
    PLATES(ArmorMaterialType.PLATES),
    TRIM(ArmorMaterialType.TRIM),

    EXTRA(PLATES, TRIM),
    DEFAULT((String)null);

    public final List<String> typeKeys;

    ArmourPartType(String... typeKeys) {
        this.typeKeys = Arrays.asList(typeKeys);
    }

    ArmourPartType(ArmourPartType... types) {
        this.typeKeys = Arrays.stream(types).flatMap(t -> t.typeKeys.stream()).collect(Collectors.toList());
    }

}
