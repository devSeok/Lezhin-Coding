package lezhin.coding.global.exception.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    ENTITY_NOT_FOUND(404, "C003", " Entity Not Found"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),
    NOT_CONVERT_ERROR(400, "C007", "convert MultipartFile to File failed"),

    EXISTENT_EMAIL(404, "C008", "메일이 이미 존재합니다."),
    CONTENT_AMOUNT_PAY_MIN_LIMIT(400, "C009", "유료는 최소 100원부터 시작입니다."),
    CONTENT_AMOUNT_FREE_VAILD(400, "C010", "무료는 0값이어야합니다.");

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
