package com.example.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // configuration class로 등록한다.
@EnableWebSecurity // Security 설정을 진행한다
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login","/join","/joinProc").permitAll() // 모든 사람한테 허용
                        .requestMatchers("/admin").hasRole("ADMIN") // role이 admin인 사람만 허용
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER") // role이 admin이든 user든 허용
                        .anyRequest().authenticated() // anyRequest() => 따로 처리하지 못한 경로들에 대한 설정
                                                    // authenicated() => 로그인 한 사용자 허용
                                                    // <=> denyAll() => 모두 거부
                );
        http
                .formLogin((auth) -> auth.loginPage("/login") // formLogin() => 인증이 필요할 때 이동할 페이지 설정
                                                                // loginPage("/login") => 로그인 페이지 경로를 호출
                        .loginProcessingUrl("/loginProc")   // 로그인을 처리하는 경로
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable()); // post 요청을 보낼때 csrf 토큰을 보내주어야 로그인이 진행됨
                                                    // 이 과정에서는 보내주지 않기 때문에 disable
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(true));
        /*
        sessionManagement() 메소드를 통한 설정을 진행한다.
        maximumSession(정수) : 하나의 아이디에 대한 다중 로그인 허용 개수
        maxSessionPreventsLogin(불린) : 다중 로그인 개수를 초과하였을 경우 처리 방법
                - true : 초과시 새로운 로그인 차단
                - false : 초과시 기존 세션 하나 삭제
         */
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());
        /*
        - sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
- sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
- sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경
         */

        return http.build();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
}
