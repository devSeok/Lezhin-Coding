package lezhin.coding.domain.member.service.impl;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.domain.member.dto.reponse.MemberLoginResDto;
import lezhin.coding.domain.member.dto.reponse.MemberSignupResDto;
import lezhin.coding.domain.member.dto.request.MemberLoginReqDto;
import lezhin.coding.domain.member.dto.request.MemberSignupReqDto;
import lezhin.coding.domain.member.service.AuthService;
import lezhin.coding.global.exception.error.exception.EmailDuplicationException;
import lezhin.coding.global.exception.error.exception.ErrorCode;
import lezhin.coding.global.exception.error.exception.UserNotException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest extends IntegrationTestSupport {

    @Autowired
    private AuthService authServiceImpl;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }


    @DisplayName("회원가입 정보를 받아 회원을 생성 한다.")
    @Test
    void memberRegister() {
    //given
        UserName userName = UserName.builder()
                .value("오진석").build();
        UserEmail userEmail = UserEmail.builder()
                .value("test@naver.com").build();

        MemberSignupReqDto memberDto = getMemberRegisterReqDto(userName, userEmail);
        //when
        MemberSignupResDto memberEntity = authServiceImpl.memberSignup(memberDto);
        //then

        assertThat(memberEntity.getUserEmail()).isNotNull();
        assertThat(memberEntity)
                .extracting("userName", "userEmail")
                .contains(userName.getValue(), userEmail.getValue());
    }

    @DisplayName("회원가입 정보를 받아 회원 생성시 이메일 중복 체크를 한다")
    @Test
    void memberRegisterWithEmailValid() {
        //given
        UserEmail userEmail = UserEmail.builder()
                .value("test@naver.com").build();
        MemberEntity memberInsert = createMember(userEmail, "진석");
        memberRepository.save(memberInsert);

        UserName userName = UserName.builder()
                .value("오진석").build();

        MemberSignupReqDto memberDto = getMemberRegisterReqDto(userName, userEmail);

        //when
        //then
        assertThatThrownBy(() -> authServiceImpl.memberSignup(memberDto))
                .isInstanceOf(EmailDuplicationException.class)
                .hasMessage(ErrorCode.EXISTENT_EMAIL.getMessage());
    }
    @DisplayName("회원가입 된 사용정보 기반으로 로그인에 성공한다.")
    @Test
    void loginAndGenerateTokens() {
        //given
        final String email = "test@naver.com";
        UserEmail userEmail = UserEmail.builder().value(email).build();
        MemberEntity memberInsert = createPasswordEncoderMember(userEmail, "test","진석");
        memberRepository.save(memberInsert);

        MemberLoginReqDto memberLoginReqDto = MemberLoginReqDto.builder()
                .email(userEmail)
                .password("test")
                .build();

        //when
        MemberLoginResDto tokenDto = authServiceImpl.login(memberLoginReqDto);

        //then
        assertThat(tokenDto.getAccessToken()).isNotNull();
        assertThat(tokenDto.getRefreshToken()).isNotNull();
        assertThat(tokenDto.getAccessTokenExpiresIn()).isInstanceOf(Long.class);
    }


    @DisplayName("로그인시 유저 정보가 없으면 익센션이 난다.")
    @Test
    void test() {
    // given
        final String email = "test@naver.com";
        UserEmail userEmail = UserEmail.builder().value(email).build();
        MemberEntity memberInsert = createPasswordEncoderMember(userEmail, "test","진석");
        memberRepository.save(memberInsert);

        final String anotherEmail = "test1@naver.com";
        UserEmail anotherEmailValue = UserEmail.builder().value(anotherEmail).build();

        MemberLoginReqDto memberLoginReqDto = MemberLoginReqDto.builder()
                .email(anotherEmailValue)
                .password("test")
                .build();

        //when
        //then
        assertThatThrownBy(() -> authServiceImpl.login(memberLoginReqDto))
                .isInstanceOf(UserNotException.class)
                .hasMessage("유저 정보가 없습니다.");
    }





    private MemberEntity createMember(UserEmail email, String name) {

        UserName userName = UserName.builder()
                .value(name)
                .build();

        return MemberEntity.builder()
                .userEmail(email)
                .userName(userName)
                .type(Type.ADULT)
                .password("test")
                .build();
    }

    private MemberEntity createPasswordEncoderMember(UserEmail email,String password, String name) {

        UserName userName = UserName.builder()
                .value(name)
                .build();

        String encode = passwordEncoder.encode(password);

        return MemberEntity.builder()
                .userEmail(email)
                .userName(userName)
                .type(Type.ADULT)
                .password(encode)
                .build();
    }




    private MemberSignupReqDto getMemberRegisterReqDto(UserName userName, UserEmail userEmail) {
        return MemberSignupReqDto.builder()
                .userName(userName)
                .userEmail(userEmail)
                .password("test")
                .gender("MAN")
                .type("ADULT")
                .build();
    }
}