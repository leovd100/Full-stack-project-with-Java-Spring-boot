package github.com.minhas.financas.model.ropositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import github.com.minhas.financas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
	
	Optional<Usuario> findByEmail(String email);
	
	boolean existsByEmail(String email);
	
	
	
	
	
}
