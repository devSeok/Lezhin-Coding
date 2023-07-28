package lezhin.coding.domain.content.api;

import lezhin.coding.domain.content.dto.ContentRegisterDto;
import lezhin.coding.domain.content.dto.ContentRegisterResDto;
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

        System.out.println("============");
        System.out.println(SecurityUtil.getCurrentMemberId());
        System.out.println("============");
        return DataResponse.create(ContentRegisterResDto.of(contentService.contentRegister(dto)));
    }

    @GetMapping
    public String test() {
        return "test";
    }

}
