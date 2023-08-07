package lezhin.coding.domain.content.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Modifying
    @Query(value = "delete from CommentEntity c where c.member.id = :memberId")
    void deleteCommentsByMemberId(@Param("memberId") Long memberId);
}
