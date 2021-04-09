package net.guilhermejr.apilivros.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import net.guilhermejr.apilivros.model.entity.Usuario;

@Slf4j
@Service
public class TokenService {
	
	@Value("${livros.jwt.secret}")
	private String secret;
	
	public String gerarToken(Authentication authenticate) {
		
		Usuario logado = (Usuario) authenticate.getPrincipal();
		
		Date agora = new Date();
		
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(agora);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    Date fimDia = calendar.getTime();
		
		return Jwts.builder()
				.setIssuer("API de Livros")
				.setSubject(logado.getNome())
				.setId(logado.getId().toString())
				.setIssuedAt(agora)
				.setExpiration(fimDia)
				.claim("email", logado.getEmail())
				.claim("criado", logado.getCriado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.claim("ultimoAcesso", logado.getUltimoAcesso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.claim("admin", true)
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)))
				.compact();
		
	}

	public boolean ehValido(String token) {
		
		try {
			
			Jwts.parserBuilder()
				    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)))         
				    .build()                    
				    .parseClaimsJws(token); 
			
			return true;	
			
		} catch (Exception e) {
			
			log.error("Erro ao validar token: "+ token);
			return false;
			
		}
		
	}

	public Usuario getUsuario(String token) {
		
		Claims body = Jwts.parserBuilder()
			    .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)))         
			    .build()                    
			    .parseClaimsJws(token)
			    .getBody();
		Usuario usuario = Usuario.builder()
							.id(Long.parseLong(body.getId()))
							.nome(body.getSubject())
							.email(body.get("email").toString())
							.criado(LocalDateTime.parse(body.get("criado").toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
							.ultimoAcesso(LocalDateTime.parse(body.get("ultimoAcesso").toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
						.build();
		return usuario;
		
	}

}
