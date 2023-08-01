package lezhin.coding.domain.content.domain.contentLog;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentLogRepository extends JpaRepository<ContentLogEntity, Long>, ContentLogRepositoryCustom {
}
