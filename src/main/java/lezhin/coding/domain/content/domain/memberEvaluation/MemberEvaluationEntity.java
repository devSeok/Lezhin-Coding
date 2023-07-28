package lezhin.coding.domain.content.domain.memberEvaluation;


import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "memberEvaluation")
public class MemberEvaluationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private EvaluationEntity evaluation;

    public MemberEvaluationEntity(MemberEntity member, EvaluationEntity evaluation) {
        this.member = member;
        this.evaluation = evaluation;
    }
}
