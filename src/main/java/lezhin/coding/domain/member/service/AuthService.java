package lezhin.coding.domain.member.service;

import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.dto.reponse.MemberLoginResDto;
import lezhin.coding.domain.member.dto.reponse.MemberSignupResDto;
import lezhin.coding.domain.member.dto.request.MemberLoginReqDto;
import lezhin.coding.domain.member.dto.request.MemberSignupReqDto;
import lezhin.coding.global.jwt.dto.TokenDto;

public interface AuthService {

    MemberSignupResDto memberSignup(MemberSignupReqDto memberDto);
    MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto);

}
