package io.mountblue.stackoverflowclone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, true from users where email=?"
        );
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select email, password from users where email=?"
        );
        return  jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/", "/search", "/filters", "/register","/homepage","/allQuestions","/tagList","/userList",
                                        "/showTag","/showUser","/question/", "/saveUser", "/search", "/question/**","/searchQuestion", "/filters","/question").permitAll()
                                .requestMatchers("/addQuestion", "/deleteQuestion/{questionId}",
                                        "/saveQuestion", "/reviewQuestion").authenticated()
                                .requestMatchers("/**").authenticated()
                                .anyRequest().authenticated())
                .formLogin(form ->
                        form.loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                .logout(logout ->
                        logout.permitAll())
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                );
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

}
