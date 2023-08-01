package lezhin.coding.domain.content.api;

import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.dto.RankResultDto;
import lezhin.coding.domain.content.dto.*;
import lezhin.coding.domain.content.service.ContentService;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.global.common.response.DataResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentApiController {

    private final ContentService contentService;

    @GetMapping("/{contentId}")
    public DataResponse<ContentResultDto> getRowContent(@PathVariable("contentId") Long contentId) {

        return DataResponse.create(contentService.getRowContent(contentId));
    }

    // 작품 저장
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<ContentRegisterResDto> contentRegister(@Valid @RequestBody ContentRegisterDto dto) {

        return DataResponse.create(ContentRegisterResDto.of(contentService.contentRegister(dto)));
    }

    // 작품 평가
    @PostMapping("/evaluation")
    public void evaluation(@Valid @RequestBody EvaluationReqDto dto) {


        contentService.evaluation(dto);
    }


    // 좋아요가 가장 많은 작품 3개와 싫어요가 가장 만은 작품 3개를 조회하는 API
    @GetMapping("/rank")
    public DataResponse<RankResultDto> rank() {

        return DataResponse.create(contentService.sortEvaluationContent());
    }

    // 특정 작품을 유료 ,무료로 변경할 수 있는 api
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
