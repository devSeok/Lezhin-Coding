package lezhin.coding.domain.content.dto.response;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.enums.PayType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentResultResDto {

    private Long id;
    private String content;
    private PayType payType;
    private int amount;
    private String minorWorkType;

    @Builder
    public ContentResultResDto(Long id, String content, PayType payType, int amount, String minorWorkType) {
        this.id = id;
        this.content = content;
        this.payType = payType;
        this.amount = amount;
        this.minorWorkType = minorWorkType;
    }

    public static ContentResultResDto of(ContentEntity content){
        return ContentResultResDto.builder()
                .id(content.getId())
                .content(content.getContent())
                .payType(content.getPayType())
                .amount(content.getAmount().getValue())
                .minorWorkType(content.getMinorWorkType().getCode())
                .build();
    }
}
