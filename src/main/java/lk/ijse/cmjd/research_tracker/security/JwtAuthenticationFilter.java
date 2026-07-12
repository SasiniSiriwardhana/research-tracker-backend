package lk.ijse.cmjd.research_tracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter that intercepts every HTTP request.
 * Extracts and validates the JWT token from the Authorization header,
 * then sets the authentication context if valid.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor injection of JWT utility and UserDetailsService.
     */
    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Extract Authorization header
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // If no Bearer token is present, continue the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the token (remove "Bearer " prefix)
        jwt = authHeader.substring(7);

        try {
            // Extract username from the token
            username = jwtUtil.extractUsername(jwt);

            // If username is valid and no authentication exists in the context
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user details from the database
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Validate the token
                if (jwtUtil.isTokenValid(jwt, userDetails)) {
                    // Create authentication token and set it in the security context
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // If token is invalid or expired, log and continue without authentication
            logger.error("JWT Authentication failed: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
