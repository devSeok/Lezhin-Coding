package lezhin.coding.domain.content.domain.contentLog.repository;

import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentLogRepository extends JpaRepository<ContentLogEntity, Long>, ContentLogRepositoryCustom {


    @Modifying
    @Query(value = "delete from ContentLogEntity cl where cl.memberId = :memberId")
    void deleteContentLogsByMemberId(@Param("memberId") Long memberId);
}
