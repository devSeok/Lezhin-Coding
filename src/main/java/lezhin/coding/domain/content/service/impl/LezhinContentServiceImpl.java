package lezhin.coding.domain.content.service.impl;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.ContentRepository;
import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.domain.content.dto.ContentRegisterDto;
import lezhin.coding.domain.content.service.ContentService;
import lezhin.coding.global.exception.error.exception.ContentAmountFreeVaildException;
import lezhin.coding.global.exception.error.exception.ContentAmountPayMinLimitException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import static lezhin.coding.domain.content.domain.content.PayType.*;

@Service
@Primary
@RequiredArgsConstructor
public class LezhinContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    @Override
    public ContentEntity contentRegister(ContentRegisterDto dto) {
        amountVaild(dto);

        return contentRepository.save(dto.toEntity());
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


    private void amountVaild(ContentRegisterDto dto) {
        if (dto.getPayType().equals(FREE.getCode())) {

            if (dto.getAmount().getValue() != 0) {
                throw new ContentAmountFreeVaildException("무료는 0값이어야합니다.");
            }

        } else if (dto.getPayType().equals(PAY.getCode())) {

            if (dto.getAmount().getValue() <= 100) {
                throw new ContentAmountPayMinLimitException("유료는 최소 100원부터 시작입니다.");
            }
        }
    }
}
