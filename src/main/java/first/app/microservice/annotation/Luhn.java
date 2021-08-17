package first.app.microservice.annotation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LuhnNumberValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Luhn {

    String message() default "Invalid Code!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

