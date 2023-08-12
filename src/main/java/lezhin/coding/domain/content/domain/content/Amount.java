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
import javax.validation.constraints.PositiveOrZero;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amount {

    @Column(name = "amount", nullable = false)
    @PositiveOrZero
    private Integer value;

    @Builder
    public Amount(Integer value) {
        this.value = value;
    }
}
