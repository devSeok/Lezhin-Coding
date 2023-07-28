package lezhin.coding.domain.content.domain.evaluation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EvaluationType {
    LIKE("좋아요"),
    BAD("싫어요");

    private final String text;
}
