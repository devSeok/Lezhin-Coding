package lezhin.coding.domain.content.domain.content.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class RankResultDto {

    private List<TuplieResult> like;
    private List<TuplieResult> bad;

    @Builder
    public RankResultDto(List<TuplieResult> like, List<TuplieResult> bad) {
        this.like = like;
        this.bad = bad;
    }

    public static RankResultDto of(final List<TuplieResult> like, final List<TuplieResult> bad) {
        return RankResultDto.builder()
                .like(like)
                .bad(bad)
                .build();
    }
}
