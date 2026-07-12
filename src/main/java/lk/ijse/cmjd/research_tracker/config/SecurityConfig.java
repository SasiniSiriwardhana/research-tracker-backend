package lk.ijse.cmjd.research_tracker.config;

import lk.ijse.cmjd.research_tracker.security.JwtAuthenticationEntryPoint;
import lk.ijse.cmjd.research_tracker.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security configuration class.
 * Configures stateless JWT authentication, role-based authorization,
 * BCrypt password encoding, and CORS/CSRF settings.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor injection of security components.
     */
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            UserDetailsService userDetailsService
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configure the security filter chain with endpoint authorization rules.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (not needed for stateless JWT APIs)
                .csrf(AbstractHttpConfigurer::disable)

                // Configure exception handling with custom entry point
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                )

                // Stateless session management (no server-side sessions)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Configure endpoint authorization rules
                .authorizeHttpRequests(auth -> auth

                        // ===== PUBLIC ENDPOINTS =====
                        .requestMatchers("/api/auth/**").permitAll()

                        // ===== USER MANAGEMENT (ADMIN ONLY) =====
                        .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

                        // ===== PROJECT ENDPOINTS =====
                        .requestMatchers(HttpMethod.POST, "/api/projects").hasAnyRole("ADMIN", "PI")
                        .requestMatchers(HttpMethod.DELETE, "/api/projects/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/projects/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/projects/**").hasAnyRole("ADMIN", "PI")
                        .requestMatchers(HttpMethod.PATCH, "/api/projects/**").hasAnyRole("ADMIN", "PI")

                        // ===== MILESTONE ENDPOINTS =====
                        .requestMatchers(HttpMethod.POST, "/api/projects/*/milestones").hasAnyRole("ADMIN", "PI", "MEMBER")
                        .requestMatchers(HttpMethod.PUT, "/api/milestones/**").hasAnyRole("ADMIN", "PI", "MEMBER")
                        .requestMatchers(HttpMethod.DELETE, "/api/milestones/**").hasAnyRole("ADMIN", "PI")
                        .requestMatchers(HttpMethod.GET, "/api/projects/*/milestones").authenticated()

                        // ===== DOCUMENT ENDPOINTS =====
                        .requestMatchers(HttpMethod.POST, "/api/projects/*/documents").hasAnyRole("ADMIN", "PI", "MEMBER")
                        .requestMatchers(HttpMethod.DELETE, "/api/documents/**").hasAnyRole("ADMIN", "PI")
                        .requestMatchers(HttpMethod.GET, "/api/projects/*/documents").authenticated()

                        // ===== ALL OTHER REQUESTS REQUIRE AUTHENTICATION =====
                        .anyRequest().authenticated()
                )

                // Set the authentication provider
                .authenticationProvider(authenticationProvider())

                // Add JWT filter before the standard authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * BCrypt password encoder bean.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication provider using DAO authentication with BCrypt.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Authentication manager bean for programmatic authentication.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
