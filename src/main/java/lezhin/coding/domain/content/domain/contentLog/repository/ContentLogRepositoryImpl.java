package lezhin.coding.domain.content.domain.contentLog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lezhin.coding.domain.content.domain.content.QContentEntity;
import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.QContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import lezhin.coding.domain.member.domain.entity.QMemberEntity;
import lezhin.coding.global.config.querydsl.Querydsl4RepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static lezhin.coding.domain.content.domain.content.QContentEntity.contentEntity;
import static lezhin.coding.domain.content.domain.contentLog.QContentLogEntity.contentLogEntity;
import static lezhin.coding.domain.member.domain.entity.QMemberEntity.*;

@Repository
public class ContentLogRepositoryImpl extends Querydsl4RepositorySupport implements ContentLogRepositoryCustom{
    public ContentLogRepositoryImpl() {
        super(ContentLogEntity.class);
    }
    QContentLogEntity cl = contentLogEntity;
    QMemberEntity m = memberEntity;


    @Override
    public Page<ContentLogHistoryDto> getContentUserHistoryByContentId(Long contentId, Pageable pageable) {

        return  applyPagination(pageable, query -> query.select(
                        constructor(
                                ContentLogHistoryDto.class,
                                m.userName.value,
                                m.userEmail.value,
                                cl.content,
                                cl.createdDate
                        ))
                .from(cl)
                .join(m).on(m.id.eq(cl.memberId))
                .where(cl.contentId.eq(contentId))
                .orderBy(cl.id.desc())
        );
    }
}
