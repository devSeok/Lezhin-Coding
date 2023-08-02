package lezhin.coding.domain.content.domain.contentLog.dto;

import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
public class ContentLogHistoryDto {

    private UserName userName;
    private UserEmail userEmail;
    private String content;
    private LocalDateTime createDate;
}
