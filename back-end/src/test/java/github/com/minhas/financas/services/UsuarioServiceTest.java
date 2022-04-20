package github.com.minhas.financas.services;


import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import github.com.minhas.financas.exception.ErroAutenticacao;
import github.com.minhas.financas.exception.RegraNegocioException;
import github.com.minhas.financas.model.entity.Usuario;
import github.com.minhas.financas.model.ropositories.UsuarioRepository;
import github.com.minhas.financas.service.UsuarioService;
import github.com.minhas.financas.service.impl.UsuarioServiceImpl;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	@SpyBean
	UsuarioServiceImpl usuarioService;
	
	@MockBean
	UsuarioRepository usuarioRepository;
	

	
	
	@Test(expected = Test.None.class)
	public void deveSalvarUsuario() {
		//cenário
		Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
		Usuario user = criaUsuario("nome", "usuario@gmail.com", "senha", 1L);
		
		Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(user);
		//ação
		
		Usuario salvarUsuario = usuarioService.salvarUsuario(new Usuario());
		
		//verificação
		
		Assertions.assertThat(salvarUsuario).isNotNull();
		Assertions.assertThat(salvarUsuario.getId()).isEqualTo(1);
		Assertions.assertThat(salvarUsuario.getNome()).isEqualTo(user.getNome());
		Assertions.assertThat(salvarUsuario.getEmail()).isEqualTo(user.getEmail());
		Assertions.assertThat(salvarUsuario.getSenha()).isEqualTo(user.getSenha());
	}
	
	
	@Test(expected = RegraNegocioException.class)
	public void naoDeveSalvarUsuarioComEmailJaCadastrado() {
		//Cenario
		Usuario user = criaUsuario("nome", "usuario@gmail.com", "senha");
		Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(user.getEmail());
		
		//Ação
		usuarioService.salvarUsuario(user);
		
		//Verificação
		Mockito.verify(usuarioRepository,Mockito.never()).save(user);
		
	}
	
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		String email = "usuario@gmail.com";
		String senha = "senha";
		
		
		Usuario usuario = criaUsuario(email, senha, 1L);
	
		Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//Ação
		Usuario result = usuarioService.autenticar(email, senha);
		
		//verificação
		
		
		Assertions.assertThat(result).isNotNull();
	}
	
	
	@Test
	public void deveLancarErroQuandoNaoEncontrarUmUsuarioCadastradoComEmailInformado() {
		//Cenário
		String email = "usuario@gmail.com";
		String senha = "senha";
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		
		//Ação
		Throwable exception =  Assertions.catchThrowable(() -> usuarioService.autenticar(email, senha));
		
		//Verificar
		Assertions.assertThat(exception)
			.isInstanceOf(ErroAutenticacao.class)
			.hasMessage("Usuário não encontrado para o e-mail informado");
	}
	
	
	
	@Test
	public void deveLancarErroQuandoSenhaEstiverIncorreta() {
		//Cenário
		String email = "usuario@gmail.com";
		String senha = "senha";
		Usuario usuario = criaUsuario(email, senha, 1L);
		
		Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//Ação
		
		Throwable exception =  Assertions.catchThrowable( () -> usuarioService.autenticar(email, "123"));
		
		//Verificar
		Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida.");
	}
	
	
	
	
	//Valida exeções
	@Test(expected = Test.None.class) // Espera que não lance nenhuma exception
	public void deveValidarEmail() {
		//Cenário
			Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
		//ação
		usuarioService.validarEmail("usuario@gmail.com");
	}
	
	@Test(expected = RegraNegocioException.class) // Espera que lance a exception criada
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		
		//Cenário
		Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//Ação
		usuarioService.validarEmail("usuario@hotmail.com");
		
	}
	
	public Usuario criaUsuario(String nome, String email, String senha) {
		Usuario user = new Usuario();
		user.setNome(nome);
		user.setEmail(email);
		user.setSenha(senha);
		return user;
	}
	
	
	public Usuario criaUsuario(String email, String senha, Long id) {
		Usuario user = new Usuario();
		user.setEmail(email);
		user.setSenha(senha);
		user.setId(id);
		return user;
	}
	
	public Usuario criaUsuario(String nome, String email, String senha, Long id) {
		Usuario user = new Usuario();
		user.setNome(nome);
		user.setEmail(email);
		user.setSenha(senha);
		user.setId(id);
		return user;
	}
	
}
