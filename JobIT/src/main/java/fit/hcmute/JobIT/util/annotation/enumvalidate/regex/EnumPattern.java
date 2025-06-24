package fit.hcmute.JobIT.util.annotation.enumvalidate.regex;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation dùng để validate giá trị enum theo biểu thức chính quy (regexp).
 *
 * Cách sử dụng:
 *   @EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")
 *   private UserStatus status;
 *
 * Ưu điểm:
 * - Tái sử dụng cho mọi enum.
 * - Không cần tạo annotation riêng cho từng enum cụ thể.
 * - Hữu ích khi muốn giới hạn tập giá trị enum được chấp nhận.
 *
 * Nhược điểm:
 * - Không kiểm tra tại compile-time, dễ sai chính tả trong regex.
 * - Không tự động cập nhật nếu enum thay đổi (thêm/xóa giá trị mới).
 * - Dễ lỗi ngầm nếu viết sai biểu thức chính quy.
 * - Không phù hợp với enum có custom value (khác `name()`).
 *
 * @see EnumPatternValidator
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Constraint(validatedBy = EnumPatternValidator.class)
public @interface EnumPattern {

    /**
     * Tên của field, dùng để hiển thị trong thông báo lỗi.
     */
    String name();

    /**
     * Regular expression định nghĩa tập giá trị enum hợp lệ (theo enum.name()).
     */
    String regexp();

    /**
     * Thông báo lỗi khi không hợp lệ.
     */
    String message() default "{name} must match {regexp}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
