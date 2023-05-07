package lezhin.coding.domain.content.dto;

import lezhin.coding.domain.content.domain.entity.embedded.Amount;
import lezhin.coding.domain.content.domain.entity.enums.PayType;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

public class ContentRegisterDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ContentRegisterReqDto {

        @NotBlank
        private String content;

        private PayType payType;

        private Amount amount;

        private Type type;
    }
}
