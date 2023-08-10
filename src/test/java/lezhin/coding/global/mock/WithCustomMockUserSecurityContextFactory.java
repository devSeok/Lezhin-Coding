package lezhin.coding.global.mock;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithCustomMockUserSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {


    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String userEmail = annotation.userEmail();
        String role = annotation.role();

        //여기서 바인딩되어 반환할 객체를 정의해주면 됩니다

        UserDetails principal = new User(userEmail, "", List.of(new SimpleGrantedAuthority(role)));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(principal, "password", List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
