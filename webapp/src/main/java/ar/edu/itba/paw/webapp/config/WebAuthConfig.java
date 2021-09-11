package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.concurrent.TimeUnit;

@ComponentScan("ar.edu.itba.paw.webapp.auth")
@EnableWebSecurity
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl pawUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pawUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .invalidSessionUrl("/")
                .and().formLogin()
                .defaultSuccessUrl("/", false)
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .userDetailsService(pawUserDetailsService)
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and().authorizeRequests()
                .antMatchers("/register", "/login").anonymous()
                .antMatchers("/").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/**").authenticated() //TODO
                .and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().
                antMatchers(); //Apago SpringSecurity para los assets publicos
    }
}

