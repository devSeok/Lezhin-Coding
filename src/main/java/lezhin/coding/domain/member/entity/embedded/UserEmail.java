package lezhin.coding.domain.member.entity.embedded;

import javax.persistence.Column;
import javax.validation.constraints.Email;

public class UserEmail {

    @Column(name = "user_email", nullable = false, unique = true)
    @Email
    private String value;
}
