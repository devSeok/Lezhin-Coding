package lezhin.coding.global.exception.error.exception;

public class UserNotException extends BusinessException{
    public UserNotException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }
}
