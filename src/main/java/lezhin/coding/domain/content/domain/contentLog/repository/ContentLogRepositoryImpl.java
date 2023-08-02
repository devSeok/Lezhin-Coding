package lezhin.coding.domain.content.domain.contentLog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lezhin.coding.domain.content.domain.content.QContentEntity;
import lezhin.coding.domain.content.domain.contentLog.QContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import lezhin.coding.domain.member.domain.entity.QMemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static lezhin.coding.domain.content.domain.content.QContentEntity.contentEntity;
import static lezhin.coding.domain.content.domain.contentLog.QContentLogEntity.contentLogEntity;
import static lezhin.coding.domain.member.domain.entity.QMemberEntity.*;

@Repository
@RequiredArgsConstructor
public class ContentLogRepositoryImpl implements ContentLogRepositoryCustom{

    private final JPAQueryFactory query;

    QContentLogEntity cl = contentLogEntity;
    QContentEntity c = contentEntity;

    QMemberEntity m = memberEntity;

    @Override
    public List<ContentLogHistoryDto> getArtworkViewHistoryByContentId(Long contentId) {

        // 현재 시간을 기준으로 최근 일주일의 시작과 끝 LocalDateTime 계산

        return query.select(
                constructor(
                        ContentLogHistoryDto.class,
                        m.userName,
                        m.userEmail,
                        c.content,
                        cl.createdDate
                ))
                .from(cl)
                .join(c).on(c.id.eq(contentId))
                .join(m).on(m.id.eq(cl.memberId))
                .orderBy(cl.id.desc())
                .fetch();

    }

    private BooleanExpression oneWeekAgo() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekAgo = now.minusWeeks(1);
        return cl.createdDate.between(oneWeekAgo, now);
    }
}
