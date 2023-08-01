package lezhin.coding.domain.content.domain.contentLog;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ContentLogRepositoryImpl implements ContentLogRepositoryCustom{

    private final JPAQueryFactory query;
}
