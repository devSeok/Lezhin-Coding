package lezhin.coding.domain.content.domain.content.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lezhin.coding.domain.content.domain.content.QContentEntity;
import lezhin.coding.domain.content.domain.content.dto.TuplieResult;
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
    public List<TuplieResult> likeList(EvaluationType type) {
        return query.select(Projections.constructor(
                        TuplieResult.class,
                        content.id,
                        content.content,
                        eval.id.count().as("count")
                ))
                .from(content)
                .leftJoin(eval).on(content.id.eq(eval.contentEntity.id))
                .groupBy(content.id)
                .where(eval.evaluationType.eq(type))
                .limit(3)
                .orderBy(eval.id.count().desc())
                .fetch();
    }

}
