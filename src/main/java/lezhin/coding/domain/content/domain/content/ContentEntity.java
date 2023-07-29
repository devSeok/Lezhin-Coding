package lezhin.coding.domain.content.domain.content;


import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.global.entity.BaseTimeEntity;
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
        this.content = content;
        this.payType = payType;
        this.amount = amount;
        this.minorWorkType = minorWorkType;
    }

    public void payTypeChange(String payType, Amount amount) {
        this.payType = PayType.of(payType);
        this.amount = amount;
    }
}
