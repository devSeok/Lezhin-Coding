package lezhin.coding.domain.member.dto;


import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Gender;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lombok.*;

import javax.validation.Valid;


public class MemberDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberRegisterReqDto {

        @Valid
        private UserName userName;
        @Valid
        private UserEmail userEmail;
        private Gender gender;
        private Type type;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                    .userName(this.userName)
                    .userEmail(this.userEmail)
                    .gender(this.gender)
                    .type(this.type)
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
