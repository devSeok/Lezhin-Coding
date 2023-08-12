package lezhin.coding.domain.content.api;

import lezhin.coding.domain.content.dto.response.*;
import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import lezhin.coding.domain.content.dto.request.ContentRegisterReqDto;
import lezhin.coding.domain.content.dto.request.EvaluationReqDto;
import lezhin.coding.domain.content.dto.request.PayTypeChangeReqDto;
import lezhin.coding.domain.content.service.ContentService;
import lezhin.coding.global.common.response.DataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public DataResponse<ContentResultResDto> getContentById(@PathVariable("contentId") Long contentId) {
        ContentResultResDto content = contentService.getContentById(contentId);

        return DataResponse.create(content);
    }

    // 작품별로 언제 어떤 사용자 조회 했는지 이력 조회
    @GetMapping("/{contentId}/user-history")
    public DataResponse<Page<ContentLogHistoryDto>> getContentUserHistory(
            @PathVariable("contentId") Long contentId,
            @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ContentLogHistoryDto> historyList = contentService.getContentUserHistory(contentId, pageable);

        return DataResponse.create(historyList);
    }

    // 작품 저장
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<ContentRegisterResDto> registerContent(
            @Valid @RequestBody ContentRegisterReqDto contentRequest
    ) {
        ContentRegisterResDto registeredContent = contentService.registerContent(contentRequest);

        return DataResponse.create(registeredContent);
    }

    // 작품 평가
    @PostMapping("/submit-rating")
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<EvaluationRegisterResDto> submitRating(@Valid @RequestBody EvaluationReqDto evaluationRequest) {
        EvaluationRegisterResDto submittedEvaluation = contentService.submitRating(evaluationRequest);

        return DataResponse.create(submittedEvaluation);
    }

    // 좋아요가 가장 많은 작품 3개와 싫어요가 가장 만은 작품 3개를 조회하는 API
    @GetMapping("/top-rankings")
    public DataResponse<RankingResultResDto> getTopRankings(@RequestParam(
            value = "limit", required = false, defaultValue = "3") int limit
    ) {

        return DataResponse.create(contentService.getTopRankedContents(limit));
    }

    // 특정 작품을 유료 ,무료로 변경할 수 있는 api
    @PutMapping("/{contentId}/pay-type")
    public DataResponse<PayTypeChangeResDto> changeContentPayType(
            @PathVariable("contentId") Long contentId,
            @Valid @RequestBody PayTypeChangeReqDto paymentTypeRequest
    ) {

        return DataResponse.create(contentService.changeContentPayType(contentId, paymentTypeRequest));
    }
}
