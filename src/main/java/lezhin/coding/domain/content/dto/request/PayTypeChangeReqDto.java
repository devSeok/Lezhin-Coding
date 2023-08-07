package lezhin.coding.domain.content.dto.request;


import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.global.EnumTypeValid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PayTypeChangeReqDto {

    @EnumTypeValid(target = PayType.class, message = "무료 유료는 FREE or PAY 이어야합니다.")
    private String payType;

    @Valid
    private Amount amount;
}
