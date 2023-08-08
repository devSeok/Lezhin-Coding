package lezhin.coding.domain.content.api;

import lezhin.coding.domain.content.domain.content.dto.RankResultDto;
import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import lezhin.coding.domain.content.dto.*;
import lezhin.coding.domain.content.dto.request.ContentRegisterReqDto;
import lezhin.coding.domain.content.dto.request.EvaluationReqDto;
import lezhin.coding.domain.content.dto.request.PayTypeChangeReqDto;
import lezhin.coding.domain.content.dto.response.ContentRegisterResDto;
import lezhin.coding.domain.content.dto.response.EvaluationRegisterResDto;
import lezhin.coding.domain.content.dto.response.PayTypeChangeResDto;
import lezhin.coding.domain.content.service.ContentService;
import lezhin.coding.global.common.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentApiController {

    private final ContentService contentService;

    // 작품 조회
    @GetMapping("/{contentId}")
    public DataResponse<ContentResultDto> getRowContent(@PathVariable("contentId") Long contentId) {

        return DataResponse.create(contentService.getRowContent(contentId));
    }

    // 작품별로 언제 어떤 사용자 조회 했는지 이력 조회
    @GetMapping("/{contentId}/user-log")
    public DataResponse<List<ContentLogHistoryDto>> userContentSelectList(@PathVariable("contentId") Long contentId) {

        return DataResponse.create(contentService.userContentSelectList(contentId));

    }

    // 작품 저장
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<ContentRegisterResDto> contentRegister(@Valid @RequestBody ContentRegisterReqDto dto) {

        return DataResponse.create(contentService.contentRegister(dto));
    }

    // 작품 평가
    @PostMapping("/evaluation")
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<EvaluationRegisterResDto> evaluation(@Valid @RequestBody EvaluationReqDto dto) {

        return DataResponse.create(contentService.evaluation(dto));
    }


    // 좋아요가 가장 많은 작품 3개와 싫어요가 가장 만은 작품 3개를 조회하는 API
    @GetMapping("/ranking")
    public DataResponse<RankResultDto> ranking(@RequestParam(
            value = "limit", required = false, defaultValue = "3") int limit
    ) {

        return DataResponse.create(contentService.sortEvaluationContent(limit));
    }

    // 특정 작품을 유료 ,무료로 변경할 수 있는 api
    @PutMapping("/{contentId}/payType")
    public DataResponse<PayTypeChangeResDto> payTypeChange(
            @PathVariable("contentId") Long contentId,
            @Valid @RequestBody PayTypeChangeReqDto dto
    ) {
        return DataResponse.create((contentService.payTypeChange(contentId, dto)));
    }


}
