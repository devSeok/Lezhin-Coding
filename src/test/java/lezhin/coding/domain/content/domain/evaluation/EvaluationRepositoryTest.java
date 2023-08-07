package lezhin.coding.domain.content.domain.evaluation;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@Transactional
class EvaluationRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private EvaluationRepository evaluationRepository;

    @AfterEach
    void tearDown() {
        evaluationRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }


    @DisplayName("특정 사용자 작품에 대한 평가(좋아요/싫어요)를 삭제한다.")
    @Test
    void deleteEvaluationsByMemberId() {
    //given
        MemberEntity member = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity saveMember = memberRepository.save(member);

        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity saveContent = contentRepository.save(content);

        EvaluationEntity evaluationEntity = EvaluationEntity.create(saveMember, saveContent, EvaluationType.LIKE.getCode());
        evaluationRepository.save(evaluationEntity);
    //when
        evaluationRepository.deleteEvaluationsByMemberId(saveMember.getId());
    //then

        assertThat(evaluationRepository.findAll()).hasSize(0).isEmpty();

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

}