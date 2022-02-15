package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.models.staff.RoleType;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.toptal.com/spring/spring-security-tutorial
 * https://github.com/Yoh0xFF/java-spring-security-example
 */
@ComponentScan("ar.edu.itba.paw.webapp.auth")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Autowired
    private AccessControl accessControl;

    /**
     * Access control methods
     */
    private static final String ACCESS_CONTROL_CHECK_USER = "@accessControl.checkUser(request, #username)";
    private static final String ACCESS_CONTROL_CHECK_NOTIFICATION_OWNER = "@accessControl.checkNotificationOwner(request, #id)";

    /**
     * User Roles
     */
    private static final String ADMIN = UserRole.ADMIN.toString();
    private static final String MOD = UserRole.MOD.toString();
    private static final String USER = UserRole.USER.toString();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(
                webExpressionVoter(),
                new RoleVoter(),
                new AuthenticatedVoter()
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

    @Bean
    public JwtTokenUtil jwtTokenUtil(@Value("classpath:jwt.key") Resource jwtKeyResource) throws IOException {
        return new JwtTokenUtil(jwtKeyResource);
    }

    // Expose authentication manager bean
    @Override @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
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
                .accessDecisionManager(accessDecisionManager())

                /**
                 * ModRequest Controller
                 */
                .antMatchers("/mods-requests/**")
                    .hasRole(ADMIN)

                /**
                 * Notification Controller
                 */
                .antMatchers("/notifications/{id}")
                    .access(ACCESS_CONTROL_CHECK_NOTIFICATION_OWNER)

                /**
                 * User Controller
                 */
                .antMatchers(HttpMethod.POST,"/users")
                    .anonymous()

                .antMatchers("/users/reset-password", "/users/verification")
                    .anonymous()

                .antMatchers("/users/{username}/mod")
                    .hasRole(ADMIN)

                .antMatchers("/users/{username}/locked")
                    .hasRole(MOD)

                .antMatchers(HttpMethod.POST, "/users/{username}/**")
                .access(ACCESS_CONTROL_CHECK_USER)

                .antMatchers(HttpMethod.DELETE, "/users/{username}/**")
                    .access(ACCESS_CONTROL_CHECK_USER)

                .antMatchers(HttpMethod.PUT, "/users/{username}/**")
                    .access(ACCESS_CONTROL_CHECK_USER)

                .antMatchers(HttpMethod.GET, "/users/{username}/notifications", "/users/{username}/collab-requests")
                    .access(ACCESS_CONTROL_CHECK_USER)

//                .antMatchers("/register/**", "/login", "/forgotPassword", "/resetPassword").anonymous()
//                .antMatchers("/settings", "/changePassword", "/deleteUser").authenticated()
//                .antMatchers("/lists/new/**", "lists/edit/**", "/report/**").hasRole("USER")
//                .antMatchers("/admin/mods/**").hasRole("ADMIN")
//                .antMatchers("/admin/**").hasRole("MOD")
//                .antMatchers(HttpMethod.POST).hasRole("USER")
//                .antMatchers(HttpMethod.DELETE).hasRole("USER")
                .antMatchers("/**").permitAll()

                // Disable CSRF
                .and().csrf().disable()

                // Add JWT Token Filter
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().
                antMatchers("/resources/**"); //Apago SpringSecurity para los assets publicos
    }
}

