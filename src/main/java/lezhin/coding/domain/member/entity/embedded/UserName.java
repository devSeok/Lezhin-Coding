package lezhin.coding.domain.member.entity.embedded;

import javax.persistence.Column;

public class UserName {
    @Column(name = "userName", nullable = false)
    private String value;
}