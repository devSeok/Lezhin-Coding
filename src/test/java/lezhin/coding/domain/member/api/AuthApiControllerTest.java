package lezhin.coding.domain.member.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lezhin.coding.global.mock.WithCustomMockUser;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;

import lezhin.coding.domain.member.dto.request.MemberLoginReqDto;
import lezhin.coding.domain.member.dto.request.MemberSignupReqDto;
import lezhin.coding.domain.member.service.AuthService;
import lezhin.coding.global.config.security.WebSecurityConfig;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

        MemberSignupReqDto build = MemberSignupReqDto.builder()
                .userName(userName)
                .userEmail(userEmail)
                .password("test")
                .gender("MAN")
                .type("ADULT")
                .build();

        //when
       //then
        mockMvc.perform(post("/api/auth/signup")
                        .content(objectMapper.writeValueAsString(build))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());

        // TODO Req dto 예외처리 잘되는지 체크

    }

    @DisplayName("로그인 한다")
    @Test
    @WithCustomMockUser
    void memberLogin() throws Exception{
        // given
        MemberLoginReqDto test = MemberLoginReqDto.builder()
                .email("test@nave.com")
                .password("test")
                .build();

        //when
        //then
        mockMvc.perform(post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(test))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

        // TODO Req dto 예외처리 잘되는지 체크
    }
}