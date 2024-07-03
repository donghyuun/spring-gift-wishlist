package gift.global.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jdk.jfr.Description;

/**
 * NotContainsValue 어노테이션을 위한 유효성 검사기
 */
public class NotContainsValueValidator implements ConstraintValidator<NotContainsValue, String> {

    private String valueToAvoid;

    /**
     * 유효성 검사기 초기화
     *
     * @param constraintAnnotation (NotContainsValue 어노테이션 인스턴스)
     */
    @Override
    public void initialize(NotContainsValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.valueToAvoid = constraintAnnotation.value();
    }

    /**
     * 유효성 검사 로직
     *
     * @param value   어노테이션이 사용된 필드 또는 매개변수의 값
     * @param context 유효성 검사 과정에서 사용되는 컨텍스트를 제공하는 객체
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if (value.contains(valueToAvoid)) {
            context.disableDefaultConstraintViolation(); // 기본 유효성 검사 오류 메시지 비활성화
            context.buildConstraintViolationWithTemplate( // 커스텀 오류 메시지를 생성해 유효성 검사 메시지로 추가
                    context.getDefaultConstraintMessageTemplate())
                .addConstraintViolation(); // 유효성 검사 오류를 컨텍스트에 추가
            return false; // MethodArgumentNotValidException 발생
        }
        return true;
    }
}
