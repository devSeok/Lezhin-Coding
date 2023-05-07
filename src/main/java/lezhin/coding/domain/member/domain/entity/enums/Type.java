package lezhin.coding.domain.member.domain.entity.enums;


import lezhin.coding.global.PolymorphicEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type implements PolymorphicEnum {
    GENERAL("GENERAL", "일반"),
    ADULT("ADULT", "성인");

    private final String code;
    private final String title;


    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getCode() {
        return code;
    }
}
