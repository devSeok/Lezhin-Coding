package lezhin.coding.domain.member.domain.entity.embedded;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;


@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserName {
    @Column(name = "userName", nullable = false)
    @NotEmpty
    private String value;

    @Builder
    public UserName(String value) {
        this.value = value;
    }
}
