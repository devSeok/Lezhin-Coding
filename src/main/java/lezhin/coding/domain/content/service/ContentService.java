package lezhin.coding.domain.content.service;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.dto.RankResultDto;
import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import lezhin.coding.domain.content.dto.request.ContentRegisterReqDto;
import lezhin.coding.domain.content.dto.ContentResultDto;
import lezhin.coding.domain.content.dto.request.EvaluationReqDto;
import lezhin.coding.domain.content.dto.request.PayTypeChangeReqDto;
import lezhin.coding.domain.content.dto.response.ContentRegisterResDto;
import lezhin.coding.domain.content.dto.response.EvaluationRegisterResDto;

import java.util.List;

public interface ContentService {

    ContentRegisterResDto contentRegister(ContentRegisterReqDto dto);

    EvaluationRegisterResDto evaluation(EvaluationReqDto dto);


    RankResultDto sortEvaluationContent();

    List<ContentLogHistoryDto> userContentSelectList(Long contentId);

    ContentResultDto getRowContent(Long contentId);

    ContentEntity payTypeChange( Long contentId, PayTypeChangeReqDto dto);
}
