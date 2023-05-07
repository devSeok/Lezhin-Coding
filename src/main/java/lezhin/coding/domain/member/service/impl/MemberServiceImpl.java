package lezhin.coding.domain.member.service.impl;

import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.domain.member.dto.MemberDto;
import lezhin.coding.domain.member.service.MemberService;
import lezhin.coding.global.exception.error.exception.EmailDuplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    @Override
    public MemberEntity memberRegister(MemberDto.MemberRegisterReqDto memberDto) {

        if (isExistedEmail(memberDto.getUserEmail()))
            throw new EmailDuplicationException("djqtse");

        return memberRepository.save(memberDto.toEntity());
    }

    @Transactional(readOnly = true)
    public boolean isExistedEmail(UserEmail email) {
        return memberRepository.findByUserEmail(email) != null;
    }

    @Override
    public void memberDelete(Long memberId) {

    }
}
