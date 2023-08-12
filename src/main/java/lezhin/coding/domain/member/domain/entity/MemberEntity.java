package lezhin.coding.domain.member.domain.entity;

import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Gender;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.global.entity.BaseTimeEntity;
import lezhin.coding.global.exception.error.exception.ErrorCode;
import lezhin.coding.global.exception.error.exception.LikeMaxValidException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    @Id
    private Long id;
    @Embedded
    private UserName userName;
    @Embedded
    private UserEmail userEmail;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "member")
    private List<EvaluationEntity> evaluationEntities = new ArrayList<>();

    @Builder
    public MemberEntity(Long id, UserName userName, UserEmail userEmail, String password, Gender gender, Type type) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.gender = gender;
        this.type = type;
    }

    public void addEvaluationEntities(final EvaluationEntity evaluationEntity) {

        this.evaluationEntities.add(evaluationEntity);

        int MaxWorkEvaluation = 1;
        if (evaluationEntities.size() > MaxWorkEvaluation) {
            throw new LikeMaxValidException(ErrorCode.LIKE_MAX.getMessage());
        }

        evaluationEntity.updateMember(this);
    }


}
