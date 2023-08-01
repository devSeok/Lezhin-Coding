package lezhin.coding.domain.member.api;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {


    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable("memberId") Long memberId) {

    }
}
