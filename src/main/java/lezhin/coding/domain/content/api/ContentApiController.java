package lezhin.coding.domain.content.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentApiController {

    @PostMapping
    public void contentRegister() {


    }

}
