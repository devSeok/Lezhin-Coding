package lezhin.coding.domain.content.dto.response;

import lezhin.coding.domain.content.domain.content.dto.RankingContentResultDto;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingResultResDto {

    private List<RankingContentResultDto> like;
    private List<RankingContentResultDto> bad;

    @Builder
    public RankingResultResDto(List<RankingContentResultDto> like, List<RankingContentResultDto> bad) {
        this.like = like;
        this.bad = bad;
    }

    public static RankingResultResDto of(final List<RankingContentResultDto> like, final List<RankingContentResultDto> bad) {
        return RankingResultResDto.builder()
                .like(like)
                .bad(bad)
                .build();
    }
}
