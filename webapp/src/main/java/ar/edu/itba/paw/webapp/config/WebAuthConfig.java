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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

/**
 * https://www.toptal.com/spring/spring-security-tutorial
 * https://github.com/Yoh0xFF/java-spring-security-example
 *
 * Endpoints: https://docs.google.com/spreadsheets/d/12-d4w7wpwGuRHetUvtA7HINCAAQFAsUD5CVlg7ucaQ8/edit?usp=sharing
 *
 * Do not execute Reformat Code
 */
@ComponentScan("ar.edu.itba.paw.webapp.auth")
@EnableWebSecurity
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;
    @Autowired
    private BasicAuthFilter basicAuthFilter;
    @Autowired
    private AccessControl accessControl; //Ignore Private field 'accessControl' is assigned but never accessed

    /**
     * Access control methods
     * Ignore warnings.
     */
    // username is equal to authenticated username:
    private static final String ACCESS_CONTROL_CHECK_USER = "@accessControl.checkUser(request, #username)";
    // notification(id) belongs to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_NOTIFICATION_OWNER = "@accessControl.checkNotificationOwner(request, #id)";
    // list(id) belongs to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_LIST_OWNER = "@accessControl.checkListOwner(request, #id)";
    // list(id) does not belong to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_LIST_NOT_OWNER = "@accessControl.checkListNotOwner(request, #id)";
    //  list(id) is public or belongs to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_LIST_OWNER_COLLABORATOR_OR_PUBLIC = "@accessControl.checkListOwnerCollaboratorOrPublic(request, #id)";
    // list(id) is editable by authenticated user:
    private static final String ACCESS_CONTROL_CHECK_LIST_COLLABORATOR = "@accessControl.checkListCollaborator(request, #id)";
    // collabRequest(id) is associated to a list that belong to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_COLLAB_REQUEST_LIST_OWNER = "@accessControl.checkCollabRequestListOwner(request, #id)";
    // mediaComment(id) belongs to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_MEDIA_COMMENT_OWNER = "@accessControl.checkMediaCommentOwner(request, #id)";
    // mediaComment(id) does not belong to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_MEDIA_COMMENT_NOT_OWNER = "@accessControl.checkMediaCommentNotOwner(request, #id)";
    // listComment(id) belongs to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_LIST_COMMENT_OWNER = "@accessControl.checkListCommentOwner(request, #id)";
    // listComment(id) does not belong to authenticated user:
    private static final String ACCESS_CONTROL_CHECK_LIST_COMMENT_NOT_OWNER = "@accessControl.checkListCommentNotOwner(request, #id)";

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

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(ALL));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.addAllowedHeader(ALL);
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Link"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Expose authentication manager bean
    @Override @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint () {
        return new UnauthorizedRequestHandler();
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
                 * CollabRequest Controller
                 */
                .antMatchers("/collab-requests/{id}")
                    .access(ACCESS_CONTROL_CHECK_COLLAB_REQUEST_LIST_OWNER)

                /**
                 * List Controller
                 */
                .antMatchers(HttpMethod.POST, "/lists")
                    .authenticated()
                .antMatchers(HttpMethod.POST, "/lists/{id}/comments")
                    .access(ACCESS_CONTROL_CHECK_LIST_OWNER_COLLABORATOR_OR_PUBLIC)
                .antMatchers(HttpMethod.DELETE, "/lists/{id}", "/lists/{id}/collaborators/{username}")
                    .access(ACCESS_CONTROL_CHECK_LIST_OWNER)
                .antMatchers(HttpMethod.PUT, "/lists/{id}", "/lists/{id}/collaborators/{username}")
                    .access(ACCESS_CONTROL_CHECK_LIST_OWNER)
                .antMatchers(HttpMethod.DELETE, "/lists/{id}/media/{media-id}")
                    .access(ACCESS_CONTROL_CHECK_LIST_COLLABORATOR)
                .antMatchers(HttpMethod.PATCH, "/lists/{id}/media")
                    .access(ACCESS_CONTROL_CHECK_LIST_COLLABORATOR)
                .antMatchers(HttpMethod.PUT, "/lists/{id}/media/{media-id}")
                    .access(ACCESS_CONTROL_CHECK_LIST_COLLABORATOR)
                .antMatchers(HttpMethod.POST, "/lists/{id}/forks", "/lists/{id}/reports", "/lists/{id}/requests")
                    .access(ACCESS_CONTROL_CHECK_LIST_NOT_OWNER)
                .antMatchers(HttpMethod.GET, "/lists/{id}/**")
                    .access(ACCESS_CONTROL_CHECK_LIST_OWNER_COLLABORATOR_OR_PUBLIC)

                /**
                 * ListComments Controller
                 */
                .antMatchers(HttpMethod.DELETE, "lists-comments/{id}")
                    .access(ACCESS_CONTROL_CHECK_LIST_COMMENT_OWNER)
                .antMatchers(HttpMethod.POST, "lists-comments/{id}/reports")
                    .access(ACCESS_CONTROL_CHECK_LIST_COMMENT_NOT_OWNER)

                /**
                 * ListCommentReport Controller
                 * ListReport Controller
                 * MediaCommentReport Controller
                 */
                .antMatchers("/lists-reports/**", "/lists-comments-reports/**", "/media-comments-reports/**")
                    .hasRole(MOD)

                /**
                 * Media Controller
                 */
                .antMatchers(HttpMethod.POST, "/media/{id}/comments")
                    .authenticated()

                /**
                 * MediaCommentController
                 */
                .antMatchers(HttpMethod.DELETE, "media-comments/{id}")
                    .access(ACCESS_CONTROL_CHECK_MEDIA_COMMENT_OWNER)
                .antMatchers(HttpMethod.POST, "media-comments/{id}/reports")
                    .access(ACCESS_CONTROL_CHECK_MEDIA_COMMENT_NOT_OWNER)


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

                .antMatchers("/users/password-token/**", "/users/verification-token/**")
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

                .antMatchers(HttpMethod.GET, "/users/{username}/lists/**", "/users/{username}/editable-lists", "/users/{username}/favorite-lists/**", "/users/{username}/notifications", "/users/{username}/collab-requests")
                    .access(ACCESS_CONTROL_CHECK_USER)

                .antMatchers("/**")
                    .permitAll()

                // Enable CORS
                .and().cors()
                // Disable CSRF
                .and().csrf().disable()

                // Add JWT Token Filter
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // Add Basic Auth Filter
                .addFilterBefore(basicAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().
                antMatchers("/resources/**"); //Apago SpringSecurity para los assets publicos
    }
}

