package lezhin.coding.domain.member.api;


import lezhin.coding.domain.member.dto.MemberDto;
import lezhin.coding.domain.member.dto.MemberLoginReqDto;
import lezhin.coding.domain.member.dto.MemberLoginResDto;
import lezhin.coding.domain.member.service.MemberService;
import lezhin.coding.global.common.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final MemberService memberServiceImpl;

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<MemberDto.Res> memberSignup(
            @Valid @RequestBody final MemberDto.MemberRegisterReqDto memberDto
    ) {
        return DataResponse.create(new MemberDto.Res(memberServiceImpl.memberRegister(memberDto)));
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public DataResponse<MemberLoginResDto> login(@Valid @RequestBody MemberLoginReqDto memberLoginReqDto) {

        return DataResponse.create(MemberLoginResDto.from(memberServiceImpl.login(memberLoginReqDto)));
    }


}