package lezhin.coding.global;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Constraint(validatedBy = EnumTypeValid.EnumValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumTypeValid {

    String message() default "invalid parameter!!";
    Class<?>[] groups() default {};
    Class<? extends PolymorphicEnum> target();
    Class<? extends Payload>[] payload() default{};

    class EnumValidator implements ConstraintValidator<EnumTypeValid, String> {
        private List<String> types;

        @Override
        public void initialize(EnumTypeValid constraintAnnotation) {

            types = Arrays.stream(constraintAnnotation.target().getEnumConstants())
                    .map(PolymorphicEnum::getCode)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return types.contains(value);
        }


    }

}
