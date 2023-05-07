package lezhin.coding.domain.member.api;


import lezhin.coding.domain.member.dto.MemberDto;
import lezhin.coding.domain.member.service.MemberService;
import lezhin.coding.global.exception.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService MemberServiceImpl;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResponseDto<MemberDto.Res>> memberRegister(
            @Valid @RequestBody final MemberDto.MemberRegisterReqDto memberDto
    ) {
        return ResponseEntity.created(URI.create("member"))
                .body(
                        ApiResponseDto.<MemberDto.Res>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(new MemberDto.Res(MemberServiceImpl.memberRegister(memberDto)))
                                .build()
                );
    }
}
