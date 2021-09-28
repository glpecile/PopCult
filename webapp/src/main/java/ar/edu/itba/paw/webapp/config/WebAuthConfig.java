package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.EditListVoter;
import ar.edu.itba.paw.webapp.auth.RequestsManagerVoter;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ComponentScan("ar.edu.itba.paw.webapp.auth")
@EnableWebSecurity
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl pawUserDetailsService;

    @Autowired
    private EditListVoter editListVoter;

    @Autowired
    private RequestsManagerVoter requestsManagerVoter;

    @Value("classpath:rememberMe.key")
    private Resource rememberMeKeyResource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler("/loginFailed");
        simpleUrlAuthenticationFailureHandler.setUseForward(true);
        return simpleUrlAuthenticationFailureHandler;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                new WebExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                editListVoter,
                requestsManagerVoter
        );
        return new UnanimousBased(decisionVoters);
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
                .failureHandler(authenticationFailureHandler())
                .usernameParameter("username")
                .passwordParameter("password")
                .and().rememberMe()
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
                .userDetailsService(pawUserDetailsService)
                .rememberMeParameter("rememberme")
                .key(FileCopyUtils.copyToString(new InputStreamReader(rememberMeKeyResource.getInputStream())))
                .and().logout()
                .deleteCookies("JSESSIONID")
                .deleteCookies("remember-me")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and().authorizeRequests()
                .antMatchers("/register/**", "/login").anonymous()
                .antMatchers("/createList").hasRole("EDITOR")
                .antMatchers("/editList/**").hasRole("EDITOR").accessDecisionManager(accessDecisionManager())
                .antMatchers(HttpMethod.POST).hasRole("EDITOR")
                .antMatchers(HttpMethod.DELETE).hasRole("EDITOR")
                .antMatchers("/**").permitAll()
                .and().exceptionHandling()
                .accessDeniedPage("/403")
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().
                antMatchers("/resources/**"); //Apago SpringSecurity para los assets publicos
    }
}

