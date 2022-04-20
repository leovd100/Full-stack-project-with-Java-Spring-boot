package github.com.minhas.financas.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import github.com.minhas.financas.exception.ErroAutenticacao;
import github.com.minhas.financas.exception.RegraNegocioException;
import github.com.minhas.financas.model.entity.Usuario;
import github.com.minhas.financas.model.ropositories.UsuarioRepository;
import github.com.minhas.financas.service.UsuarioService;
import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	// a camada de serviço faz acesso a camada de modelo com o repository
	private UsuarioRepository repository;
	
	public UsuarioServiceImpl(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		Optional<Usuario> user = repository.findByEmail(email);
		
		
		if (!user.isPresent()) {
			throw new ErroAutenticacao("Usuário não encontrado para o e-mail informado"); 
		}
		
		if (!user.get().getSenha().equals(senha)){
			throw new ErroAutenticacao("Senha inválida.");
		}
		
		
		return user.get();
	}

	@Override
	@Transactional // Salva usuário e commita
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		validarEmail(usuario.getEmail());
		return repository.save(usuario);
	}

	@Override
	public void validarEmail(String email) {
		boolean exists = repository.existsByEmail(email);
		if(exists) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este e-mail.");
		}
	}

	@Override
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
		
		
	}
	
	

}
