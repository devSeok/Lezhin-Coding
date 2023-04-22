package lezhin.coding.domain.user.entity.embedded;

import javax.persistence.Column;

public class UserEmail {

    @Column(name = "userEmail", nullable = false)
    private String value;
}
