package lezhin.coding.domain.member.service.impl;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.content.domain.comment.Comment;
import lezhin.coding.domain.content.domain.comment.CommentEntity;
import lezhin.coding.domain.content.domain.comment.CommentRepository;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.content.domain.content.enums.PayType;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
import lezhin.coding.domain.content.domain.contentLog.ContentLogEntity;
import lezhin.coding.domain.content.domain.contentLog.repository.ContentLogRepository;
import lezhin.coding.domain.content.domain.evaluation.EvaluationEntity;
import lezhin.coding.domain.content.domain.evaluation.EvaluationRepository;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.entity.embedded.UserName;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.domain.member.dto.reponse.UserWithAdultContentResDto;
import lezhin.coding.domain.member.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContentRepository contentRepository;
    @Autowired
    private ContentLogRepository contentLogRepository;
    @Autowired
    private MemberService memberServiceImpl;
    @Autowired
    private EvaluationRepository evaluationRepository;
    @Autowired
    private CommentRepository commentRepository;


    @AfterEach
    void tearDown() {
        contentLogRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        evaluationRepository.deleteAllInBatch();
    }

    @DisplayName("최근 1주일간 성인작품 3개 이상 조회한 사용자 목록을 조회한다.")
    @Test
    void findUsersWithAdultContentViews() {
    // given
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
        List<UserWithAdultContentResDto> usersWithAdultContentViews = memberServiceImpl.findUsersWithAdultContentViews(startDateTime, endDateTime, minorWorkType);
        //then

        assertThat(usersWithAdultContentViews).hasSize(2)
                .extracting("userName", "userEmail")
                .containsExactlyInAnyOrder(
                        tuple("진석", "test@naver.com"),
                        tuple("길동", "test11@naver.com")
                );
    }

    @DisplayName("특정 유저 회원탈퇴할시 평가, 조회 이력를 삭제한다.")
    @Test
    void memberDelete() {
    //given
        MemberEntity member1 = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity saveMember = memberRepository.save(member1);

        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity contentSave = contentRepository.save(content);

        ContentLogEntity contentLog =  ContentLogEntity.create(member1, content);
        contentLogRepository.save(contentLog);

        EvaluationEntity evaluationEntity = EvaluationEntity.create(saveMember, contentSave, "LIKE");
        evaluationRepository.save(evaluationEntity);

        Comment commentBuild = Comment.builder().value("test").build();
        CommentEntity commentSave = CommentEntity.create(contentSave, saveMember, commentBuild);
        commentRepository.save(commentSave);

        //when
        memberServiceImpl.memberDelete(saveMember.getId());

        //then
        assertThat(memberRepository.findById(saveMember.getId())).isEmpty();
        assertThat(evaluationRepository.findAll()).hasSize(0).isEmpty();;
        assertThat(commentRepository.findAll()).hasSize(0).isEmpty();
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