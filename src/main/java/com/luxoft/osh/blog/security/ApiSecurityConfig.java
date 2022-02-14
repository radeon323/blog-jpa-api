package com.luxoft.osh.blog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

import static com.luxoft.osh.blog.security.ApiUserPermission.*;
import static com.luxoft.osh.blog.security.ApiUserRole.*;

/**
 * @author Oleksandr Shevchenko
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
                // TODO: @PreAuthorize annotation possibly will be better and more convenient
                .antMatchers(HttpMethod.DELETE,"/api/v1/posts/{postId}/comments/**").hasAuthority(COMMENT_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/api/v1/posts/{id}/comments/**").hasAuthority(COMMENT_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/api/v1/posts/{postId}/comments/**").hasAuthority(COMMENT_WRITE.getPermission())

                .antMatchers(HttpMethod.DELETE,"/api/v1/**").hasAuthority(POST_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/api/v1/**").hasAuthority(POST_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/api/v1/**").hasAuthority(POST_WRITE.getPermission())

                .antMatchers(HttpMethod.GET, "/api/v1/**")
                .hasAnyAuthority(POST_READ.getPermission(), COMMENT_READ.getPermission(), TAG_READ.getPermission(), USER_READ.getPermission())

                .anyRequest()
                .authenticated()
                .and()
//                .httpBasic();
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/posts",true)
                .and()
                    .rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) // defaults to 2 weeks!!!
                    .key("somethingverysecured")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // delete this line if csrf() enable !!!
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails radeon323User = User.builder()
                .username("radeon323")
                .password(passwordEncoder.encode("pass"))
                .authorities(USER.getGrantedAuthorities())
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("pass"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                radeon323User,
                admin
        );
    }



}
