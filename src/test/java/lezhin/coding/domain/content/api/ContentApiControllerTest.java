package lezhin.coding.domain.content.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lezhin.coding.global.mock.WithCustomMockUser;
import lezhin.coding.domain.content.domain.comment.Comment;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.content.domain.content.enums.PayType;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;
import lezhin.coding.domain.content.dto.request.ContentRegisterReqDto;
import lezhin.coding.domain.content.dto.request.EvaluationReqDto;
import lezhin.coding.domain.content.dto.request.PayTypeChangeReqDto;
import lezhin.coding.domain.content.service.ContentService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ContentApiController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
        }
)
class ContentApiControllerTest {

    @MockBean
    private ContentService contentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("컨텐츠를 호출한다.")
    @WithCustomMockUser
    void getRowContent() throws Exception {
        mockMvc.perform(get("/api/content/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("작품별로 언제 어떤 사용자 조회 했는지 이력 조회를한다.")
    @WithCustomMockUser
    void userContentSelectList() throws Exception {

        mockMvc.perform(get("/api/content/1/user-history")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.code").value("200"));
    }

    @Test
    @DisplayName("컨텐츠를 등록한다.")
    @WithCustomMockUser
    void contentRegister() throws Exception {

        Amount amountValue = Amount.builder().value(200).build();

        ContentRegisterReqDto contentRegisterReqDto = ContentRegisterReqDto.builder()
                .minorWorkType(MinorWorkType.GENERAL_WORK.getCode())
                .payType(PayType.PAY.getCode())
                .content("test")
                .amount(amountValue)
                .build();

        mockMvc.perform(post("/api/content")
                        .content(objectMapper.writeValueAsString(contentRegisterReqDto))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());

        // TODO : 예외케이스를 더 만든다.
    }

    @Test
    @DisplayName("작품을 평가한다.")
    @WithCustomMockUser
    void evaluation() throws Exception {

        Comment commentValue = Comment.builder().value("test").build();

        EvaluationReqDto evaluationReqDto = EvaluationReqDto.builder()
                .contentId(1L)
                .evaluationType(EvaluationType.LIKE.getCode())
                .comment(commentValue)
                .build();

        mockMvc.perform(post("/api/content/submit-rating")
                        .content(objectMapper.writeValueAsString(evaluationReqDto))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("랭킹을 조회한다.")
    @WithCustomMockUser
    void ranking() throws Exception {

        mockMvc.perform(get("/api/content/top-rankings")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"))
                .andExpect(jsonPath("$.code").value("200"));

    }

    @Test
    @DisplayName("특정 작품을 유료 ,무료로 변경할 수 있다.")
    @WithCustomMockUser
    void payTypeChange() throws Exception {

        Amount amountValue = Amount.builder().value(200).build();

        PayTypeChangeReqDto payTypeChangeReqDto = PayTypeChangeReqDto.builder()
                .payType(PayType.PAY.getCode())
                .amount(amountValue)
                .build();

        mockMvc.perform(put("/api/content/1/pay-type")
                        .content(objectMapper.writeValueAsString(payTypeChangeReqDto))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}