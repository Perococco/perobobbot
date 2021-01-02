package perobobbot.server.config.security;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import perobobbot.data.service.UserProvider;
import perobobbot.security.PermissionEvaluatorDispatcher;
import perobobbot.security.TargetedPermissionEvaluator;
import perobobbot.server.EndPoints;
import perobobbot.server.config.security.jwt.JwtAuthenticationFilter;
import perobobbot.server.config.security.jwt.JWTokenServiceFromUserService;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @NonNull
    private final PasswordEncoder passwordEncoder;

    @NonNull
    private final UserProvider userProvider;

    @NonNull
    private final JWTokenServiceFromUserService jsonWebTokenService;

    public @NonNull PermissionEvaluator permissionEvaluator(@NonNull List<TargetedPermissionEvaluator> evaluators) {
        final var map = evaluators.stream().collect(ImmutableMap.toImmutableMap(TargetedPermissionEvaluator::getTargetType, e -> e));
        return new PermissionEvaluatorDispatcher(map);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailServiceWithUserProvider(userProvider))
            .passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable();

        http.addFilterBefore(new JwtAuthenticationFilter(jsonWebTokenService), BasicAuthenticationFilter.class);
        http.cors();

        http.authorizeRequests()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/api/debug/**").permitAll()
            .antMatchers(HttpMethod.POST, EndPoints.fullPath(EndPoints.SIGN_IN)).permitAll()
            .antMatchers(HttpMethod.POST, EndPoints.fullPath(EndPoints.SIGN_UP)).permitAll()
            .antMatchers(EndPoints.fullPath("/**"))
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }


}
