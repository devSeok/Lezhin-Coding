package lezhin.coding.domain.member.entity;

import lezhin.coding.domain.member.entity.embedded.UserEmail;
import lezhin.coding.domain.member.entity.embedded.UserName;
import lezhin.coding.domain.member.entity.enums.Gender;
import lezhin.coding.domain.member.entity.enums.Type;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Type type;

}
