package lezhin.coding.domain.member.dto;


import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Gender;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.global.EnumTypeValid;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


public class MemberDto {
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberRegisterReqDto {

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

    @Getter
    public static class Res {
        private UserEmail userEmail;
        private UserName userName;
        private Gender gender;
        private Type type;

        public Res(MemberEntity member) {
            this.userEmail = member.getUserEmail();
            this.userName = member.getUserName();
            this.gender = member.getGender();
            this.type = member.getType();
        }

    }


}
