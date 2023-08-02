package lezhin.coding.domain.content.domain.comment;

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
    private String value;
    @Builder
    public Comment(String value) {
        this.value = value;
    }
}
