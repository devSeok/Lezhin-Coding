package lezhin.coding.domain.content.domain.content.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lezhin.coding.domain.content.domain.content.QContentEntity;
import lezhin.coding.domain.content.domain.content.dto.RankingContentResultDto;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;
import lezhin.coding.domain.content.domain.evaluation.QEvaluationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static lezhin.coding.domain.content.domain.content.QContentEntity.contentEntity;
import static lezhin.coding.domain.content.domain.evaluation.QEvaluationEntity.evaluationEntity;

@Repository
@RequiredArgsConstructor
public class ContentRepositoryImpl implements ContentRepositoryCustom{

    private final JPAQueryFactory query;

    QContentEntity content = contentEntity;
    QEvaluationEntity eval = evaluationEntity;

    @Override
    public List<RankingContentResultDto> getTopRankedContentsByType(EvaluationType type, int limit) {
        return query.select(Projections.constructor(
                        RankingContentResultDto.class,
                        content.id,
                        content.content,
                        eval.id.count().as("count")
                ))
                .from(content)
                .leftJoin(eval).on(content.id.eq(eval.contentEntity.id))
                .groupBy(content.id)
                .where(eval.evaluationType.eq(type))
                .limit(limit)
                .orderBy(eval.id.count().desc())
                .fetch();
    }

}
