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
public @interface EnumTypeValid {

    String message() default "invalid parameter!!";
    Class<?>[] groups() default {};
    Class<? extends PolymorphicEnum> target();
    Class<? extends Payload>[] payload() default{};

    class EnumValidator implements ConstraintValidator<EnumTypeValid, String> {
        private List<String> types;

        @Override
        public void initialize(EnumTypeValid constraintAnnotation) {
            System.out.println("ssssssssssssssss");
            types = Arrays.stream(constraintAnnotation.target().getEnumConstants())
                    .map(PolymorphicEnum::getCode)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            System.out.println("ffffffffff");
            return types.contains(value);
        }


    }

}
