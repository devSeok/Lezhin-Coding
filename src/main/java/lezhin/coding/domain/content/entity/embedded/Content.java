package lezhin.coding.domain.content.entity.embedded;

import javax.persistence.Column;

public class Content {
    @Column(name = "content", nullable = false)
    private Integer value;
}
