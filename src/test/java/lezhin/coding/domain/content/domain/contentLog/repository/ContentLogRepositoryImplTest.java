package lezhin.coding.domain.content.domain.contentLog.repository;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.content.domain.content.enums.PayType;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.dto.ContentLogHistoryDto;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Transactional
class ContentLogRepositoryImplTest extends IntegrationTestSupport {


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

    @DisplayName("작품 별로 어떤 사용자가 죄회 했는데 확인한다.")
    @Test
    void contentLogAllDelete() {
    // given
        MemberEntity member1 = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity member2 = createMember(getUserEmail("test1@naver.com"), "길동");
        memberRepository.saveAll(List.of(member1, member2));

        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity saveContent = contentRepository.save(content);

        ContentLogEntity contentLogEntity1 = ContentLogEntity.create(member1, saveContent);
        ContentLogEntity contentLogEntity2 = ContentLogEntity.create(member1, saveContent);
        ContentLogEntity contentLogEntity3 = ContentLogEntity.create(member2, saveContent);
        ContentLogEntity contentLogEntity4 = ContentLogEntity.create(member2, saveContent);
        contentLogRepository.saveAll(List.of(contentLogEntity1, contentLogEntity2, contentLogEntity3, contentLogEntity4));

        Pageable pageable = PageRequest.of(0, 10);
        //when
        Page<ContentLogHistoryDto> artworkViewHistoryByContentId = contentLogRepository.getContentUserHistoryByContentId(saveContent.getId(), pageable);

        //then
        assertThat(artworkViewHistoryByContentId).hasSize(4)
                .extracting("userName", "userEmail")
                .containsExactlyInAnyOrder(
                  tuple("진석", "test@naver.com"),
                  tuple("진석", "test@naver.com"),
                  tuple("길동", "test1@naver.com"),
                  tuple("길동", "test1@naver.com")
                );
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