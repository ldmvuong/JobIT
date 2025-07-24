package fit.hcmute.jobit.util.annotation.enumvalidate.value;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation dùng để validate giá trị kiểu Enum có nằm trong enumClass cho trước không.
 *
 * Áp dụng khi bạn dùng kiểu Enum trong DTO và vẫn muốn validate rõ ràng theo enumClass.
 *
 * Cách sử dụng:
 *   @EnumValue(name = "type", enumClass = UserType.class)
 *   private UserType type;
 *
 * Ưu điểm:
 * - Dễ tái sử dụng, không cần chuyển về String.
 * - Vẫn dùng được khi enum có nhiều logic hoặc giá trị phức tạp.
 * - Có thể mở rộng để hỗ trợ validate theo `getValue()`.
 *
 * Nhược điểm:
 * - Không kiểm tra compile-time nếu truyền sai enumClass khác kiểu field.
 * - Cần viết logic validator cẩn thận để tránh lỗi ngầm.
 *
 * @see EnumValueValidator
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {

    /**
     * Tên của field (hiển thị trong thông báo lỗi).
     */
    String name();

    /**
     * Enum class được dùng để kiểm tra hợp lệ.
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * Thông báo lỗi.
     */
    String message() default "{name} must be a valid value in {enumClass}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
