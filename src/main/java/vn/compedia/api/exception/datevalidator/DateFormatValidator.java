/**
 *
 */
package vn.compedia.api.exception.datevalidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author KhanhND25
 *
 */
public class DateFormatValidator implements ConstraintValidator<ApiDateFormat, String> {

    protected String pattern;

    @Override
    public void initialize(ApiDateFormat constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @SuppressWarnings("unused")
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        try {
            SimpleDateFormat sdfrmt = new SimpleDateFormat(pattern);
            sdfrmt.setLenient(false);
            Date javaDate = sdfrmt.parse(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
