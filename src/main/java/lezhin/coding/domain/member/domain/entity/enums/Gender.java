package lezhin.coding.domain.member.domain.entity.enums;


import lezhin.coding.global.PolymorphicEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender implements PolymorphicEnum {
    MAN("MAN", "남자"),
    WOMAN("WOMAN", "여자");
    private final String code;
    private final String title;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static Gender of(String gender) {
        if(gender == null) {
            throw new IllegalArgumentException();
        }

        for (Gender g : Gender.values()) {
            if(g.code.equals(gender)) {
                return g;
            }
        }

        throw new IllegalArgumentException("일치하는게 없습니다");
    }


}
