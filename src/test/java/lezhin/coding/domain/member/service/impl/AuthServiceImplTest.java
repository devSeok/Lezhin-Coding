package lezhin.coding.domain.member.service.impl;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Gender;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.domain.member.dto.MemberDto;
import lezhin.coding.domain.member.dto.MemberLoginReqDto;
import lezhin.coding.domain.member.service.AuthService;
import lezhin.coding.global.exception.error.exception.EmailDuplicationException;
import lezhin.coding.global.jwt.dto.TokenDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest extends IntegrationTestSupport {

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

        MemberDto.MemberRegisterReqDto memberDto = getMemberRegisterReqDto(userName, userEmail);
        //when
        MemberEntity memberEntity = authServiceImpl.memberRegister(memberDto);
        //then

        assertThat(memberEntity.getId()).isNotNull();
        assertThat(memberEntity)
                .extracting("userName", "userEmail", "gender", "type")
                .contains(userName, userEmail, Gender.MAN, Type.ADULT);
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

        MemberDto.MemberRegisterReqDto memberDto = getMemberRegisterReqDto(userName, userEmail);

        //when
        //then
        assertThatThrownBy(() -> authServiceImpl.memberRegister(memberDto))
                .isInstanceOf(EmailDuplicationException.class)
                .hasMessage("메일이 존재합니다");
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
                .email(email)
                .password("test")
                .build();

        //when
        TokenDto tokenDto = authServiceImpl.login(memberLoginReqDto);

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
        MemberLoginReqDto memberLoginReqDto = MemberLoginReqDto.builder()
                .email(anotherEmail)
                .password("test")
                .build();

        //when
        //then
        assertThatThrownBy(() -> authServiceImpl.login(memberLoginReqDto))
                .isInstanceOf(EmailDuplicationException.class)
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




    private  MemberDto.MemberRegisterReqDto getMemberRegisterReqDto(UserName userName, UserEmail userEmail) {
        return MemberDto.MemberRegisterReqDto.builder()
                .userName(userName)
                .userEmail(userEmail)
                .password("test")
                .gender("MAN")
                .type("ADULT")
                .build();
    }
}