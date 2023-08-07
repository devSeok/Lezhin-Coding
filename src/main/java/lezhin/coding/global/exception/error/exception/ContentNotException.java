package lezhin.coding.global.exception.error.exception;

public class ContentNotException  extends BusinessException{
    public ContentNotException(String message) {
        super(message, ErrorCode.USER_NOT_FOUND);
    }

}
