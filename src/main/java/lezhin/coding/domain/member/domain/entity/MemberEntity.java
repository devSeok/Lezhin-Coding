package lezhin.coding.domain.member.domain.entity;

import lezhin.coding.domain.content.domain.memberEvaluation.MemberEvaluationEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Gender;
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
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberEvaluationEntity> evaluationEntities = new ArrayList<>();

    @Builder
    public MemberEntity(UserName userName, UserEmail userEmail, String password, Gender gender, Type type) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.gender = gender;
        this.type = type;
    }
}
