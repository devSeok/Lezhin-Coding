package lezhin.coding.domain.content.domain.evaluation;

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
}
