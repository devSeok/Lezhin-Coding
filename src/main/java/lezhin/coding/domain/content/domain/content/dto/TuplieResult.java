package lezhin.coding.domain.content.domain.content.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TuplieResult {

    private Long contentId;
    private String content;
    private Long count;
}
