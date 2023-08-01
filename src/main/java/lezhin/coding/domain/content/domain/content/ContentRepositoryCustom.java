package lezhin.coding.domain.content.domain.content;

import lezhin.coding.domain.content.domain.content.dto.TuplieResult;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;

import java.util.List;

public interface ContentRepositoryCustom {

    List<TuplieResult> likeList(EvaluationType type);
}
