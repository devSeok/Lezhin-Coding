package lezhin.coding.domain.member.api;



import lezhin.coding.domain.member.dto.reponse.MemberSignupResDto;
import lezhin.coding.domain.member.dto.request.MemberLoginReqDto;
import lezhin.coding.domain.member.dto.reponse.MemberLoginResDto;
import lezhin.coding.domain.member.dto.request.MemberSignupReqDto;
import lezhin.coding.domain.member.service.AuthService;
import lezhin.coding.global.common.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authServiceImpl;

    @PostMapping("/signup")
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<MemberSignupResDto> memberSignup(
            @Valid @RequestBody final MemberSignupReqDto memberDto
    ) {
        return DataResponse.create(authServiceImpl.memberSignup(memberDto));
    }

    @PostMapping("/login")
    @ResponseStatus(value = HttpStatus.OK)
    public DataResponse<MemberLoginResDto> login(@Valid @RequestBody MemberLoginReqDto memberLoginReqDto) {

        return DataResponse.create(authServiceImpl.login(memberLoginReqDto));
    }


}