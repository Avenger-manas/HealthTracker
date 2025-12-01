package HeathTech.HealthTech.Service;
// ✅ यह सही है
import org.springframework.http.HttpMethod;
import HeathTech.HealthTech.Jwtfiler.JwtFilter;
//import io.netty.handler.codec.http.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private MyUserDetails userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

   @Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    
    // Vercel Live URL को यहाँ जोड़ें 
    config.setAllowedOrigins(Arrays.asList(
        "http://localhost:5173",                            // Local Development
        "https://health-tracker-frontend-2n7l.vercel.app"   // Vercel Live Deployment URL
    ));
    
    config.setAllowedHeaders(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}

    // SecurityConfig.java
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource() ))
                .authorizeHttpRequests(auth -> auth
                        // ✅ Preflight OPTIONS Request को तुरंत अनुमति दें
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // अब, Context Path के साथ बाकी नियम लागू करें
                        .requestMatchers("/healthtech/find/**").hasRole("USER")
                        .requestMatchers("/healthtech/ai_factor/**").hasRole("USER")
                        .requestMatchers("/healthtech/getregisteruser/**").hasRole("NGO")
                        .requestMatchers("/healthtech/searchNgo/**").hasRole("USER")
                        .requestMatchers("/healthtech/factor_available/**").hasRole("HOSPITAL")
                        .requestMatchers("/serachHospital/**").hasRole("USER")
//                        .requestMatchers("/user/login/**").hasRole("USER")
//                        .requestMatchers("/Ngo/ngologin/**").hasRole("NGO")
//                        .requestMatchers("/hospital/hospiatllogin/**").hasRole("HOSPITAL")


                        // यदि कोई पाथ ऊपर शामिल नहीं है, तो उसे भी अनुमति दें
                        .anyRequest().permitAll()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.csrf(csrf -> csrf.disable())
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // ✅ FIXED
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/registration_user/**").hasRole("USER")
//                       // .requestMatchers("/user_register/**").permitAll()
//                        .requestMatchers("/ai_factor/**").hasRole("USER")
//                        .requestMatchers("/getregisteruser/**").hasRole("NGO")
//                        .requestMatchers("/searchNgo/**").hasRole("USER")
//                        .requestMatchers("/factor_available/**").hasRole("HOSPITAL")
//                        .anyRequest().permitAll()
//                )
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
