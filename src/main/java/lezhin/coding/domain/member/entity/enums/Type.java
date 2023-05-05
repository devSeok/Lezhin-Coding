package lezhin.coding.domain.member.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    GENERAL("general", "일반"),
    ADULT("adult", "성인");

    private final String code;
    private final String value;

}
