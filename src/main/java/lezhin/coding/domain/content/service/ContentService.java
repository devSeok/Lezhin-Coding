package lezhin.coding.domain.content.service;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.dto.ContentRegisterDto;
import lezhin.coding.domain.content.dto.EvaluationReqDto;
import lezhin.coding.domain.content.dto.PayTypeChangeReqDto;

public interface ContentService {

    ContentEntity contentRegister(ContentRegisterDto dto);

    void evaluation(EvaluationReqDto dto);

    void contentEvaluationList();

    void contentCheckMemberList();

    void sortEvaluationContent();

    void recentlyOneWeekMemberAdultMoreThanThreeTimes();

    ContentEntity payTypeChange( Long contentId, PayTypeChangeReqDto dto);
}
