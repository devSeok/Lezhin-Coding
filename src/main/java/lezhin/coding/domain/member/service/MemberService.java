package lezhin.coding.domain.member.service;

import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.dto.MemberDto;

public interface MemberService {

    MemberEntity memberRegister(MemberDto.MemberRegisterReqDto memberDto);
    void memberDelete(Long memberId);
}
