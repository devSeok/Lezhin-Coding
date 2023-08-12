package lezhin.coding.domain.content.domain.contentLog.repository;

import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;

import java.util.List;

public interface ContentLogRepositoryCustom {

     List<ContentLogHistoryDto> getContentUserHistoryByContentId(Long contentId);
}
