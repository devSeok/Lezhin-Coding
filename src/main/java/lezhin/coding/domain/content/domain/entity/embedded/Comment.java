package lezhin.coding.domain.content.domain.entity.embedded;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Column(name = "content", nullable = false)
    private Integer value;
    @Builder
    public Comment(Integer value) {
        this.value = value;
    }
}
