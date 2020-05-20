package xyz.phanta.tconevo.material;

import java.util.Arrays;
import java.util.List;

public enum MaterialForm {

    METAL("ingot", "dust"),
    GEM("gem", "crystal");

    private final List<String> prefixes;

    MaterialForm(String... prefixes) {
        this.prefixes = Arrays.asList(prefixes);
    }

}
