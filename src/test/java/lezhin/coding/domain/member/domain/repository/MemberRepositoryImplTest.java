package lezhin.coding.domain.member.domain.repository;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.content.domain.content.enums.PayType;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.dto.reponse.UserWithAdultContentResDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


class MemberRepositoryImplTest extends IntegrationTestSupport {

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

    @DisplayName("최근 1주일간 성인작품 3개 이상 조회한 사용자 목록을 조회한다.")
    @Test
    void findUsersWithAdultContentViews() {
    //given
        MemberEntity member1 = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity member2 = createMember(getUserEmail("test11@naver.com"), "길동");

        memberRepository.saveAll(List.of(member1, member2));

        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity contentSave = contentRepository.save(content);

        ContentLogEntity contentLog1 =  ContentLogEntity.create(member1, content);
        ContentLogEntity contentLog2 =  ContentLogEntity.create(member1, content);
        ContentLogEntity contentLog3 =  ContentLogEntity.create(member1, content);

        ContentLogEntity contentLog4 =  ContentLogEntity.create(member2, content);
        ContentLogEntity contentLog5 =  ContentLogEntity.create(member2, content);
        ContentLogEntity contentLog6 =  ContentLogEntity.create(member2, content);

        contentLogRepository.saveAll(List.of(contentLog1, contentLog2, contentLog3, contentLog4, contentLog5, contentLog6));

        LocalDateTime startDateTime = LocalDateTime.now().minusWeeks(1);
        LocalDateTime endDateTime = LocalDateTime.now();
        MinorWorkType minorWorkType = MinorWorkType.ADULT_WORK;

        //when
        List<UserWithAdultContentResDto> usersWithAdultContentViews = memberRepository.findUsersWithAdultContentViews(
                startDateTime, endDateTime, minorWorkType
        );

        //then
        assertThat(usersWithAdultContentViews).hasSize(2)
                .extracting("userName", "userEmail")
                .containsExactlyInAnyOrder(
                    tuple("진석", "test@naver.com"),
                    tuple("길동", "test11@naver.com")
                );
    }

    @DisplayName("최근 1주일간 성인작품 3개 미만인 사용자는 나오지 않는다.")
    @Test
    void findUsersNotWithAdultContentViews() {
        //given
        MemberEntity member1 = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity member2 = createMember(getUserEmail("test11@naver.com"), "길동");

        memberRepository.saveAll(List.of(member1, member2));

        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity contentSave = contentRepository.save(content);

        ContentLogEntity contentLog1 =  ContentLogEntity.create(member1, content);
        ContentLogEntity contentLog2 =  ContentLogEntity.create(member1, content);

        ContentLogEntity contentLog4 =  ContentLogEntity.create(member2, content);
        ContentLogEntity contentLog5 =  ContentLogEntity.create(member2, content);
        ContentLogEntity contentLog6 =  ContentLogEntity.create(member2, content);

        contentLogRepository.saveAll(List.of(contentLog1, contentLog2, contentLog4, contentLog5, contentLog6));

        LocalDateTime startDateTime = LocalDateTime.now().minusWeeks(1);
        LocalDateTime endDateTime = LocalDateTime.now();
        MinorWorkType minorWorkType = MinorWorkType.ADULT_WORK;

        //when
        List<UserWithAdultContentResDto> usersWithAdultContentViews = memberRepository.findUsersWithAdultContentViews(
                startDateTime, endDateTime, minorWorkType
        );

        //then
        assertThat(usersWithAdultContentViews).hasSize(1)
                .extracting("userName", "userEmail")
                .containsExactlyInAnyOrder(
                        tuple("길동", "test11@naver.com")
                );
    }

    @DisplayName("최근 이주일 ~ 일주일 사이에 조회한 유져중에서 성인작품 3개 이상 조회한 사용자 목록은 나오지 않는다.")
    @Test
    void NoUsersWithThreeOrMoreAdultContentViewsLastTwoWeeks() {
        //given
        MemberEntity member1 = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity member2 = createMember(getUserEmail("test11@naver.com"), "길동");

        memberRepository.saveAll(List.of(member1, member2));

        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity contentSave = contentRepository.save(content);

        ContentLogEntity contentLog1 = ContentLogEntity.create(member1, content);
        ContentLogEntity contentLog2 = ContentLogEntity.create(member1, content);

        ContentLogEntity contentLog4 = ContentLogEntity.create(member2, content);
        ContentLogEntity contentLog5 = ContentLogEntity.create(member2, content);
        ContentLogEntity contentLog6 = ContentLogEntity.create(member2, content);

        contentLogRepository.saveAll(List.of(contentLog1, contentLog2, contentLog4, contentLog5, contentLog6));
        LocalDateTime startDateTime = LocalDateTime.now().minusWeeks(2);
        LocalDateTime endDateTime = LocalDateTime.now().minusWeeks(1);
        MinorWorkType minorWorkType = MinorWorkType.ADULT_WORK;

        //when
        List<UserWithAdultContentResDto> usersWithAdultContentViews = memberRepository.findUsersWithAdultContentViews(
                startDateTime, endDateTime, minorWorkType
        );

    //then

        assertThat(usersWithAdultContentViews).hasSize(0);
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