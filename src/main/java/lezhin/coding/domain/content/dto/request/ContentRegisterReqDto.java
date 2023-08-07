package lezhin.coding.domain.content.dto.request;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.global.EnumTypeValid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentRegisterReqDto {

        @NotBlank
        private String content;

        @EnumTypeValid(target = PayType.class, message = "무료 유료는 FREE or PAY 이어야합니다.")
        private String payType;

        @Valid
        private Amount amount;

        @EnumTypeValid(target = MinorWorkType.class, message = "성인 , 일반 작품값은 ADULT_WORK or GENERAL_WORK 이어야합니다.")
        private String minorWorkType;


        @Builder
        public ContentRegisterReqDto(String content, String payType, Amount amount, String minorWorkType) {
                this.content = content;
                this.payType = payType;
                this.amount = amount;
                this.minorWorkType = minorWorkType;
        }

        public ContentEntity toEntity() {
            return ContentEntity.builder()
                    .content(content)
                    .amount(amount)
                    .minorWorkType(MinorWorkType.of(minorWorkType))
                    .payType(PayType.of(payType))
                    .build();
        }
}
