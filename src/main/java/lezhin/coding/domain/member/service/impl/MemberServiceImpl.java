package lezhin.coding.domain.member.service.impl;

import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.content.domain.evaluation.EvaluationRepository;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.domain.member.dto.UserWithAdultContentResDto;
import lezhin.coding.domain.member.service.MemberService;
import lezhin.coding.global.exception.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final ContentLogRepository contentLogRepository;
    private final EvaluationRepository evaluationRepository;

    @Override
    public List<UserWithAdultContentResDto> findUsersWithAdultContentViews() {

        LocalDateTime startDateTime = LocalDateTime.now().minusWeeks(1);
        LocalDateTime endDateTime = LocalDateTime.now();
        MinorWorkType minorWorkType = MinorWorkType.ADULT_WORK;

        return memberRepository.findUsersWithAdultContentViews(startDateTime, endDateTime, minorWorkType);
    }

    @Override
    public void memberDelete(Long memberId) {

        MemberEntity findByMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("유저 값이 없습니다."));

        contentLogRepository.deleteContentLogsByMemberId(findByMember.getId());
        evaluationRepository.deleteEvaluationsByMemberId(findByMember.getId());
        memberRepository.deleteById(findByMember.getId());
    }
}
