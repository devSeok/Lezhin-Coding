package lezhin.coding.global.exception.error.exception;

public class LikeMaxVaildException extends BusinessException{
    public LikeMaxVaildException(String message) {
        super(message, ErrorCode.LIKE_MAX);
    }
}
