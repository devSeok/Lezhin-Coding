package lezhin.coding.domain.member.service;

import lezhin.coding.domain.member.domain.entity.MemberEntity;
import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lezhin.coding.domain.member.domain.repository.MemberRepository;
import lezhin.coding.domain.member.dto.reponse.MemberLoginResDto;
import lezhin.coding.domain.member.dto.reponse.MemberSignupResDto;
import lezhin.coding.domain.member.dto.request.MemberLoginReqDto;
import lezhin.coding.domain.member.dto.request.MemberSignupReqDto;
import lezhin.coding.global.exception.error.exception.EmailDuplicationException;
import lezhin.coding.global.exception.error.exception.ErrorCode;
import lezhin.coding.global.exception.error.exception.UserNotException;
import lezhin.coding.global.jwt.TokenProvider;
import lezhin.coding.global.jwt.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberSignupResDto memberSignup(MemberSignupReqDto memberDto) {

        if (memberRepository.existsByUserEmail(memberDto.getUserEmail())) {
            throw new EmailDuplicationException(ErrorCode.EXISTENT_EMAIL.getMessage());
        }
        MemberEntity memberSave = memberRepository.save(memberDto.toEntity(passwordEncoder));

        return MemberSignupResDto.of(memberSave);
    }


    public MemberLoginResDto login(MemberLoginReqDto memberLoginReqDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = memberLoginReqDto.toAuthentication();

        UserEmail email = UserEmail.builder()
                .value(memberLoginReqDto.getEmail())
                .build();

        MemberEntity byEmail = memberRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotException(ErrorCode.USER_NOT_FOUND.getMessage()));

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        return MemberLoginResDto.from(tokenDto);
    }
}
