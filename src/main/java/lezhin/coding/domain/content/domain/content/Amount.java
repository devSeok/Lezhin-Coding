package lezhin.coding.domain.content.domain.content;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amount {

    @Column(name = "amount", nullable = false)
    @Range(min = 0, max = 500)
    private Integer value;

    @Builder
    public Amount(Integer value) {
        this.value = value;
    }
}
