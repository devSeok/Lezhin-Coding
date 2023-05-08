package lezhin.coding.domain.content.domain.repository;

import lezhin.coding.domain.content.domain.entity.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, Long> {
}
