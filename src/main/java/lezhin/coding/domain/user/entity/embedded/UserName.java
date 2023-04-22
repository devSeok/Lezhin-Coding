package lezhin.coding.domain.user.entity.embedded;

import javax.persistence.Column;

public class UserName {
    @Column(name = "userName", nullable = false)
    private String value;
}
