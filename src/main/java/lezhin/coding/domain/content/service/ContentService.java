package lezhin.coding.domain.content.service;

import lezhin.coding.domain.content.domain.comment.CommentRepository;
import lezhin.coding.domain.content.domain.comment.CommentEntity;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.content.dto.response.RankingResultResDto;
import lezhin.coding.domain.content.domain.content.dto.RankingContentResultDto;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.domain.content.domain.evaluation.EvaluationRepository;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;
import lezhin.coding.domain.content.dto.request.ContentRegisterReqDto;
import lezhin.coding.domain.content.dto.response.ContentResultResDto;
import lezhin.coding.domain.content.dto.request.EvaluationReqDto;
import lezhin.coding.domain.content.dto.request.PayTypeChangeReqDto;
import lezhin.coding.domain.content.dto.response.ContentRegisterResDto;
import lezhin.coding.domain.content.dto.response.EvaluationRegisterResDto;
import lezhin.coding.domain.content.dto.response.PayTypeChangeResDto;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.global.common.utils.SecurityUtil;
import lezhin.coding.global.event.content.ContentEvent;
import lezhin.coding.global.exception.error.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentService {

    private final ContentRepository contentRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ContentLogRepository contentLogRepository;
    private final EvaluationRepository evaluationRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public ContentRegisterResDto registerContent(ContentRegisterReqDto dto) {
        ContentEntity insertContent = contentRepository.save(dto.toEntity());

        return ContentRegisterResDto.of(insertContent);
    }


    @Transactional
    public EvaluationRegisterResDto submitRating(EvaluationReqDto evaluationRequest) {

        MemberEntity findMember = findMemberEntity();
        ContentEntity findContent = findContentEntity(evaluationRequest.getContentId());

        EvaluationEntity evaluation = EvaluationEntity.create(findMember, findContent, evaluationRequest.getEvaluationType());
        findMember.addEvaluationEntities(evaluation);
        evaluationRepository.save(evaluation);

        CommentEntity newComments = CommentEntity.create(findContent, findMember, evaluationRequest.getComment());

        return EvaluationRegisterResDto.of(commentRepository.save(newComments));
    }

    public RankingResultResDto getTopRankedContents(int limit) {

        List<RankingContentResultDto> topLikedContents  = contentRepository
                .getTopRankedContentsByType(EvaluationType.LIKE, limit);

        List<RankingContentResultDto> topBadContents   = contentRepository
                .getTopRankedContentsByType(EvaluationType.BAD, limit);

       return RankingResultResDto.of(topLikedContents, topBadContents);
    }

    public Page<ContentLogHistoryDto> getContentUserHistory(Long contentId, Pageable pageable) {

        return contentLogRepository.getContentUserHistoryByContentId(contentId, pageable);
    }

    public ContentResultResDto getContentById(Long contentId) {
        ContentEntity findContent = findContentEntity(contentId);

        applicationEventPublisher.publishEvent(ContentEvent.ContentHistory.of(contentId));

        return ContentResultResDto.of(findContent);
    }


    @Transactional
    public PayTypeChangeResDto changeContentPayType(Long contentId, PayTypeChangeReqDto paymentTypeRequest) {

        ContentEntity findContent = findContentEntity(contentId);

        findContent.updatePayTypeAndAmount(paymentTypeRequest.getPayType(), paymentTypeRequest.getAmount());
        ContentEntity updatedContent  = contentRepository.save(findContent);

        return PayTypeChangeResDto.of(updatedContent);
    }

    private MemberEntity findMemberEntity() {
        return memberRepository.findByUserEmail(SecurityUtil.getCurrentMemberEmail())
                .orElseThrow(() -> new UserNotException(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    private ContentEntity findContentEntity(Long contentId) {
        return contentRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotException(ErrorCode.CONTENT_NOT_FOUND.getMessage()));
    }
}
