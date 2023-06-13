package com.example.foody.utils;

import com.example.foody.services.CustomUserDetailService;
import com.example.foody.services.OAuthService;
import com.example.foody.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao. DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails. UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password. PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuthService oAuthService;
    private final UserService userService;
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        return http.csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**","/scss/**","/vendor/**", "/", "/register", "/error","/images/**")
                        .permitAll()
                        .requestMatchers("/products/edit/**", "/products/delete/**")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers("/admin","/products","products/add")
                        .hasAnyAuthority("ADMIN")
                        .requestMatchers("/") // Add this line
                        .hasAnyAuthority("USER") // Restrict access to users with the "USER" role

                        .anyRequest().authenticated()

                )
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()

                )
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error=true")
                        .permitAll()

                ).oauth2Login(
                        oauth2Login -> oauth2Login.loginPage("/login")
                                .failureUrl("/login?error")
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(oAuthService)
                                )
                                .successHandler(
                                        (request, response,
                                         authentication) -> {
                                            var oidcUser =
                                                    (DefaultOidcUser) authentication.getPrincipal();
                                            userService.saveOauthUser(oidcUser.getEmail(), oidcUser.getName());
                                            response.sendRedirect("/");
                                        }
                                )
                                .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/403"))

                .build();
    }
}