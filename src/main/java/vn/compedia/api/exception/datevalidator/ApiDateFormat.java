package vn.compedia.api.exception.datevalidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author KhanhND25
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = {DateFormatValidator.class})
public @interface ApiDateFormat {

	// Date format
	public static final String DATE_FORMAT = "uuuuMMdd";

	// Message date format when error
	public static final String MSG_DATE_FORMAT = " Let's enter with pattern " + DATE_FORMAT + "!";

	String message() default MSG_DATE_FORMAT;

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String pattern();

	@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
	@Retention(RUNTIME)
	@Documented
	@interface List {
		ApiDateFormat[] pattern();
	}

}
