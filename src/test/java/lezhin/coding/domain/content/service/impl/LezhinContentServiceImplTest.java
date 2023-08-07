package lezhin.coding.domain.content.service.impl;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.WithCustomMockUser;
import lezhin.coding.domain.content.domain.comment.Comment;
import lezhin.coding.domain.content.domain.comment.CommentRepository;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.domain.content.domain.content.dto.RankResultDto;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.domain.content.domain.evaluation.EvaluationRepository;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;
import lezhin.coding.domain.content.dto.ContentResultDto;
import lezhin.coding.domain.content.dto.request.ContentRegisterReqDto;
import lezhin.coding.domain.content.dto.request.EvaluationReqDto;
import lezhin.coding.domain.content.dto.response.ContentRegisterResDto;
import lezhin.coding.domain.content.dto.response.EvaluationRegisterResDto;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.global.exception.error.exception.*;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.junit.jupiter.api.Assertions.*;



class LezhinContentServiceImplTest extends IntegrationTestSupport {

    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ContentLogRepository contentLogRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private LezhinContentServiceImpl lezhinContentService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        contentLogRepository.deleteAllInBatch();;
        commentRepository.deleteAllInBatch();
        evaluationRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("컨텐츠를 무료인 버전으로 등록한다.")
    void contentFreeRegister() {
        //given

        ContentRegisterReqDto contentRegisterReqDto = getContentRegisterReqDto(0, PayType.FREE.getCode());
        //when
        ContentRegisterResDto contentedRegister = lezhinContentService.contentRegister(contentRegisterReqDto);

        //then
        assertThat(contentedRegister.getAmount()).isEqualTo(0);
        assertThat(contentedRegister.getPayType()).isEqualTo(PayType.FREE.getCode());
        assertThat(contentedRegister.getMinorWorkType()).isEqualTo(MinorWorkType.ADULT_WORK.getCode());
    }

    @Test
    @DisplayName("컨텐츠를 유료인 버전으로 등록한다.")
    void contentPayRegister() {
        //given

        ContentRegisterReqDto contentRegisterReqDto = getContentRegisterReqDto(100, PayType.PAY.getCode());
        //when
        ContentRegisterResDto contentedRegister = lezhinContentService.contentRegister(contentRegisterReqDto);

        //then
        assertThat(contentedRegister.getAmount()).isEqualTo(100);
        assertThat(contentedRegister.getPayType()).isEqualTo(PayType.PAY.getCode());
        assertThat(contentedRegister.getMinorWorkType()).isEqualTo(MinorWorkType.ADULT_WORK.getCode());
    }


    @DisplayName("컨텐츠를 등록시 payType이 무료일때 0 이상인 금액을 넣으면 예외가 발생한다.")
    @Test
    void contentFreeNotRegister() {
        // given
        ContentRegisterReqDto contentRegisterReqDto = getContentRegisterReqDto(100, PayType.FREE.getCode());

        //when
        //then
        assertThatThrownBy(() -> lezhinContentService.contentRegister(contentRegisterReqDto))
                .isInstanceOf(ContentAmountFreeVaildException.class)
                .hasMessage("무료는 0값이어야합니다.");
    }

    @DisplayName("컨텐츠를 등록시 payType이 유료일때 100 미만인 금액을 넣으면 예외가 발생한다.")
    @Test
    void contentPayNotRegister() {
        // given
        ContentRegisterReqDto contentRegisterReqDto = getContentRegisterReqDto(99, PayType.PAY.getCode());

        //when
        //then
        assertThatThrownBy(() -> lezhinContentService.contentRegister(contentRegisterReqDto))
                .isInstanceOf(ContentAmountPayMinLimitException.class)
                .hasMessage("유료는 최소 100원부터 시작입니다.");
    }

    @Test
    @DisplayName("특정 컨텐츠를 평가 할수 있다.")
    @WithCustomMockUser
    void evaluation() {
        // given
        UserEmail userEmail = UserEmail.builder()
                .value("test@naver.com").build();
        MemberEntity memberInsert = createMember(userEmail, "진석");
        memberRepository.deleteAll();
        MemberEntity save = memberRepository.saveAndFlush(memberInsert);

        System.out.println("=============");
        System.out.println(save.getId());
        ContentEntity contentSave = contentCreate();
        EvaluationReqDto evaluationReqDto = getEvaluationReqDto(contentSave.getId());

        //when
        EvaluationRegisterResDto evaluation = lezhinContentService.evaluation(evaluationReqDto);

        //then
        assertThat(evaluation.getContentId()).isEqualTo(contentSave.getId());
    }

