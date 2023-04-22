package lezhin.coding.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MAN("M", "남자"),
    WOMAN("W", "여자");

    private String code;
    private String value;

}
