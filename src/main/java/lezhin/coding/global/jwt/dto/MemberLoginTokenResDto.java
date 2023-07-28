package lezhin.coding.global.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLoginTokenResDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;

    public static MemberLoginTokenResDto from(TokenDto dto) {
        return new MemberLoginTokenResDto(dto.getGrantType(), dto.getAccessToken(), dto.getRefreshToken(), dto.getAccessTokenExpiresIn());
    }
}
