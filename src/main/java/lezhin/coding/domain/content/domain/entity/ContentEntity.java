package lezhin.coding.domain.content.domain.entity;


import lezhin.coding.domain.content.domain.entity.embedded.Amount;
import lezhin.coding.domain.content.domain.entity.enums.PayType;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private Type type;

    @Builder
    public ContentEntity(String content, PayType payType, Amount amount, Type type) {
        this.content = content;
        this.payType = payType;
        this.amount = amount;
        this.type = type;
    }
}
