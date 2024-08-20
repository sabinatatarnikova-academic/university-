package com.foxminded.university.config.security;

import com.foxminded.university.config.ServiceConfig;
import com.foxminded.university.service.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Import(ServiceConfig.class)
public class SecurityConfig  {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/admin", "/api/v1/admin").hasRole("ADMIN")
                                .requestMatchers("/teacher", "/api/v1/teacher").hasRole("TEACHER")
                                .requestMatchers("/student", "/api/v1/student").hasRole("STUDENT")
                                .requestMatchers("/", "/api/v1/").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .userDetailsService(userDetailsService)
                .formLogin((form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .failureUrl("/login?error=true")))
                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/admin/schedule/classes/add")))
                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/admin/schedule/edit")))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring()
                .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/vendor/**", "/fonts/**");
    }
}
