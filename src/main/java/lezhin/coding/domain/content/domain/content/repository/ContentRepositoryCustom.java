package lezhin.coding.domain.content.domain.content.repository;

import lezhin.coding.domain.content.domain.content.dto.RankingContentResultDto;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;

import java.util.List;

public interface ContentRepositoryCustom {

    List<RankingContentResultDto> getTopRankedContentsByType(EvaluationType type, int limit);

}
