package lezhin.coding.domain.content.domain.evaluation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRepository extends JpaRepository<EvaluationEntity, Long> {

    @Modifying
    @Query(value = "delete from EvaluationEntity e where e.member.id = :memberId")
    void deleteEvaluationsByMemberId(@Param("memberId") Long memberId);
}
