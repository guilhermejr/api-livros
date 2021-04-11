package net.guilhermejr.apilivros.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.guilhermejr.apilivros.service.AutenticacaoService;
import net.guilhermejr.apilivros.service.TokenService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@Autowired
	private TokenService tokenService;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests()
				.antMatchers("/auth").permitAll() 
				.antMatchers(HttpMethod.GET, "/livros/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/estantes/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/autores/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/editoras/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/tipos/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/generos/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/idiomas/**").permitAll() 
				.antMatchers(HttpMethod.GET, "/actuator/**").permitAll() 
				.antMatchers("/swagger-ui/**").permitAll() 
				.antMatchers("/api-docs/**").permitAll() 
				.anyRequest().authenticated()	
			.and()
				.csrf()
					.disable()
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.addFilterBefore(new AutenticacaoViaTokenFilter(this.tokenService), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
}
