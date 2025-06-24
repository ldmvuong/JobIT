package fit.hcmute.JobIT.util.annotation.enumvalidate.value;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


public class EnumValueValidator implements ConstraintValidator<EnumValue, Enum<?>> {

    private Set<String> acceptedNames;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
        acceptedNames = Arrays.stream(enumClass.getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) return true; // allow null, use @NotNull if needed
        return acceptedNames.contains(value.name());
    }
}
