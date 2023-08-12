package lezhin.coding.domain.member.api;


import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.member.dto.reponse.UserWithAdultContentResDto;
import lezhin.coding.domain.member.service.MemberService;
import lezhin.coding.global.common.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/adult/top")
    public DataResponse<List<UserWithAdultContentResDto>> findUsersWithAdultContentViews() {
        LocalDateTime startDateTime = LocalDateTime.now().minusWeeks(1);
        LocalDateTime endDateTime = LocalDateTime.now();
        MinorWorkType minorWorkType = MinorWorkType.ADULT_WORK;

        List<UserWithAdultContentResDto> topViewers = memberService.findUsersWithAdultContentViews(
                startDateTime, endDateTime, minorWorkType
        );

        return DataResponse.create(topViewers);
    }


    // 사용자 정보와 사용자의 평가 ,조회 이력 모두 삭제
    @DeleteMapping("/{memberId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteMember(@PathVariable("memberId") Long memberId) {

        memberService.memberDelete(memberId);
       return ResponseEntity.noContent().build();
    }
}
