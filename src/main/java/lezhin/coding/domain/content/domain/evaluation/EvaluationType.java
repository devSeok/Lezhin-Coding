package lezhin.coding.domain.content.domain.evaluation;

import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.global.PolymorphicEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EvaluationType implements PolymorphicEnum {
    LIKE("LIKE", "좋아요"),
    BAD("BAD", "싫어요");

    private final String code;
    private final String title;

    public static EvaluationType of(String gender) {
        if(gender == null) {
            throw new IllegalArgumentException();
        }

        for (EvaluationType g : EvaluationType.values()) {
            if(g.code.equals(gender)) {
                return g;
            }
        }

        throw new IllegalArgumentException("일치하는게 없습니다");
    }
}
