package lezhin.coding.domain.member.controller;


import lezhin.coding.domain.member.dto.MemberDto;
import lezhin.coding.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService MemberServiceImpl;

    @PostMapping
    public MemberDto.Res memberRegister(@RequestBody @Valid final MemberDto.MemberRegisterReqDto memberDto) {
        return new MemberDto.Res(MemberServiceImpl.memberRegister(memberDto));
    }
}
