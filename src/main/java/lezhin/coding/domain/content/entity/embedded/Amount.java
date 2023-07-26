package lezhin.coding.domain.content.entity.embedded;

import javax.persistence.Column;

public class Amount {

    @Column(name = "amount", nullable = false)
    private Integer value;
}
