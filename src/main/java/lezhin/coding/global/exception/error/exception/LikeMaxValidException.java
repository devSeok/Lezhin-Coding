package lezhin.coding.global.exception.error.exception;

public class LikeMaxValidException extends BusinessException{
    public LikeMaxValidException(String message) {
        super(message, ErrorCode.LIKE_MAX);
    }
}
