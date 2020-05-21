package perobobbot.server.config.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.server.EndPoints;
import perobobbot.server.config.security.jwt.JwtAuthenticationFilter;
import perobobbot.server.config.security.jwt.JwtTokenManager;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final JwtTokenManager jwtTokenManager;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailServiceWithUserRepository(userRepository))
            .passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable();

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenManager), BasicAuthenticationFilter.class);
        http.cors();

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, EndPoints.fullPath(EndPoints.LOGIN)).permitAll()
            .antMatchers(HttpMethod.POST,EndPoints.fullPath(EndPoints.SIGN_UP)).permitAll()
            .antMatchers(EndPoints.fullPath("/**"))
            .authenticated()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new Http403ForbiddenEntryPoint());

    }






}
