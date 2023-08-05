package lezhin.coding.domain.member.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lezhin.coding.WithCustomMockUser;
import lezhin.coding.domain.member.service.MemberService;
import lezhin.coding.global.config.security.WebSecurityConfig;
import lezhin.coding.global.jwt.TokenProvider;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = MemberApiController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
        })
class MemberApiControllerTest {

    @MockBean
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("사용자 조회를 한다.")
    @Test
    @WithCustomMockUser
    void findUsersWithAdultContentViews() throws Exception{
    //given

//    //when
//    //then
     mockMvc.perform(get("/api/member/adult/top")
             .contentType(MediaType.APPLICATION_JSON)
     )
             .andDo(print())
             .andExpect(status().isOk());
//
//
    }

    @DisplayName("사용자 삭제")
    @Test
    @WithCustomMockUser
    void deleteMember() throws Exception{
        //given
//    //when
//    //then
        mockMvc.perform(delete("/api/member/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(status().isNoContent());
//
//
    }


}