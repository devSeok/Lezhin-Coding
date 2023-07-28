package lezhin.coding.domain.content.api;

import lezhin.coding.domain.content.dto.ContentRegisterDto;
import lezhin.coding.domain.content.dto.ContentRegisterResDto;
import lezhin.coding.domain.content.dto.EvaluationReqDto;
import lezhin.coding.domain.content.service.ContentService;
import lezhin.coding.global.common.response.DataResponse;
import lezhin.coding.global.common.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentApiController {

    private final ContentService contentService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public DataResponse<ContentRegisterResDto> contentRegister(@Valid @RequestBody ContentRegisterDto dto) {

        return DataResponse.create(ContentRegisterResDto.of(contentService.contentRegister(dto)));
    }

    @PostMapping("/evaluation")
    public void evaluation(@Valid @RequestBody EvaluationReqDto dto) {

        System.out.println(dto);
    }


    @PostMapping("/")
    public String test() {
        return "test";
    }

}
