package org.example.backend.config;

import org.example.backend.domain.User;
import org.example.backend.service.UserService;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*super.configure(http);
        http
                .logout().logoutSuccessUrl("/");*/
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/static/**", "/js/**", "/error**").permitAll()
//                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PrincipalExtractor principalExtractor(UserService userService) {
        return map -> {
            String id = (String) map.get("sub");
            User user = userService.getUserById(id).orElseGet(() ->
                    new User(id,
                            (String) map.get("name"),
                            (String) map.get("email"),
                            (String) map.get("picture")));
            return userService.saveUser(user);
        };
    }
}
