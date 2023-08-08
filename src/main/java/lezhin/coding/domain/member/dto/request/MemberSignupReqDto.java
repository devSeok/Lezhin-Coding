package lezhin.coding.domain.member.dto.request;

import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Gender;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.global.EnumTypeValid;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class MemberSignupReqDto {


    @Valid
    private UserName userName;
    @Valid
    private UserEmail userEmail;

    @NotBlank(message = "비밀번호은 필수입니다.")
    private String password;

    @EnumTypeValid(target = Gender.class, message = "성별 타입은 MAN or WOMAN 이어야합니다.")
    private String gender;

    @EnumTypeValid(target = Type.class, message = "타입은 GENERAL(일반) or ADULT(성인) 이어야합니다.")
    private String type;

    @Builder
    public MemberSignupReqDto(UserName userName, UserEmail userEmail, String password, String gender, String type) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.gender = gender;
        this.type = type;
    }

    public MemberEntity toEntity(PasswordEncoder passwordEncoder) {
        return MemberEntity.builder()
                .userName(this.userName)
                .userEmail(this.userEmail)
                .password(passwordEncoder.encode(this.password))
                .gender(Gender.of(this.gender))
                .type(Type.of(this.type))
                .build();
    }
}
