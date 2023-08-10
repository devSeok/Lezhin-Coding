package lezhin.coding.domain.content.dto;

import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.content.domain.content.PayType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentResultDto {

    private Long id;
    private String content;
    private PayType payType;
    private int amount;
    private String minorWorkType;

    @Builder

    public ContentResultDto(Long id, String content, PayType payType, int amount, String minorWorkType) {
        this.id = id;
        this.content = content;
        this.payType = payType;
        this.amount = amount;
        this.minorWorkType = minorWorkType;
    }

    public static ContentResultDto of(ContentEntity content){
        return ContentResultDto.builder()
                .id(content.getId())
                .content(content.getContent())
                .payType(content.getPayType())
                .amount(content.getAmount().getValue())
                .minorWorkType(content.getMinorWorkType().getCode())
                .build();
    }
}
