package lezhin.coding.domain.content.domain.comment;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Column(name = "comment", nullable = false, length = 100)
    @Max(value = 100, message = "100자 이상은 작성할수 없습니다.")
    private String value;
    @Builder
    public Comment(String value) {
        this.value = value;
    }
}
