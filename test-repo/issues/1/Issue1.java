package foo;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BarValidator.class)
public interface Issue1 {
    String message() default "{bar}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
