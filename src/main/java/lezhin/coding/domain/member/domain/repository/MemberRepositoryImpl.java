package lezhin.coding.domain.member.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.content.domain.content.QContentEntity;
import lezhin.coding.domain.content.domain.contentLog.QContentLogEntity;
import lezhin.coding.domain.member.domain.entity.QMemberEntity;
import lezhin.coding.domain.member.dto.reponse.UserWithAdultContentResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static lezhin.coding.domain.content.domain.content.QContentEntity.*;
import static lezhin.coding.domain.content.domain.contentLog.QContentLogEntity.*;
import static lezhin.coding.domain.member.domain.entity.QMemberEntity.*;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory query;
    QMemberEntity m = memberEntity;
    QContentLogEntity cl = contentLogEntity;
    QContentEntity content = contentEntity;

    @Override
    public List<UserWithAdultContentResDto> findUsersWithAdultContentViews(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            MinorWorkType minorWorkType
    ) {

        return query.select(Projections.constructor(
                UserWithAdultContentResDto.class,
                m.id,
                m.userName.value,
                m.userEmail.value
        )).from(m)
                .join(cl).on(m.id.eq(cl.memberId))
                .join(content).on(cl.contentId.eq(content.id))
                .where(
                        cl.createdDate.between(startDateTime, endDateTime)
                                .and(content.minorWorkType.eq(minorWorkType))
                )
                .groupBy(m.id)
                .having(cl.count().goe(3))
                .fetch();
    }
}
