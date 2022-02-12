package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.models.user.UserRole;
import ar.edu.itba.paw.webapp.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * https://www.toptal.com/spring/spring-security-tutorial
 */
@ComponentScan("ar.edu.itba.paw.webapp.auth")
@EnableWebSecurity
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl pawUserDetailsService;

    @Autowired
    private UserPanelManagerVoter userPanelManagerVoter;

    @Autowired
    private DeleteCommentVoter deleteCommentVoter;

    @Autowired
    private ListsManagerVoter listsManagerVoter;

    @Autowired
    private ListsVoter listsVoter;

    @Value("classpath:rememberMe.key")
    private Resource rememberMeKeyResource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                webExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter(),
                userPanelManagerVoter,
                deleteCommentVoter,
                listsManagerVoter,
                listsVoter
        );
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public WebExpressionVoter webExpressionVoter() {
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
        return webExpressionVoter;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = String.format("%s > %s and %s > %s",
                UserRole.ADMIN.getRoleType(),
                UserRole.MOD.getRoleType(),
                UserRole.MOD.getRoleType(),
                UserRole.USER.getRoleType());
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pawUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                // Set session management to stateless
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // Set exception handlers
                .and().exceptionHandling()
                    // Set unauthorized requests exception handler
                    .authenticationEntryPoint(new UnauthorizedRequestHandler())
                    // Set forbidden requests exception handler
                    .accessDeniedHandler(new ForbiddenRequestHandler())

                // Set permissions on endpoints
                .and().authorizeRequests()
//                .accessDecisionManager(accessDecisionManager())
//                .antMatchers("/register/**", "/login", "/forgotPassword", "/resetPassword").anonymous()
//                .antMatchers("/settings", "/changePassword", "/deleteUser").authenticated()
//                .antMatchers("/lists/new/**", "lists/edit/**", "/report/**").hasRole("USER")
//                .antMatchers("/admin/mods/**").hasRole("ADMIN")
//                .antMatchers("/admin/**").hasRole("MOD")
//                .antMatchers(HttpMethod.POST).hasRole("USER")
//                .antMatchers(HttpMethod.DELETE).hasRole("USER")
                .antMatchers("/**").permitAll()
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().
                antMatchers("/resources/**"); //Apago SpringSecurity para los assets publicos
    }
}

