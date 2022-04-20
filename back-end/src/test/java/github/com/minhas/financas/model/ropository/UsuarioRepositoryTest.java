package github.com.minhas.financas.model.ropository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import github.com.minhas.financas.model.entity.Usuario;
import github.com.minhas.financas.model.ropositories.UsuarioRepository;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest // Ele cria uma base de dados de teste e ao final de cada teste, ele dá um rowback na base
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UsuarioRepositoryTest {

	@Autowired
	UsuarioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		//Cenário
		Usuario user = criaUsuario("usuario", "usuario@gmail.com", "senha");
		entityManager.persist(user);
		
		//Ação / Execução
		
		boolean result = repository.existsByEmail(user.getEmail());
		
		
		//Verificação
		Assertions.assertThat(result).isTrue();
	}
	
	
	@Test
	public void deveRetornarFalseQuandoNaoHouverUsuarioCadastradoComEmail() {
		//Cenário
		
		
		
		//ação
		boolean result = repository.existsByEmail("usuario@hotmail.com");

		//Verificação
		Assertions.assertThat(result).isFalse();
		
	}
	
	@Test
	public void devePersistirUmUsuarioNaBaseDeDados() {
		//Cenário
		Usuario user = criaUsuario("usuario", "usuario@gmail.com", "senha");
		
		
		//Ação
		Usuario usuarioSalvo = repository.save(user);
		
		//Verificação
		Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
	}
	
	@Test
	public void deveBuscarUmUsuarioPorEmail() {
		//Cenário
			 Usuario user = criaUsuario("usuario", "usuario@gmail.com", "senha");
			 entityManager.persist(user);
		//Ação
				Optional<Usuario> usuarioBuscado = repository.findByEmail(user.getEmail());
				
		//Verificação
				Assertions.assertThat(usuarioBuscado.isPresent()).isTrue();
	}
	
	
	
	@Test
	public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
		//Cenário
			
		//Ação
				Optional<Usuario> usuarioBuscado = repository.findByEmail("usuario@gmail.com");
				
		//Verificação
				Assertions.assertThat(usuarioBuscado.isPresent()).isFalse();
	}
	
	
	
	public Usuario criaUsuario(String nome ,String email, String senha) {
		Usuario user = new Usuario();
		user.setNome(nome);
		user.setEmail(email);
		user.setSenha(senha);
		return user;
	}
	
}
