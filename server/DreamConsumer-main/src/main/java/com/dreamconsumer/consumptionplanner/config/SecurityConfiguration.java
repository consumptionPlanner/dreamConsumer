package com.dreamconsumer.consumptionplanner.config;

import com.dreamconsumer.consumptionplanner.auth.filter.JwtAuthenticationFilter;
import com.dreamconsumer.consumptionplanner.auth.filter.JwtVerificationFilter;
import com.dreamconsumer.consumptionplanner.auth.handler.UserAuthenticationFailureHandler;
import com.dreamconsumer.consumptionplanner.auth.handler.UserAuthenticationSuccessHandler;
import com.dreamconsumer.consumptionplanner.auth.jwt.JwtTokenizer;
import com.dreamconsumer.consumptionplanner.auth.utils.AuthorityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {

    private final JwtTokenizer jwtTokenizer;
    private final AuthorityUtils authorityUtils;

    public SecurityConfiguration(JwtTokenizer jwtTokenizer, AuthorityUtils authorityUtils) {
        this.jwtTokenizer = jwtTokenizer;
        this.authorityUtils = authorityUtils;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin() // clickJacking 막고자 frameOption() 디폴트 DENY로 활성화되어 있음. 동일 출처 request만 페이지 렌더링 허용
                .and()
                .csrf().disable()
                //.cors(Customizer.withDefaults()) // CorsConfigurationSource Bean 사용
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable() // form Login은 SSR에서 사용
                .httpBasic().disable() // httpBasic : UserName/Password 정보를 http header에 실어 인증
                .apply(new CustomFilterConfigure())   // (1)
                .and()
                .authorizeHttpRequests(authorize -> authorize
                        .antMatchers("/users/sign-up").permitAll()
                        .antMatchers("/users/sign-in").permitAll()
                        .antMatchers(HttpMethod.DELETE,"/users/{member-id}").hasRole("USER") // 회원 탈퇴
                        .antMatchers(HttpMethod.PATCH, "/activate/{member-id}").hasRole("USER") // 휴면 계정 해제
                        .antMatchers(HttpMethod.GET,"/users/{member-id}").hasRole("USER")
                        .antMatchers("/**").permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080"));
        corsConfiguration.setAllowedMethods(Arrays.asList("POST", "GET", "PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin",
                "Access-Control-Allow-Methods",
                "Access-Control-Allow-Headers"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Accept", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);


        //  CorsConfigurationSource 인터페이스의 구현 클래스 UrlBasedCorsConfigurationSource
        //  source에 configuration 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    public class CustomFilterConfigure extends AbstractHttpConfigurer<CustomFilterConfigure, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtTokenizer);
            jwtAuthenticationFilter.setFilterProcessesUrl("/users/sign-in");
            jwtAuthenticationFilter.setAuthenticationSuccessHandler(new UserAuthenticationSuccessHandler()); // 404 오류 수정
            jwtAuthenticationFilter.setAuthenticationFailureHandler(new UserAuthenticationFailureHandler());

            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer, authorityUtils);
            builder.addFilter(jwtAuthenticationFilter)
                    .addFilterAfter(jwtVerificationFilter, JwtAuthenticationFilter.class);
        }
    }
}