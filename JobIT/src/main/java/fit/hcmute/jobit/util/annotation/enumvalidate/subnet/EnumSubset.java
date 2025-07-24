package fit.hcmute.jobit.util.annotation.enumvalidate.subnet;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation dùng để validate giá trị Enum có nằm trong tập cho phép hay không.
 *
 * Phù hợp khi bạn muốn giới hạn Enum chỉ được phép nhận một số giá trị cụ thể (subset).
 *
 * Cách sử dụng:
 *   @EnumSubset(enumClass = Gender.class, anyOf = {"MALE", "FEMALE"})
 *   private Gender gender;
 *
 * Ưu điểm:
 * - Tái sử dụng được cho nhiều enum khác nhau (generic).
 * - Cho phép kiểm soát tập giá trị hợp lệ của Enum.
 * - Dễ mở rộng, không phụ thuộc vào thứ tự khai báo enum.
 *
 * Nhược điểm:
 * - Cần truyền đúng tên `enum.name()` dưới dạng chuỗi → dễ sai chính tả nếu viết tay.
 * - Không kiểm tra được tại compile-time.
 * - Không hỗ trợ enum có `value()` tùy chỉnh (trừ khi viết thêm logic).
 *
 * @see EnumSubsetValidator
 */
@Documented
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumSubsetValidator.class)
public @interface EnumSubset {

    /**
     * Enum class được sử dụng để kiểm tra giá trị.
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * Danh sách các tên enum hợp lệ (tùy chọn).
     * Nếu không truyền, mặc định sẽ lấy toàn bộ Enum.name().
     */
    String[] anyOf() default {};

    String message() default "must be any of {anyOf}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
