package net.guilhermejr.apilivros.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import net.guilhermejr.apilivros.exception.Exception401;
import net.guilhermejr.apilivros.exception.ExceptionPadrao;
import net.guilhermejr.apilivros.model.dto.TokenDTO;
import net.guilhermejr.apilivros.model.entity.RefreshToken;
import net.guilhermejr.apilivros.model.entity.Usuario;
import net.guilhermejr.apilivros.model.repository.RefreshTokenRepository;
import net.guilhermejr.apilivros.model.repository.UsuarioRepository;

@Slf4j
@Service
public class TokenService {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.refreshExpiration}")
	private String refreshExpiration;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	public TokenDTO gerarToken(Usuario usuario) {
		
		Date agora = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(agora);
		calendar.add(Calendar.MILLISECOND, Integer.parseInt(this.expiration));
		Date expiracao = calendar.getTime();
		
		String token =  Jwts.builder()
				.setIssuer("API de Livros")
				.setSubject(usuario.getNome())
				.setId(usuario.getId().toString())
				.setIssuedAt(agora)
				.setExpiration(expiracao)
				.claim("email", usuario.getEmail())
				.claim("criado", usuario.getCriado().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.claim("ultimoAcesso", usuario.getUltimoAcesso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
				.claim("admin", true)
				.signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret)))
				.compact();
		
		String refreshToken = this.stringRandomica();

		RefreshToken rt = RefreshToken.builder().token(refreshToken)
				.expiracao(LocalDateTime.now().plusSeconds(Long.parseLong(refreshExpiration))).build();

		this.refreshTokenRepository.save(rt);

		return TokenDTO.builder().access_token(token).refresh_token(refreshToken).token_type("Bearer").build();
		
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
	
	public TokenDTO refresh(String token, String refreshToken) {

		Optional<RefreshToken> rto = this.refreshTokenRepository.findByToken(refreshToken);
		if (rto.isPresent()) {
			RefreshToken rt = rto.get();
			if (rt.getExpiracao().isBefore(LocalDateTime.now())) {
				throw new Exception401("Token expirado.");
			} else {

				try {

					Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.secret))).build()
							.parseClaimsJws(token);
					
					return TokenDTO.builder().access_token(token).refresh_token(refreshToken).token_type("Bearer").build();

				} catch (ExpiredJwtException e) {

					String email = e.getClaims().get("email").toString();
					Optional<Usuario> usuario = this.usuarioRepository.findByEmailAndAtivo(email, Boolean.TRUE);
					
					if (usuario.isPresent()) {
						
						this.refreshTokenRepository.delete(rt);
						
						return this.gerarToken(usuario.get());
					} else {
						throw new ExceptionPadrao("Token inválido");
					}

				} catch (MalformedJwtException e) {
					throw new ExceptionPadrao("Token inválido");
				} catch (SignatureException e) {
					throw new ExceptionPadrao("Token inválido");
				} catch (Exception e) {
					throw new ExceptionPadrao("Token inválido");
				}

			}
		} else {
			throw new ExceptionPadrao("Token inválido");
		}

	}

	public String stringRandomica() {

		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 32;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;

	}

}
