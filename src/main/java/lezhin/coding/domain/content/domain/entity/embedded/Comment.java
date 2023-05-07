package lezhin.coding.domain.content.domain.entity.embedded;

import javax.persistence.Column;

public class Comment {
    @Column(name = "content", nullable = false)
    private Integer value;
}
