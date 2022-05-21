package com.infoshareacademy.responsibledrinkersweb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Properties;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}user").roles("USER").disabled(false)
//                .and()
//                .withUser("admin").password("{noop}admin").roles("ADMIN")
//                .and()
//                .withUser("superadmin").password("{noop}superadmin").roles("ADMIN", "USER");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(inMemoryUserDetailsManager());
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        final Properties users = new Properties();
        users.put("user", "{noop}user,ROLE_USER,enabled");
        users.put("admin", "{noop}admin,ROLE_ADMIN,ROLE_USER,enabled");
        return new InMemoryUserDetailsManager(users);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/img/**", "/bootstrap/**", "/js/**", "/favicon/**").permitAll()
                .antMatchers("/panel").hasRole("ADMIN")
                .antMatchers("/add_new_drink", "/manager").hasAnyRole("ADMIN", "USER")
                .antMatchers("/", "/index", "/list", "/signup", "/new_account", "/show-more", "/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login?logout").permitAll()
                .deleteCookies("JSESSIONID");
    }

}
