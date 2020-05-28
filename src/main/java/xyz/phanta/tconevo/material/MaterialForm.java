package xyz.phanta.tconevo.material;

import java.util.Arrays;
import java.util.List;

public enum MaterialForm {

    METAL("ingot", "dust", "nugget"),
    GEM("gem", "crystal"),
    SLIME_CRYSTAL("slimecrystal"),
    RAW("");

    public final List<String> prefixes;

    MaterialForm(String... prefixes) {
        this.prefixes = Arrays.asList(prefixes);
    }

    public boolean isRaw() {
        switch (this) {
            case SLIME_CRYSTAL:
            case RAW:
                return true;
            default:
                return false;
        }
    }

}
