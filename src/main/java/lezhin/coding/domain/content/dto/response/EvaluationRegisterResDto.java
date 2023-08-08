package lezhin.coding.domain.content.dto.response;

import lezhin.coding.domain.content.domain.comment.CommentEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationRegisterResDto {

    private Long contentId;

    private String comment;


    @Builder
    public EvaluationRegisterResDto(Long contentId, String comment) {
        this.contentId = contentId;
        this.comment = comment;
    }

    public static EvaluationRegisterResDto of(CommentEntity comment) {
        return EvaluationRegisterResDto.builder()
                .comment(comment.getComment().getValue())
                .contentId(comment.getContent().getId())
                .build();
    }

}
