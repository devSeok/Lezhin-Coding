package lezhin.coding.domain.content.domain.contentLog;


import lezhin.coding.domain.content.domain.content.ContentEntity;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content_log")
public class contentLogEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_log_id")
    @Id
    private Long id;

}
