package lezhin.coding.domain.member.service;

import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.dto.MemberDto;
import lezhin.coding.domain.member.dto.MemberLoginReqDto;
import lezhin.coding.global.jwt.dto.TokenDto;

public interface AuthService {

    MemberEntity memberRegister(MemberDto.MemberRegisterReqDto memberDto);
    TokenDto login(MemberLoginReqDto memberLoginReqDto);

}
