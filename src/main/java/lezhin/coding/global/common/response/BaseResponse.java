package lezhin.coding.global.common.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BaseResponse {

//    @Schema(description = "성공 여부", example = "true")
    private Boolean success;
//    @Schema(description = "결과 코드", example = "200")
    private Integer code;
//    @Schema(description = "결과 메세지", example = "ok")
    private String message;

    protected BaseResponse() {
        this.success = true;
        this.code = 200;
        this.message = "Ok";
    }

    public BaseResponse(String message) {
        this.success = true;
        this.code = 200;
        this.message = message;
    }

    protected BaseResponse(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static BaseResponse ok() {
        return new BaseResponse();
    }
}