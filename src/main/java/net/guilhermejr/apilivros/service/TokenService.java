package net.guilhermejr.apilivros.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.guilhermejr.apilivros.model.entity.Usuario;

@Service
public class TokenService {
	
	@Value("${livros.jwt.expiration}")
	private String expiration;
	
	@Value("${livros.jwt.secret}")
	private String secret;
	
	public String gerarToken(Authentication authenticate) {
		
		Usuario logado = (Usuario) authenticate.getPrincipal();
		
		Date hoje = new Date();
		Date expiracao = new Date(hoje.getTime() + Long.parseLong(this.expiration));
		
		return Jwts.builder()
				.setIssuer("API de Livros")
				.setSubject(logado.getId().toString())
				.setId(logado.getId().toString())
				.setIssuedAt(hoje)
				.setExpiration(expiracao)
				.claim("nome", logado.getNome())
				.claim("email", logado.getEmail())
				.claim("criado", logado.getCriado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.claim("ultimoAcesso", logado.getUltimoAcesso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.claim("admin", true)
				.signWith(SignatureAlgorithm.HS256, this.secret)
				.compact();
	}

	public boolean ehValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;	
		} catch (Exception e) {
			return false;
		}
		
	}

	public Usuario getUsuario(String token) {
		
		Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		Usuario usuario = Usuario.builder()
							.id(Long.parseLong(body.getId()))
							.nome(body.get("nome").toString())
							.email(body.get("email").toString())
							.criado(LocalDateTime.parse(body.get("criado").toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
							.ultimoAcesso(LocalDateTime.parse(body.get("ultimoAcesso").toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
						.build();
		return usuario;
	}

}