package lezhin.coding.domain.content.domain.comment;


import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Comments")
public class CommentEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    @Id
    private Long id;

    @Embedded
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private ContentEntity content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;


    @Builder
    public CommentEntity(Comment comment, ContentEntity content, MemberEntity member) {
        this.comment = comment;
        this.content = content;
        this.member = member;
    }

    public static CommentEntity create(ContentEntity findContent, MemberEntity findMember, Comment comment) {
        return CommentEntity.builder()
                .comment(comment)
                .content(findContent)
                .member(findMember)
                .build();
    }
}
