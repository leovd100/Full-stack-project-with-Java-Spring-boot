package github.com.minhas.financas.api.resources;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import github.com.minhas.financas.api.dto.UsuarioDTO;
import github.com.minhas.financas.exception.ErroAutenticacao;
import github.com.minhas.financas.exception.RegraNegocioException;
import github.com.minhas.financas.model.entity.Usuario;
import github.com.minhas.financas.service.LancamentoService;
import github.com.minhas.financas.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/usuarios")
public class UsuarioResources {

	
	private final UsuarioService service;
	
	private final LancamentoService lancamentoService;

	
	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		Usuario user = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(user);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
			
		}catch(RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try{
			Usuario autenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(autenticado);
		}catch(ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	
	@GetMapping("{id}/saldo")
	public ResponseEntity obterSaldo(@PathVariable("id") Long id) {
		Optional<Usuario> user = service.obterPorId(id);
		if(!user.isPresent()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		
		BigDecimal saldo = lancamentoService.obterSaldoPorUsuario(id);
		return ResponseEntity.ok(saldo);
	}
	
	
	
}
