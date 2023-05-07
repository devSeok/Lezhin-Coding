package lezhin.coding.domain.content.service;

import lezhin.coding.domain.content.dto.ContentRegisterDto;

public interface ContentService {

    void contentRegister(ContentRegisterDto.ContentRegisterReqDto dto);

    void contentEvaluationList();

    void contentCheckMemberList();

    void sortEvaluationContent();

    void recentlyOneWeekMemberAdultMoreThanThreeTimes();

    void PayTypeChange();
}
