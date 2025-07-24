package fit.hcmute.jobit.util.annotation.enumvalidate.subnet;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EnumSubsetValidator implements ConstraintValidator<EnumSubset, Enum<?>> {

    private final Set<String> allowedNames = new HashSet<>();

    @Override
    public void initialize(EnumSubset constraintAnnotation) {
        if (constraintAnnotation.anyOf().length > 0) {
            allowedNames.addAll(Arrays.asList(constraintAnnotation.anyOf()));
        } else {
            // Nếu không chỉ định anyOf → lấy tất cả giá trị enum
            Class<? extends Enum<?>> enumClass = constraintAnnotation.enumClass();
            for (Enum<?> constant : enumClass.getEnumConstants()) {
                allowedNames.add(constant.name());
            }
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        // Cho phép null (optional nếu muốn kết hợp với @NotNull để kiểm tra bắt buộc)
        if (value == null) return true;
        return allowedNames.contains(value.name());
    }
}