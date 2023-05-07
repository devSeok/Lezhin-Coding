package lezhin.coding.domain.content.api;

import lezhin.coding.domain.content.dto.ContentRegisterDto;
import lezhin.coding.domain.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentApiController {

    private final ContentService contentService;

    @PostMapping
    public void contentRegister(@Valid @RequestBody ContentRegisterDto.ContentRegisterReqDto dto) {


        System.out.println(dto);
        contentService.contentRegister(dto);



    }

}
