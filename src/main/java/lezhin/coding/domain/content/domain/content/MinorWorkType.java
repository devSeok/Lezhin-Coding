package lezhin.coding.domain.content.domain.content;

import lezhin.coding.global.PolymorphicEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MinorWorkType implements PolymorphicEnum {
    ADULT_WORK("ADULT_WORK", "성인 작품"),
    GENERAL_WORK("GENERAL_WORK", "일반 작품");

    private final String code;
    private final String title;


    public static MinorWorkType of(String gender) {
        if(gender == null) {
            throw new IllegalArgumentException();
        }

        for (MinorWorkType g : MinorWorkType.values()) {
            if(g.code.equals(gender)) {
                return g;
            }
        }

        throw new IllegalArgumentException("일치하는게 없습니다");
    }
}
