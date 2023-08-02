package lezhin.coding.domain.content.domain.evaluation;


import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "evaluation")
public class EvaluationEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id")
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ContentEntity contentEntity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberEntity member;

    @Enumerated(EnumType.STRING)
    private EvaluationType evaluationType;

    public void updateMember(MemberEntity member) {
        this.member = member;
    }

    @Builder
    public EvaluationEntity(MemberEntity member,ContentEntity contentEntity, EvaluationType evaluationType ) {
        this.evaluationType = evaluationType;
        this.member = member;
        this.contentEntity = contentEntity;
    }

    public static EvaluationEntity create(MemberEntity findMember, ContentEntity findContent, String evaluationType) {
       return EvaluationEntity.builder()
                .contentEntity(findContent)
                .member(findMember)
                .evaluationType(EvaluationType.of(evaluationType))
                .build();
    }


}
