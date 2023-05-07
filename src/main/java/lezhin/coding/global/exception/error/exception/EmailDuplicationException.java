package lezhin.coding.global.exception.error.exception;

public class EmailDuplicationException  extends BusinessException {

    public EmailDuplicationException(String message) {
        super(message, ErrorCode.EXISTENT_EMAIL);
    }
}
