    package com.spring.blogApp_api.config;

    import com.spring.blogApp_api.security.CustomUserDetailService;
    import com.spring.blogApp_api.security.JwtAuthenticationEntryPoint;
    import com.spring.blogApp_api.security.JwtAuthenticationFilter;
    import org.springframework.beans.factory.annotation.Value;
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
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
    import org.springframework.web.cors.CorsConfiguration;
    import org.springframework.web.cors.CorsConfigurationSource;
    import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
    import org.springframework.web.servlet.config.annotation.EnableWebMvc;

    import java.util.List;

    @Configuration
    @EnableWebSecurity
    @EnableMethodSecurity(prePostEnabled = true)
    @EnableWebMvc
    public class SecurityConfig  {

        private final CustomUserDetailService userDetailService;

        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfig(CustomUserDetailService userDetailService,JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,JwtAuthenticationFilter jwtAuthenticationFilter) {
            this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
            this.jwtAuthenticationFilter = jwtAuthenticationFilter;
            this.userDetailService = userDetailService;
        }


        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
             return http
                    .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                     .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enables CORS with default settings
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/swagger-ui/**",
                                    "/swagger-ui",
                                    "/v3/api-docs/**",
                                    "/v3/api-docs",
                                    "/swagger-ui/index.html",
                                    "/api/auth/**",
                                    "/swagger-resources/**",
                                    "/swagger-ui.html",
                                    "/webjars/**",
                                    "/api/auth/register").permitAll()
                            .requestMatchers(HttpMethod.GET).permitAll()
                            .anyRequest().authenticated()
                    )
                     .userDetailsService(userDetailService)
                    .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                     .build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailService); // Ensure DB user is used
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            return authenticationProvider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            return config.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000", "https://nextapp.localhost:44383/"));
            corsConfiguration.setAllowCredentials(true);
            corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
            corsConfiguration.addAllowedMethod("*"); // Allow all methods (GET, POST, PUT, DELETE, OPTIONS)

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", corsConfiguration);
            return source;
        }
    }
