package lezhin.coding.domain.content.domain.contentLog;


import lezhin.coding.global.common.utils.SecurityUtil;
import lezhin.coding.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "content_log")
public class ContentLogEntity extends BaseTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_log_id")
    @Id
    private Long id;

    private Long contentId;

    private Long memberId;

    @Builder
    public ContentLogEntity(Long contentId, Long memberId) {
        this.contentId = contentId;
        this.memberId = memberId;
    }
    public static ContentLogEntity create(Long contentId, Long memberId) {
      return ContentLogEntity.builder()
                .contentId(contentId)
                .memberId(memberId)
                .build();
    }
}
