package net.guilhermejr.apilivros.inicializacao;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import net.guilhermejr.apilivros.model.entity.Estante;
import net.guilhermejr.apilivros.model.entity.Perfil;
import net.guilhermejr.apilivros.model.entity.Tipo;
import net.guilhermejr.apilivros.model.entity.Usuario;
import net.guilhermejr.apilivros.model.repository.EstanteRepository;
import net.guilhermejr.apilivros.model.repository.PerfilRepository;
import net.guilhermejr.apilivros.model.repository.TipoRepository;
import net.guilhermejr.apilivros.model.repository.UsuarioRepository;

@Component
public class CarregaBancoDeDados {

	@Autowired
	private PerfilRepository perfilRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TipoRepository tipoRepository;

	@Autowired
	private EstanteRepository estanteRepository;

	@Bean
	public void carregar() {

		// --- Se não existir Perfil cadastrado realiza o cadastro ---
		if (this.perfilRepository.count() == 0) {

			Perfil perfil = Perfil.builder().descricao("ROLE_USUARIO").build();

			this.perfilRepository.save(perfil);

		}

		// --- Se não existir Usuario cadastrado realiza o cadastro ---
		if (this.usuarioRepository.count() == 0) {

			// --- Encode da senha ---
			// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			Usuario usuario = Usuario.builder().nome("Guilherme Jr.").email("falecom@guilhermejr.net").senha("123456").build();
			//Usuario usuario = Usuario.builder().nome("Guilherme Jr.").email("falecom@guilhermejr.net").senha(encoder.encode("123456")).build();

			Perfil perfil1 = new Perfil();
			perfil1.setId(1L);

			usuario.setPerfis(Arrays.asList(perfil1));

			this.usuarioRepository.save(usuario);
		}

		// --- Se não existir Tipo cadastrado realiza o cadastro ---
		if (this.tipoRepository.count() == 0) {

			Tipo tipo1 = Tipo.builder().descricao("Físico").build();
			Tipo tipo2 = Tipo.builder().descricao("E-book").build();
			Tipo tipo3 = Tipo.builder().descricao("PDF").build();

			this.tipoRepository.saveAll(Arrays.asList(tipo1, tipo2, tipo3));

		}

		// --- Se não existir Estante cadastrada realiza o cadastro ---
		if (this.estanteRepository.count() == 0) {

			Estante estante1 = Estante.builder().descricao("Biblioteca").build();
			Estante estante2 = Estante.builder().descricao("Desejados").build();
			Estante estante3 = Estante.builder().descricao("Troca").build();
			Estante estante4 = Estante.builder().descricao("Doação").build();
			Estante estante5 = Estante.builder().descricao("Venda").build();

			this.estanteRepository.saveAll(Arrays.asList(estante1, estante2, estante3, estante4, estante5));

		}

	}

}
