package lezhin.coding.domain.content.domain.content.repository;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.domain.content.domain.content.dto.TuplieResult;
import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.domain.content.domain.evaluation.EvaluationRepository;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContentRepositoryImplTest extends IntegrationTestSupport {


    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @AfterEach
    void tearDown() {
        evaluationRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("좋아요가 가장 많은 작품을 조회")
    @Test
    void contentLikeSelect() {
    //given
        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity save = contentRepository.save(content);

        MemberEntity member = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity save1 = memberRepository.save(member);

        EvaluationEntity evaluationEntity1 = EvaluationEntity.create(save1, save, EvaluationType.LIKE.getCode());
        EvaluationEntity evaluationEntity2 = EvaluationEntity.create(save1, save, EvaluationType.LIKE.getCode());
        evaluationRepository.saveAll(List.of(evaluationEntity1, evaluationEntity2));
        //when
        List<TuplieResult> tuplieResults = contentRepository.likeList(EvaluationType.LIKE);

        //then
        Assertions.assertThat(tuplieResults).hasSize(1)
                .extracting("content", "count")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("test", 2L)
                );
    }

    private ContentEntity createContent(MinorWorkType minorWorkType) {
        Amount amount = Amount.builder()
                .value(200)
                .build();

        return ContentEntity.builder()
                .content("test")
                .payType(PayType.PAY)
                .amount(amount)
                .minorWorkType(minorWorkType)
                .build();
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

    private UserEmail getUserEmail(String email) {
        UserEmail userEmail = UserEmail.builder()
                .value(email)
                .build();
        return userEmail;
    }
}