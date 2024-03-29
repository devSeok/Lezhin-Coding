package lezhin.coding.domain.content.domain.content;


import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.content.domain.content.enums.PayType;
import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.global.entity.BaseTimeEntity;
import lezhin.coding.global.exception.error.exception.ContentAmountFreeVaildException;
import lezhin.coding.global.exception.error.exception.ContentAmountPayMinLimitException;
import lezhin.coding.global.exception.error.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content")
public class ContentEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    @Id
    private Long id;

    @Column(length = 500)
    private String content;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Embedded
    private Amount amount;

    @Enumerated(EnumType.STRING)
    private MinorWorkType minorWorkType;

    @OneToMany(mappedBy = "contentEntity")
    private List<EvaluationEntity> evaluationEntity = new ArrayList<>();

    @Builder
    public ContentEntity(String content, PayType payType, Amount amount, MinorWorkType minorWorkType) {
        validateAmount(payType.getCode(), amount);

        this.content = content;
        this.payType = payType;
        this.amount = amount;
        this.minorWorkType = minorWorkType;
    }

    public void updatePayTypeAndAmount(String payType, Amount amount) {
        validateAmount(payType, amount);

        this.payType = PayType.of(payType);
        this.amount = amount;
    }

    private void validateAmount(String payType, Amount amount) {
        int amountValue = amount.getValue();

        switch (payType) {
            case "FREE" :
                if (amountValue != 0) {
                    throw new ContentAmountFreeVaildException(ErrorCode.CONTENT_AMOUNT_FREE_VAILD.getMessage());
                }
                break;
            case "PAY" :
                if (amountValue < 100 || amountValue > 500) {
                    throw new ContentAmountPayMinLimitException(ErrorCode.CONTENT_AMOUNT_PAY_MIN_LIMIT.getMessage());
                }
                break;
            default:
                throw new IllegalArgumentException("유효하지 않은 payType 입니다.");
        }
    }
}
