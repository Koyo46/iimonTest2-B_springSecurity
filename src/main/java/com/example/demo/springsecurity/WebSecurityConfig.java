package com.example.demo.springsecurity;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

  @Autowired
  private DataSource dataSource;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
      BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
      return bCryptPasswordEncoder;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin(login -> login

        .loginProcessingUrl("/login")

        .loginPage("/login")

        .defaultSuccessUrl("/post")

        .failureUrl("/login?error")

        .permitAll()
).logout(logout -> logout
        .logoutSuccessUrl("/post")

).authorizeHttpRequests(ahr -> ahr
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        .mvcMatchers("/post").permitAll()
        .mvcMatchers("/regist").permitAll()
        .mvcMatchers("/postForm").permitAll()

        .anyRequest().authenticated()
        );
      return http.build();
  }

  @Autowired
  public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication()
              .dataSource(dataSource)
              .usersByUsernameQuery(
                      "select userName as username, password, enabled from user where userName = ?")
              .passwordEncoder(new BCryptPasswordEncoder());
  }



}
