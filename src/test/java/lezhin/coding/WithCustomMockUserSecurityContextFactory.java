package lezhin.coding;

import lezhin.coding.domain.member.domain.entity.MemberEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {


    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String userUuid = annotation.userUuid();
        String role = annotation.role();

        //여기서 바인딩되어 반환할 객체를 정의해주면 됩니다



        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userUuid, "password", List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
