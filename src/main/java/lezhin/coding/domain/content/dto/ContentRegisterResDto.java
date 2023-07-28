package lezhin.coding.domain.content.dto;


import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.content.domain.content.PayType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ContentRegisterResDto {
    private Long id;
    private String payType;
    private Integer amount;
    private String minorWorkType;

    @Builder
    public ContentRegisterResDto(Long id, String payType, Integer amount, String minorWorkType) {
        this.id = id;
        this.payType = payType;
        this.amount = amount;
        this.minorWorkType = minorWorkType;
    }

    public static ContentRegisterResDto of(ContentEntity contentEntity) {
        return ContentRegisterResDto.builder()
                .id(contentEntity.getId())
                .payType(contentEntity.getPayType().getCode())
                .amount(contentEntity.getAmount().getValue())
                .minorWorkType(contentEntity.getMinorWorkType().getCode())
                .build();

    }
}
