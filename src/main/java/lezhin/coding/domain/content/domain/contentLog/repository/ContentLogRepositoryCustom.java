package lezhin.coding.domain.content.domain.contentLog.repository;

import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContentLogRepositoryCustom {

     Page<ContentLogHistoryDto> getContentUserHistoryByContentId(Long contentId, Pageable pageable);
}
