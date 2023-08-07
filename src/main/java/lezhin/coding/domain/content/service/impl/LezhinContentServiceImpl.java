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
        amountVaild(dto.getPayType(), dto.getAmount());

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
    public RankResultDto sortEvaluationContent() {

        List<TuplieResult> topLikeList  = contentRepository.likeList(EvaluationType.LIKE);
        List<TuplieResult> topBadList  = contentRepository.likeList(EvaluationType.BAD);

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
    public ContentEntity payTypeChange(Long contentId, PayTypeChangeReqDto dto) {

        ContentEntity findContent = contentRepository.findById(contentId)
                .orElseThrow(() -> new EntityNotFoundException("컨텐츠를 찾을수 없습니다"));
        amountVaild(dto.getPayType(), dto.getAmount());
        findContent.payTypeChange(dto.getPayType(), dto.getAmount());

        return contentRepository.save(findContent);
    }


    private void amountVaild(String payType, Amount amount) {

        if (FREE.getCode().equals(payType)) {
            if (amount.getValue() != 0) {
                throw new ContentAmountFreeVaildException("무료는 0값이어야합니다.");
            }
        } else if (PAY.getCode().equals(payType)) {
            if (amount.getValue() < 100) {
                throw new ContentAmountPayMinLimitException("유료는 최소 100원부터 시작입니다.");
            }
        }
    }
}
