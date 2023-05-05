package lezhin.coding.domain.content.entity.embedded;

import javax.persistence.Column;

public class Comment {

    @Column(name = "comment", nullable = false)
    private Integer value;
}
