package lezhin.coding.domain.content.dto;

import lezhin.coding.domain.content.domain.content.PayType;
import lezhin.coding.domain.content.domain.evaluation.EvaluationType;
import lezhin.coding.global.EnumTypeValid;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EvaluationReqDto {

    @EnumTypeValid(target = EvaluationType.class, message = "좋아요 싫어요는 LIKE or BAD 이어야합니다.")
    private String evaluationType;

    private String comment;

    @NotNull(message = "컨텐츠 id값은 필수 입니다.")
    private Long contentId;


}
