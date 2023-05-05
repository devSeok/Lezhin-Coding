package lezhin.coding.domain.content.entity;


import lezhin.coding.domain.content.entity.embedded.Comment;
import lezhin.coding.domain.member.entity.MemberEntity;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Comments")
public class CommentsEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    @Id
    private Long id;

    @Embedded
    private Comment comment;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "comments_id")
    private List<ContentEntity> contentEntity = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private List<MemberEntity> memberEntity = new ArrayList<>();

}