    @Test
    @DisplayName("특정 컨텐츠를 평가 등록시 유저가 존재하지 않으면 예외가 발생한다.")
    @WithCustomMockUser
    void evaluationNotMember() {

        // given
        ContentEntity contentSave = contentCreate();
        EvaluationReqDto evaluationReqDto = getEvaluationReqDto(contentSave.getId());

        //when
        //then
        assertThatThrownBy(() -> lezhinContentService.evaluation(evaluationReqDto))
                .isInstanceOf(UserNotException.class)
                .hasMessage("유저 정보가 없습니다.");
    }

    @Test
    @DisplayName("특정 컨텐츠를 평가 등록시 컨텐츠가 존재하지 없으면 예외가 발생한다.")
    @WithCustomMockUser
    void evaluationNotContent() {

        // given
        UserEmail userEmail = UserEmail.builder()
                .value("test@naver.com").build();
        MemberEntity memberInsert = createMember(userEmail, "진석");
        memberRepository.save(memberInsert);
        EvaluationReqDto evaluationReqDto = getEvaluationReqDto(2L);

        //when
        //then
        assertThatThrownBy(() -> lezhinContentService.evaluation(evaluationReqDto))
                .isInstanceOf(ContentNotException.class)
                .hasMessage("컨텐츠 정보가 없습니다.");
    }

    @Test
    @DisplayName("좋아요가 가장 많은 작품 3개와 싫어요 작품 3개를 가져온다. ")
    void sortEvaluationContent() {
        ContentEntity contentEntity = contentCreate();
        UserEmail userEmail = UserEmail.builder()
                .value("test@naver.com").build();
        MemberEntity memberInsert = createMember(userEmail, "진석");
        EvaluationEntity evaluationEntity1 = EvaluationEntity.create(memberInsert, contentEntity, EvaluationType.LIKE.getCode());
        EvaluationEntity evaluationEntity2 = EvaluationEntity.create(memberInsert, contentEntity, EvaluationType.LIKE.getCode());

        evaluationRepository.saveAll(List.of(evaluationEntity1, evaluationEntity2));

        RankResultDto rankResultDto = lezhinContentService.sortEvaluationContent();

        assertThat(rankResultDto.getLike()).hasSize(1)
                .extracting("content", "count")
                .containsExactlyInAnyOrder(
                  tuple("test", 2L)
                );

    }

    @Test
    void userContentSelectList() {
    }

    @Test
    @DisplayName("등록된 컨텐츠 값을 가져온다.")
    @WithCustomMockUser
    void getRowContent() {
        ContentEntity contentEntity = contentCreate();

        ContentResultDto rowContent = lezhinContentService.getRowContent(contentEntity.getId());

        assertThat(rowContent.getContent()).isEqualTo("test");
    }

    @Test
    @DisplayName("특정 작품을 유료로 전환한다.")
    void payTypePayChange() {

    }


    private static EvaluationReqDto getEvaluationReqDto(Long contentId) {
        Comment comment = Comment.builder().value("test").build();

        return EvaluationReqDto.builder()
                .evaluationType("LIKE")
                .comment(comment)
                .contentId(contentId)
                .build();
    }

    private ContentEntity contentCreate() {

        Amount amount = Amount.builder().value(200).build();

        ContentEntity content = ContentEntity.builder()
                .content("test")
                .payType(PayType.PAY)
                .amount(amount)
                .minorWorkType(MinorWorkType.ADULT_WORK)
                .build();

        ContentEntity contentSave = contentRepository.save(content);
        return contentSave;
    }
    private MemberEntity createMember(UserEmail email, String name) {

        UserName userName = UserName.builder()
                .value(name)
                .build();

        return MemberEntity.builder()
                .id(1L)
                .userEmail(email)
                .userName(userName)
                .type(Type.ADULT)
                .password("test")
                .build();
    }



    private static ContentRegisterReqDto getContentRegisterReqDto(int amount, String payType) {
        Amount amountObj = Amount.builder().value(amount).build();

        ContentRegisterReqDto contentRegisterReqDto = ContentRegisterReqDto.builder()
                .payType(payType)
                .amount(amountObj)
                .content("test")
                .minorWorkType("ADULT_WORK")
                .build();
        return contentRegisterReqDto;
    }
}