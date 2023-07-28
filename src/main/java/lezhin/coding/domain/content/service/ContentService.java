package lezhin.coding.domain.content.service;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.dto.ContentRegisterDto;

public interface ContentService {

    ContentEntity contentRegister(ContentRegisterDto dto);

    void contentEvaluationList();

    void contentCheckMemberList();

    void sortEvaluationContent();

    void recentlyOneWeekMemberAdultMoreThanThreeTimes();

    void PayTypeChange();
}
