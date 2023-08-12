package lezhin.coding.domain.content.domain.comment;

import lezhin.coding.IntegrationTestSupport;
import lezhin.coding.domain.content.domain.content.Amount;
import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.content.domain.content.enums.MinorWorkType;
import lezhin.coding.domain.content.domain.content.enums.PayType;
import lezhin.coding.domain.content.domain.content.repository.ContentRepository;
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

@Transactional
class CommentRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ContentRepository contentRepository;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
        contentRepository.deleteAllInBatch();
    }

    @DisplayName("사용자가 작품에 대한 댓글을 전체 삭제한다.")
    @Test
    void deleteCommentsByMemberId() {
        MemberEntity member = createMember(getUserEmail("test@naver.com"), "진석");
        MemberEntity saveMember = memberRepository.save(member);

        ContentEntity content = createContent(MinorWorkType.ADULT_WORK);
        ContentEntity saveContent = contentRepository.save(content);

        Comment comment = Comment.builder().value("test").build();
        CommentEntity comments = CommentEntity.create(saveContent, saveMember, comment);
        commentRepository.save(comments);


        commentRepository.deleteCommentsByMemberId(saveMember.getId());

        assertThat(commentRepository.findAll()).hasSize(0);

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