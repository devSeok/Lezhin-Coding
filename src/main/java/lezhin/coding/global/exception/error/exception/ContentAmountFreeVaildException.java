package lezhin.coding.global.exception.error.exception;

public class ContentAmountFreeVaildException extends BusinessException{

    public ContentAmountFreeVaildException(String message) {
        super(message, ErrorCode.CONTENT_AMOUNT_FREE_VAILD);
    }
}
