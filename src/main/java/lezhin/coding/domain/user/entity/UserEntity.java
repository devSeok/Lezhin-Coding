package lezhin.coding.domain.user.entity;

import lezhin.coding.domain.user.entity.embedded.UserEmail;
import lezhin.coding.domain.user.entity.embedded.UserName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user")
public class UserEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    @Id
    private Long id;

    @Embedded
    private UserName userName;
    @Embedded
    private UserEmail userEmail;

    private Gender gender;
    private String type;

}
