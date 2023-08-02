package lezhin.coding.domain.content.domain.content.repository;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepository extends JpaRepository<ContentEntity, Long>, ContentRepositoryCustom{
}
