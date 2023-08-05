package lezhin.coding.domain.member.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lezhin.coding.WithCustomMockUser;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.dto.MemberDto;
import lezhin.coding.domain.member.dto.MemberLoginReqDto;
import lezhin.coding.domain.member.dto.MemberLoginResDto;
import lezhin.coding.domain.member.service.AuthService;
import lezhin.coding.global.config.security.WebSecurityConfig;
import lezhin.coding.global.jwt.dto.TokenDto;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthApiController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
        })
class AuthApiControllerTest  {

    @MockBean
    private AuthService authServiceImpl;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("회원가입을 한다")
    @Test
    @WithCustomMockUser
    void memberSignup() throws Exception{
    // given
        UserName userName = UserName.builder().value("test").build();
        UserEmail userEmail = UserEmail.builder().value("test@naver.com").build();

        MemberDto.MemberRegisterReqDto build = MemberDto.MemberRegisterReqDto.builder()
                .userName(userName)
                .userEmail(userEmail)
                .password("test")
                .gender("MAN")
                .type("ADULT")
                .build();

        Map test = new HashMap();

        test.put("userEmail", "1");
        test.put("userName", "1");
        test.put("userEmail", "1");
        test.put("userEmail", "1");

        MemberEntity member1 = createMember(getUserEmail("test@naver.com"), "진석");



        MemberDto.Res mock = Mockito.mock(MemberDto.Res.class);
        //when
       //then
        mockMvc.perform(post("/api/auth/signup")
                        .content(objectMapper.writeValueAsString(build))
                        .with(csrf()) // 해당 설정을 추가
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @DisplayName("로그인 한다")
    @Test

    void memberLogin() throws Exception{

        MemberLoginReqDto test = MemberLoginReqDto.builder()
                .email("test@nave.com")
                .password("test")
                .build();


        //when
// TokenDto mock 객체 생성
        TokenDto terst = Mockito.mock(TokenDto.class);

// MemberLoginResDto mock 객체 생성 및 반환값 설정
        MemberLoginResDto expectedLoginResDto = new MemberLoginResDto("someGrantType", "someAccessToken", "someRefreshToken", 3600L);
//        Mockito.when(MemberLoginResDto.from(terst)).thenReturn(expectedLoginResDto);

// 실제 테스트 로직에서 MemberLoginResDto.from(terst) 호출 시 expectedLoginResDto 객체가 반환됨
//        MemberLoginResDto result = MemberLoginResDto.from(terst);


//        Mockito.doReturn(expectedLoginResDto).when(terst);

        Mockito.when(authServiceImpl.login(test)).thenReturn(null);
        //then
        mockMvc.perform(post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(test))

                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
    private UserEmail getUserEmail(String email) {
        UserEmail userEmail = UserEmail.builder()
                .value(email)
                .build();
        return userEmail;
    }

    private MemberEntity createMember(UserEmail email, String name) {

        UserName userName = UserName.builder()
                .value(name)
                .build();

        return MemberEntity.builder()
                .userEmail(email)
                .userName(userName)
                .password("test")
                .build();
    }
}