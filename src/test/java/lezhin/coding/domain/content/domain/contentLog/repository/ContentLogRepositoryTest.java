package lezhin.coding.domain.content.domain.contentLog.repository;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.MinorWorkType;
import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class ContentLogRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentLogRepository contentLogRepository;


    @AfterEach
    void tearDown() {
        contentLogRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("특정 맴버 컨텐츠로그를 삭제 한다.")
    @Test
    void contentLogAllDelete() {
        // given
        MemberEntity member = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity saveMember = memberRepository.save(member);

        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity saveContent = contentRepository.save(content);

        ContentLogEntity contentLogEntity = ContentLogEntity.create(saveContent.getId(), saveMember.getId());
        contentLogRepository.save(contentLogEntity);

        //when
        contentLogRepository.deleteContentLogsByMemberId(saveMember.getId());

        //then
        assertThat(contentLogRepository.findAll()).hasSize(0);
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