package lezhin.coding.domain.member.service;

import lezhin.coding.domain.content.domain.comment.CommentRepository;
import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.content.domain.evaluation.EvaluationRepository;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.domain.member.dto.reponse.UserWithAdultContentResDto;
import lezhin.coding.global.exception.error.exception.EntityNotFoundException;
import lezhin.coding.global.exception.error.exception.ErrorCode;
import lezhin.coding.global.exception.error.exception.UserNotException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService  {

    private final MemberRepository memberRepository;
    private final ContentLogRepository contentLogRepository;
    private final EvaluationRepository evaluationRepository;
    private final CommentRepository commentRepository;


    public List<UserWithAdultContentResDto> findUsersWithAdultContentViews(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            MinorWorkType minorWorkType
    ) {

        return memberRepository.findUsersWithAdultContentViews(startDateTime, endDateTime, minorWorkType);
    }

    @Transactional
    public void memberDelete(Long memberId) {

        MemberEntity findByMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new UserNotException(ErrorCode.USER_NOT_FOUND.getMessage()));

        contentLogRepository.deleteContentLogsByMemberId(findByMember.getId());
        evaluationRepository.deleteEvaluationsByMemberId(findByMember.getId());
        commentRepository.deleteCommentsByMemberId(findByMember.getId());

        memberRepository.deleteById(findByMember.getId());
    }
}
