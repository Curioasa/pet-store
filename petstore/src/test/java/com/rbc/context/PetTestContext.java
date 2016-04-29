package com.rbc.context;

import com.rbc.services.PetService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = {"com.rbc.controllers"})
public class PetTestContext {

    @Bean
    public PetService petService() {
        return Mockito.mock(PetService.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password")
                .roles("USER").and().withUser("admin").password("password")
                .roles("USER", "ADMIN");
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(
                new HttpSessionEventPublisher());
    }

    @Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll().anyRequest()
                    .authenticated()
                    .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .logoutUrl("/logout")
                    .and()
                    .csrf()
                    .disable();
        }
    }

}
