package xyz.phanta.tconevo.util;

import java.lang.annotation.*;

// exists to indicate that a method or field is reached using reflection
// mostly just to get code inspections to run more smoothly
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Reflected {
    // NO-OP
}
