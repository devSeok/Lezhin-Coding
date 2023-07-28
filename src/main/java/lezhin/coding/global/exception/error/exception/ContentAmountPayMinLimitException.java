package lezhin.coding.global.exception.error.exception;

public class ContentAmountPayMinLimitException extends BusinessException{
    public ContentAmountPayMinLimitException(String message) {
        super(message, ErrorCode.CONTENT_AMOUNT_PAY_MIN_LIMIT);
    }
}
