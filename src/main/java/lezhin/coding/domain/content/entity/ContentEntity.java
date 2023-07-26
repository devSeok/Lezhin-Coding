package lezhin.coding.domain.content.entity;


import lezhin.coding.domain.content.entity.embedded.Amount;
import lezhin.coding.domain.content.entity.embedded.Content;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content")
public class ContentEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contentId")
    @Id
    private Long id;

    @Embedded
    private Content content;

    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Embedded
    private Amount amount;
}
