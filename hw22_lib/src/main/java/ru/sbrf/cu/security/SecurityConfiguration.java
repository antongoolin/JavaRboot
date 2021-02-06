package ru.sbrf.cu.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/api/library/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/api/library/*").permitAll()
                .and()
                .authorizeRequests().antMatchers("/api/library/books/book").authenticated()
                .and()
                .authorizeRequests().antMatchers("/api/library/**").hasRole("LIBRARY")
                .and()
                .exceptionHandling().accessDeniedPage("/api/library/accessDenied")
                .and()
                .formLogin()
                .defaultSuccessUrl("/api/library/success")
                .failureUrl("/api/library/error")
                .and()
                .anonymous()
                .principal(new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return null;
                    }

                    @Override
                    public String getPassword() {
                        return null;
                    }

                    @Override
                    public String getUsername() {
                        return "anonymous";
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return false;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return false;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return false;
                    }

                    @Override
                    public boolean isEnabled() {
                        return false;
                    }
                })
                .and()
                .rememberMe()
                .tokenValiditySeconds(60 * 60 * 24)
                .key("123")
                .and()
                .logout().logoutUrl("/api/library/logout");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("LIBRARY").password("password").roles("LIBRARY")
                .and()
                .withUser("user").password("password").roles("USER")
        ;
    }
}

