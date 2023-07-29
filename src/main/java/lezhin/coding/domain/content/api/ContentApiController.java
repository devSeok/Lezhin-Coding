package lezhin.coding.domain.content.api;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.dto.*;
import lezhin.coding.domain.content.service.ContentService;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.global.common.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentApiController {

    private final ContentService contentService;
    private final MemberRepository memberRepository;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<ContentRegisterResDto> contentRegister(@Valid @RequestBody ContentRegisterDto dto) {

        return DataResponse.create(ContentRegisterResDto.of(contentService.contentRegister(dto)));
    }

    @PostMapping("/evaluation")
    public void evaluation(@Valid @RequestBody EvaluationReqDto dto) {


        contentService.evaluation(dto);
    }

    @GetMapping("/rank")
    public void rank() {
        contentService.sortEvaluationContent();
    }

    @PutMapping("/{contentId}/payType")
    public DataResponse<PayTypeChangeResDto> payTypeChange(
            @PathVariable("contentId") Long contentId,
            @Valid @RequestBody PayTypeChangeReqDto dto
    ) {
        return DataResponse.create(PayTypeChangeResDto.of((contentService.payTypeChange(contentId, dto))));
    }


    @PostMapping("/")
    public String test() {
        return "test";
    }

}
