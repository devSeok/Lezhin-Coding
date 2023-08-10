package lezhin.coding.domain.content.service.impl;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.global.mock.WithCustomMockUser;
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
import lezhin.coding.domain.content.dto.request.PayTypeChangeReqDto;
import lezhin.coding.domain.content.dto.response.ContentRegisterResDto;
import lezhin.coding.domain.content.dto.response.EvaluationRegisterResDto;
import lezhin.coding.domain.content.dto.response.PayTypeChangeResDto;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.entity.enums.Type;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.global.exception.error.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;


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
        memberRepository.deleteAllInBatch();
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
                .hasMessage("유료는 100원~500원 값이어야 합니다.");
    }

    @Test
    @DisplayName("특정 컨텐츠를 평가 할수 있다.")
    @WithCustomMockUser
    void evaluation() {
        // TODO : 오류

        // given
        UserEmail userEmail = UserEmail.builder()
                .value("test@naver.com").build();
        MemberEntity memberInsert = createMember(userEmail, "진석");
        MemberEntity save = memberRepository.saveAndFlush(memberInsert);

        System.out.println(save.getId());
        ContentEntity contentSave = contentCreate(PayType.PAY, 100, MinorWorkType.ADULT_WORK);
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
        ContentEntity contentSave = contentCreate(PayType.PAY, 100, MinorWorkType.ADULT_WORK);
        EvaluationReqDto evaluationReqDto = getEvaluationReqDto(contentSave.getId());

        //when
        //then
        assertThatThrownBy(() -> lezhinContentService.evaluation(evaluationReqDto))
                .isInstanceOf(UserNotException.class)
                .hasMessage("유저 정보가 없습니다.");
    }

    @Test
    @DisplayName("좋아요가 가장 많은 작품 3개와 싫어요 작품 3개를 가져온다. ")
    void sortEvaluationContent() {
        // TODO : 오류

        // given
        ContentEntity contentEntity = contentCreate(PayType.PAY, 100, MinorWorkType.ADULT_WORK);
        UserEmail userEmail = UserEmail.builder()
                .value("test@naver.com").build();
        MemberEntity memberInsert = createMember(userEmail, "진석");
        MemberEntity MemberSave = memberRepository.save(memberInsert);

        EvaluationEntity evaluationEntity1 = EvaluationEntity.create(MemberSave, contentEntity, EvaluationType.LIKE.getCode());
        EvaluationEntity evaluationEntity2 = EvaluationEntity.create(MemberSave, contentEntity, EvaluationType.LIKE.getCode());

        evaluationRepository.saveAll(List.of(evaluationEntity1, evaluationEntity2));

        //when
        RankResultDto rankResultDto = lezhinContentService.sortEvaluationContent(3);

        //then
        assertThat(rankResultDto.getLike()).hasSize(1)
                .extracting("content", "count")
                .containsExactlyInAnyOrder(
                  tuple("test", 2L)
                );

    }

    @Test
    @DisplayName("등록된 컨텐츠 값을 가져온다.")
    @WithCustomMockUser
    void getRowContent() {

        // given
        UserEmail userEmail = UserEmail.builder()
                .value("test@naver.com").build();
        MemberEntity memberInsert = createMember(userEmail, "진석");
        MemberEntity MemberSave = memberRepository.save(memberInsert);

        ContentEntity contentEntity = contentCreate(PayType.PAY, 100, MinorWorkType.ADULT_WORK);
        //when
        ContentResultDto rowContent = lezhinContentService.getRowContent(contentEntity.getId());
        //then
        assertThat(rowContent.getContent()).isEqualTo("test");
    }

    @Test
    @DisplayName("특정 작품을 유료로 전환한다.")
    void payTypePayChange() {
        // given
        ContentEntity contentEntity = contentCreate(PayType.FREE, 0, MinorWorkType.ADULT_WORK);

        Amount amountValue = Amount.builder().value(200).build();

        PayTypeChangeReqDto build = PayTypeChangeReqDto.builder()
                .payType(PayType.PAY.getCode())
                .amount(amountValue)
                .build();
        //when
        PayTypeChangeResDto contentEntity1 = lezhinContentService.payTypeChange(contentEntity.getId(), build);

        //then
        assertThat(contentEntity1.getPayType()).isEqualTo(PayType.PAY.getCode());
        assertThat(contentEntity1.getAmount()).isEqualTo(200);
    }

    @Test
    @DisplayName("특정 작품을 무료로 전환한다.")
    void payTypeFreeChange() {
        // given
        ContentEntity contentEntity = contentCreate(PayType.PAY, 100, MinorWorkType.ADULT_WORK);

        Amount amountValue = Amount.builder().value(0).build();

        PayTypeChangeReqDto build = PayTypeChangeReqDto.builder()
                .payType(PayType.FREE.getCode())
                .amount(amountValue)
                .build();
        //when
        PayTypeChangeResDto contentEntity1 = lezhinContentService.payTypeChange(contentEntity.getId(), build);

        //then
        assertThat(contentEntity1.getPayType()).isEqualTo(PayType.FREE.getCode());
        assertThat(contentEntity1.getAmount()).isEqualTo(0);
    }

    @Test
    @DisplayName("특정 작품을 무료로 전환시 금액을 0원 이상으로 작성시 예외처리가 된다..")
    void payTypeFreeNotChange() {
        // given
        ContentEntity contentEntity = contentCreate(PayType.PAY, 100, MinorWorkType.ADULT_WORK);

        Amount amountValue = Amount.builder().value(100).build();

        PayTypeChangeReqDto build = PayTypeChangeReqDto.builder()
                .payType(PayType.FREE.getCode())
                .amount(amountValue)
                .build();
        //when
        //then
            assertThatThrownBy(() -> lezhinContentService.payTypeChange(contentEntity.getId(), build))
                    .isInstanceOf(ContentAmountFreeVaildException.class)
                    .hasMessage("무료는 0값이어야합니다.");

    }


    @Test
    @DisplayName("특정 작품을 유로로 전환시 금액을 100원 미만으로 작성시 예외처리가 된다..")
    void payTypePayNotChange() {
        // given
        ContentEntity contentEntity = contentCreate(PayType.FREE, 0, MinorWorkType.ADULT_WORK);
        Amount amountValue = Amount.builder().value(501).build();
        PayTypeChangeReqDto build = PayTypeChangeReqDto.builder()
                .payType(PayType.PAY.getCode())
                .amount(amountValue)
                .build();

        //when
        //then
        assertThatThrownBy(() -> lezhinContentService.payTypeChange(contentEntity.getId(), build))
                .isInstanceOf(ContentAmountPayMinLimitException.class)
                .hasMessage("유료는 100원~500원 값이어야 합니다.");

    }



    private static EvaluationReqDto getEvaluationReqDto(Long contentId) {
        Comment comment = Comment.builder().value("test").build();

        return EvaluationReqDto.builder()
                .evaluationType("LIKE")
                .comment(comment)
                .contentId(contentId)
                .build();
    }

    private ContentEntity contentCreate(PayType payType, int amount, MinorWorkType minorWorkType) {

        Amount amountValue = Amount.builder().value(amount).build();

        ContentEntity content = ContentEntity.builder()
                .content("test")
                .payType(payType)
                .amount(amountValue)
                .minorWorkType(minorWorkType)
                .build();

        return contentRepository.save(content);
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