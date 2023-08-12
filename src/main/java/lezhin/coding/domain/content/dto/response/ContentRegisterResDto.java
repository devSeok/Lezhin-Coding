package lezhin.coding.domain.content.dto.response;


import lezhin.coding.domain.content.domain.content.ContentEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
