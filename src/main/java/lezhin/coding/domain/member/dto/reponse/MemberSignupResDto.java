package lezhin.coding.domain.member.dto.reponse;

import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Gender;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignupResDto {

    private String userEmail;
    private String userName;
    private String gender;
    private Type type;

    @Builder
    public MemberSignupResDto(String userEmail, String userName, String gender, Type type) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.gender = gender;
        this.type = type;
    }

    public static MemberSignupResDto of(MemberEntity member) {
            return MemberSignupResDto.builder()
                    .userEmail(member.getUserEmail().getValue())
                    .userName(member.getUserName().getValue())
                    .gender(member.getGender().getCode())
                    .type(member.getType())
                    .build();
    }
}
