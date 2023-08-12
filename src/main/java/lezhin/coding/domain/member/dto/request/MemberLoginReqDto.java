package lezhin.coding.domain.member.dto.request;

import lezhin.coding.domain.member.domain.entity.embedded.UserEmail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class MemberLoginReqDto {

    @Valid
    private UserEmail email;

    @NotBlank(message = "비밀번호은 필수입니다.")
    private String password;

    @Builder
    public MemberLoginReqDto(UserEmail email, String password) {
        this.email = email;
        this.password = password;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(this.email.getValue(), this.password);
    }
}
