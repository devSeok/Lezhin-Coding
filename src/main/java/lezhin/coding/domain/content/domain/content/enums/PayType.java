package lezhin.coding.domain.content.domain.content.enums;


import lezhin.coding.global.PolymorphicEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayType implements PolymorphicEnum {
    FREE("FREE", "무료"),
    PAY("PAY", "유료");

    private final String code;
    private final String title;


    public static PayType of(String gender) {
        if(gender == null) {
            throw new IllegalArgumentException();
        }

        for (PayType g : PayType.values()) {
            if(g.code.equals(gender)) {
                return g;
            }
        }

        throw new IllegalArgumentException("일치하는게 없습니다");
    }
}
