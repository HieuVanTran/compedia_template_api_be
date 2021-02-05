package vn.compedia.api.logging;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Loggable {


    boolean result() default true;

    Level level() default Level.INFO;

    enum Level {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }
}
