package com.myfood.security;
/**
 * @author David Maza
 *
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.myfood.security.jwt.AuthEntryPointJwt;
import com.myfood.security.jwt.JwtAuthorizationFilter;
import com.myfood.security.service.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	
	@Bean
	JwtAuthorizationFilter authorizationJwtTokenFilter() {
		return new JwtAuthorizationFilter();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// CORS Configuration Bean
    @Bean
   CorsConfigurationSource corsConfigurationSource() {
    	
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");      // Allow all origins or Arrays.asList("http://localhost:4200","http://localhost:3000")
        configuration.addAllowedMethod("*");      // Allow all methods or List.of("GET", "POST", "PUT", "DELETE")
        configuration.addAllowedHeader("*");      // Allow all headers
        configuration.setAllowCredentials(true);  // Allow sending of authentication cookies
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    


    // Security Filter Chain
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http ,  AuthenticationManager authenticationManager) throws Exception {

      return  http.cors(cors -> corsConfigurationSource())                            
        .csrf(csrf -> csrf.disable())                                                            
        .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests( auth -> auth
        	
        	.requestMatchers(AUTH_WHITELIST)
        	.permitAll()
        	.anyRequest().authenticated()
        )
        	.authenticationProvider(authenticationProvider())
            .addFilterBefore(authorizationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class)
            .build();
	}
	

	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.inMemoryAuthentication()
	                .withUser("superadmin")
	                .password(passwordEncoder().encode("123456"))
	                .authorities("ADMIN");
	    }
	
	private final String[] AUTH_WHITELIST = {
			"/auth/**",
	        "/swagger-resources",
	        "/swagger-resources/**",
	        "/configuration/ui",
	        "/configuration/security",
	        "/swagger-ui.html",
	        "/v3/api-docs/**",
	        "/swagger-ui/**",
	};
	
 	
}
