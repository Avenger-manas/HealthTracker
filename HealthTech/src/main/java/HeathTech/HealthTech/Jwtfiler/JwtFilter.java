package HeathTech.HealthTech.Jwtfiler;

import HeathTech.HealthTech.JwtToken.JwtToken;
import HeathTech.HealthTech.Service.MyUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private MyUserDetails userDetailsService;
//
//    @Autowired
//    private JwtToken jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain)
//            throws ServletException, IOException {
//
//        String authorizationHeader = request.getHeader("Authorization");
//        String username = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            username = jwtUtil.extractUsername(jwt);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            if (jwtUtil.validateToken(jwt)) {
//
//                // EXTRACT ROLE FROM TOKEN
//                String role = jwtUtil.extractRole(jwt);
//
//                // MAKE SPRING AUTHORITIES
//                SimpleGrantedAuthority authority =
//                        new SimpleGrantedAuthority("ROLE_" + role);
//
//                UsernamePasswordAuthenticationToken auth =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                List.of(authority)       // <-- FIXED
//                        );
//
//                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}

//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private MyUserDetails userDetailsService;
//
//    @Autowired
//    private JwtToken jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain)
//            throws ServletException, IOException {
//
//        String authorizationHeader = request.getHeader("Authorization");
//        String username = null;
//        String jwt = null;
//
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            jwt = authorizationHeader.substring(7);
//            username = jwtUtil.extractUsername(jwt);
//        }
//
//        // only run if user not authenticated
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//            if (jwtUtil.validateTokenSimple(jwt)) {
//
//                // Extract roles (list of strings)
//                String role = jwtUtil.extractRole(jwt); // e.g. "ROLE_USER"
//
//                // Do NOT prepend "ROLE_" again (already in JWT)
//                SimpleGrantedAuthority authority =
//                        new SimpleGrantedAuthority(role);
//
//                UsernamePasswordAuthenticationToken auth =
//                        new UsernamePasswordAuthenticationToken(
//                                userDetails,
//                                null,
//                                List.of(authority)
//                        );
//
//                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetails userDetailsService;

    @Autowired
    private JwtToken jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        // Read Authorization header
        String authHeader = request.getHeader("Authorization");

        // If header missing or does NOT start with Bearer -> skip
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Extract token safely
        String jwt = authHeader.substring(7);
        String username = null;

        try {
            username = jwtUtil.extractUsername(jwt);
        } catch (Exception e) {
            System.out.println("Invalid/Expired JWT: " + e.getMessage());
            chain.doFilter(request, response);
            return;
        }

        // If username extracted AND user not authenticated already
        if (username != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateTokenSimple(jwt)) {

                // Extract role stored inside token
                String role = jwtUtil.extractRole(jwt); // Already contains ROLE_USER, ROLE_ADMIN etc.

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        chain.doFilter(request, response);
    }
}

