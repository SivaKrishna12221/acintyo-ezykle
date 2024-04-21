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
	private UserDetailsServiceImp userDetailsServiceImp;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(req -> req.requestMatchers("/ezykle-user/**","/ezycle/forgotPassword/verifyMail/{email}","/ezycle/forgotPassword/verifyOtp/{otp}/{email}","/ezycle/forgotPassword/changePassword/{email}").permitAll()
						.requestMatchers("/ezykle-user/get-all-users").hasAuthority("ADMIN")
						.requestMatchers("/ezykle-user/get-user/{id}").hasAnyAuthority("USER")
						.requestMatchers("/ezykle-user/update-user/{id}").hasAnyAuthority("USER")
						.requestMatchers("/ezykle-user/reset-pwd/").hasAnyAuthority("USER")
						.requestMatchers("/ezykle-service/book-appointment").hasAnyAuthority("USER")
						.requestMatchers("/ezykle-service/get-all-appointments").hasAnyAuthority("USER")
						.requestMatchers("/ezykle-service/get-appointment/{id}").hasAnyAuthority("USER")
						.requestMatchers("/ezykle-admin/add-service-center").hasAnyAuthority("ADMIN")
						.requestMatchers("/ezykle-admin/***").hasAnyAuthority("ADMIN")
						.requestMatchers("/ezykle-admin/get-service-center/{id}").hasAnyAuthority("ADMIN")
						.requestMatchers("/ezykle-admin/get-service/{id}").hasAnyAuthority("ADMIN")
						.requestMatchers("/ezykle-admin/update-service-center/{id}").hasAnyAuthority("ADMIN")
						.requestMatchers("/ezykle-admin/update-service/{id}").hasAnyAuthority("ADMIN"))
				
				.userDetailsService(userDetailsServiceImp)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class).build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

}