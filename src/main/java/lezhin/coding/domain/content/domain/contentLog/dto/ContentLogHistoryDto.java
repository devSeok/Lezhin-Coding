package lezhin.coding.domain.content.domain.contentLog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private String userName;
    private String userEmail;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
}
