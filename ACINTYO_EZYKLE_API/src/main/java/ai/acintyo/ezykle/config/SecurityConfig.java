package ai.acintyo.ezykle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ai.acintyo.ezykle.entities.UserDetailsServiceImp;
import ai.acintyo.ezykle.filter.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private  UserDetailsServiceImp userDetailsServiceImp;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req -> req.requestMatchers("/register/**","/login").permitAll())
				.userDetailsService(userDetailsServiceImp)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
		
		
	}
	
	  @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
	        return configuration.getAuthenticationManager();
	    }

}