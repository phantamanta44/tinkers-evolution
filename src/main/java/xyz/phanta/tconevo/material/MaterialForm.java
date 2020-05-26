package xyz.phanta.tconevo.material;

import java.util.Arrays;
import java.util.List;

public enum MaterialForm {

    METAL("ingot", "dust", "nugget"),
    GEM("gem", "crystal"),
    RAW("");

    public final List<String> prefixes;

    MaterialForm(String... prefixes) {
        this.prefixes = Arrays.asList(prefixes);
    }

}
