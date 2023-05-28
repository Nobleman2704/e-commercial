package com.example.ecommercial.config;

import com.example.ecommercial.bot.ECommercialBot;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {
    private final ECommercialBot eCommercialBot;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final String[] WHITE_LIST = {
            "/test/add",
            "/"
    };

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
        return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(WHITE_LIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successForwardUrl("/test/dashboard-page")
                .and()
                .build();
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(eCommercialBot);
            System.out.println("started");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }
}

