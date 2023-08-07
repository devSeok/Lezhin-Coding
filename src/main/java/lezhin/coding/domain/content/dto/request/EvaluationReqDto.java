package lezhin.coding.domain.content.dto.request;

import lezhin.coding.domain.content.domain.comment.Comment;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;
import lezhin.coding.global.EnumTypeValid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationReqDto {

    @EnumTypeValid(target = EvaluationType.class, message = "좋아요 싫어요는 LIKE or BAD 이어야합니다.")
    private String evaluationType;

    private Comment comment;

    @NotNull(message = "컨텐츠 id값은 필수 입니다.")
    private Long contentId;


    @Builder
    public EvaluationReqDto(String evaluationType, Comment comment, Long contentId) {
        this.evaluationType = evaluationType;
        this.comment = comment;
        this.contentId = contentId;
    }
}
