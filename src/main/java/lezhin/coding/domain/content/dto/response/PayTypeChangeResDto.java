package lezhin.coding.domain.content.dto.response;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PayTypeChangeResDto {

    private Long id;
    private String payType;
    private Integer amount;
    private String minorWorkType;

    @Builder
    public PayTypeChangeResDto(Long id, String payType, Integer amount, String minorWorkType) {
        this.id = id;
        this.payType = payType;
        this.amount = amount;
        this.minorWorkType = minorWorkType;
    }

    public static PayTypeChangeResDto of(ContentEntity contentEntity) {
        return PayTypeChangeResDto.builder()
                .id(contentEntity.getId())
                .payType(contentEntity.getPayType().getCode())
                .amount(contentEntity.getAmount().getValue())
                .minorWorkType(contentEntity.getMinorWorkType().getCode())
                .build();

    }
}
