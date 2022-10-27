package lezhin.coding.domain.content.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Comments")
public class CommentsEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentsId")
    @Id
    private Long id;


}
