package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.example.security.security.CustomLoginSuccessHandler;

@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {

        http
                // .authorizeHttpRequests(authorize -> authorize
                // .requestMatchers("/", "/sample/guest").permitAll()
                // .requestMatchers("/sample/member").hasRole("USER")
                // .requestMatchers("/sample/admin").hasRole("ADMIN")
                // .anyRequest().authenticated())
                // .httpBasic(Customizer.withDefaults());

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**", "/image/**")
                        .permitAll()
                        .requestMatchers("/board/read").permitAll()
                        .requestMatchers("/board/modify").hasAnyRole("ADMIN", "MANAGER", "USER")
                        .anyRequest().permitAll())

                .formLogin(login -> login.loginPage("/member/login")
                        .successHandler(successHandler())
                        .permitAll())
                .oauth2Login(login -> login.successHandler(successHandler()));

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/"));
        http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));
        return http.build();
    }

    @Bean // = new 한 후 스프링 컨테이너가 관리
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CustomLoginSuccessHandler successHandler() {
        return new CustomLoginSuccessHandler();
    }

    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("mykey", userDetailsService,
                encodingAlgorithm);
        rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);
        return rememberMeServices;
    }
    // @Bean
    // UserDetailsService users() {
    // UserDetails user = User.builder()
    // .username("user")
    // .password("{bcrypt}$2a$10$4hb4tlSVolRPZEJbi56Zt.gyrTNLj6i.irJV5ixMeE/.L2p3xGaje")
    // .roles("USER") // ROLE_
    // .build();

    // UserDetails admin = User.builder()
    // .username("admin")
    // .password("{bcrypt}$2a$10$4hb4tlSVolRPZEJbi56Zt.gyrTNLj6i.irJV5ixMeE/.L2p3xGaje")
    // .roles("USER", "ADMIN") // ROLE_
    // .build();
    // return new InMemoryUserDetailsManager(user, admin);
    // }

}
