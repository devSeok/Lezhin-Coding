package lezhin.coding.domain.content.service.impl;

import lezhin.coding.domain.content.domain.repository.ContentRepository;
import lezhin.coding.domain.content.dto.ContentRegisterDto;
import lezhin.coding.domain.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class LezhinContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    @Override
    public void contentRegister(ContentRegisterDto.ContentRegisterReqDto dto) {

        contentRepository.save(dto.toEntity());
    }

    @Override
    public void contentEvaluationList() {

    }

    @Override
    public void contentCheckMemberList() {

    }

    @Override
    public void sortEvaluationContent() {

    }

    @Override
    public void recentlyOneWeekMemberAdultMoreThanThreeTimes() {

    }

    @Override
    public void PayTypeChange() {

    }
}
