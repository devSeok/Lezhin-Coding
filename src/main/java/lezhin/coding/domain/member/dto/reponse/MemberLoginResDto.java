package lezhin.coding.domain.member.dto.reponse;

import lezhin.coding.global.jwt.dto.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginResDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public static MemberLoginResDto from(TokenDto dto) {
        return new MemberLoginResDto(dto.getGrantType(), dto.getAccessToken(), dto.getRefreshToken(), dto.getAccessTokenExpiresIn());
    }
}
