package lezhin.coding.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserWithAdultContentResDto {
    private Long memberId;
    private String userName;
}
