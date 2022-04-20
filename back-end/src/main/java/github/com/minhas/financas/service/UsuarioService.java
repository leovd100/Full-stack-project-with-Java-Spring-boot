package github.com.minhas.financas.service;

import java.util.Optional;

import github.com.minhas.financas.model.entity.Usuario;

public interface UsuarioService {
	
	//Retorn um Usuario caso a autenticação ocorra
	Usuario autenticar(String email, String senha); 
	
	//Retorna um usuário caso ele seja salvo
	Usuario salvarUsuario(Usuario usuario);
	
	void validarEmail(String email);
	
	Optional<Usuario> obterPorId(Long id);
}
