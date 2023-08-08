package lezhin.coding.domain.content.service.impl;

import lezhin.coding.domain.content.domain.comment.CommentRepository;
import lezhin.coding.domain.content.domain.comment.CommentEntity;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.content.domain.content.dto.RankResultDto;
import lezhin.coding.domain.content.domain.content.dto.TuplieResult;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;
import lezhin.coding.domain.content.dto.request.ContentRegisterReqDto;
import lezhin.coding.domain.content.dto.ContentResultDto;
import lezhin.coding.domain.content.dto.request.EvaluationReqDto;
import lezhin.coding.domain.content.dto.request.PayTypeChangeReqDto;
import lezhin.coding.domain.content.dto.response.ContentRegisterResDto;
import lezhin.coding.domain.content.dto.response.EvaluationRegisterResDto;
import lezhin.coding.domain.content.dto.response.PayTypeChangeResDto;
import lezhin.coding.domain.content.service.ContentService;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.global.common.utils.SecurityUtil;
import lezhin.coding.global.event.content.ContentEvent;
import lezhin.coding.global.exception.error.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static lezhin.coding.domain.content.domain.content.PayType.*;

@Service
@Primary
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LezhinContentServiceImpl implements ContentService {

    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ContentLogRepository contentLogRepository;
    @Override
    @Transactional
    public ContentRegisterResDto contentRegister(ContentRegisterReqDto dto) {
        validateAmount(dto.getPayType(), dto.getAmount());

        return ContentRegisterResDto.of(contentRepository.save(dto.toEntity()));
    }

    @Override
    @Transactional
    public EvaluationRegisterResDto evaluation(EvaluationReqDto dto) {

        MemberEntity findMember = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new UserNotException("유저 정보가 없습니다."));

        ContentEntity findContent = contentRepository.findById(dto.getContentId())
                        .orElseThrow(() -> new ContentNotException("컨텐츠 정보가 없습니다."));

        EvaluationEntity evaluation = EvaluationEntity.create(findMember, findContent, dto.getEvaluationType());
        findMember.addEvaluationEntities(evaluation);
        memberRepository.save(findMember);

        CommentEntity comments = CommentEntity.create(findContent, findMember, dto.getComment());

        return EvaluationRegisterResDto.of(commentRepository.save(comments));
    }

    @Override
    public RankResultDto sortEvaluationContent(int limit) {

        List<TuplieResult> topLikeList  = contentRepository.likeList(EvaluationType.LIKE, limit);
        List<TuplieResult> topBadList  = contentRepository.likeList(EvaluationType.BAD, limit);

       return RankResultDto.of(topLikeList, topBadList);
    }

    @Override
    public List<ContentLogHistoryDto> userContentSelectList(Long contentId) {

        return contentLogRepository.getArtworkViewHistoryByContentId(contentId);
    }

    @Override
    public ContentResultDto getRowContent(Long contentId) {
        ContentEntity findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new EntityNotFoundException("컨텐츠 값이 없습니다."));

        applicationEventPublisher.publishEvent(ContentEvent.ContentHistory.of(contentId));

        return ContentResultDto.of(findContent);
    }

    @Override
    @Transactional
    public PayTypeChangeResDto payTypeChange(Long contentId, PayTypeChangeReqDto dto) {

        ContentEntity findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new EntityNotFoundException("컨텐츠를 찾을수 없습니다"));
        validateAmount(dto.getPayType(), dto.getAmount());
        findContent.payTypeChange(dto.getPayType(), dto.getAmount());

        return PayTypeChangeResDto.of(contentRepository.save(findContent));
    }


    private void validateAmount(String payType, Amount amount) {
        int amountValue = amount.getValue();

        switch (payType) {
            case "FREE" :
                if (amountValue != 0) {
                    throw new ContentAmountFreeVaildException("무료는 0값이어야합니다.");
                }
                break;
            case "PAY" :
                if (amountValue < 100 || amountValue > 500) {
                    throw new ContentAmountPayMinLimitException("유료는 100원~500원 값이어야 합니다.");
                }
                break;
            default:
                throw new IllegalArgumentException("유효하지 않은 payType 입니다.");
        }
    }
}
