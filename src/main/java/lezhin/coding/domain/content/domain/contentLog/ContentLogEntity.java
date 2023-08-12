package lezhin.coding.domain.content.domain.contentLog;


import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.enums.PayType;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.global.common.utils.SecurityUtil;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content_log")
public class ContentLogEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_log_id")
    @Id
    private Long id;

    private Long contentId;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Embedded
    private Amount amount;

    @Column(length = 500)
    private String content;

    private Long memberId;

    @Builder
    public ContentLogEntity(Long contentId, PayType payType, Amount amount, String content, Long memberId) {
        this.contentId = contentId;
        this.payType = payType;
        this.amount = amount;
        this.content = content;
        this.memberId = memberId;
    }

    public static ContentLogEntity create(MemberEntity findMember, ContentEntity findContent) {
      return ContentLogEntity.builder()
              .contentId(findContent.getId())
              .payType(findContent.getPayType())
              .amount(findContent.getAmount())
              .content(findContent.getContent())
              .memberId(findMember.getId())
              .build();
    }
}
