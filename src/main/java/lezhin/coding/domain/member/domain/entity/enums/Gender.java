package lezhin.coding.domain.member.domain.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MAN("M", "남자"),
    WOMAN("W", "여자");
    private final String code;
    private final String value;

}
