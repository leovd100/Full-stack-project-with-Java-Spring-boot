package github.com.minhas.financas.api.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.com.minhas.financas.api.dto.AtualizaStatusDTO;
import github.com.minhas.financas.api.dto.LancamentoDTO;
import github.com.minhas.financas.exception.RegraNegocioException;
import github.com.minhas.financas.model.entity.Lancamento;
import github.com.minhas.financas.model.entity.Usuario;
import github.com.minhas.financas.model.enuns.StatusLancamento;
import github.com.minhas.financas.model.enuns.TipoLancamento;
import github.com.minhas.financas.service.LancamentoService;
import github.com.minhas.financas.service.UsuarioService;
import github.com.minhas.financas.service.impl.LancamentoServiceImp;
import github.com.minhas.financas.service.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lancamentos")
public class LancamentoResources {


	private final LancamentoServiceImp service;
	private final UsuarioServiceImpl usuario;
		
	
	@GetMapping
	public ResponseEntity buscar(
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value ="mes", required = false) Integer mes,
			@RequestParam(value ="ano", required = false) Integer ano,
			@RequestParam("usuario") Long idUsuario
								) {
		
		
		
		Lancamento lancamentoFiltro = new Lancamento(); 
		lancamentoFiltro.setDescricao(descricao);
		lancamentoFiltro.setMes(mes);
		lancamentoFiltro.setAno(ano);
	
		
		
		Optional<Usuario> user = usuario.obterPorId(idUsuario);
		if (!user.isPresent()) {
			return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o Id informado.");
		}else {
			lancamentoFiltro.setUsuario(user.get());
		}
		
		List<Lancamento> listaLancamentos = service.buscar(lancamentoFiltro);
		return ResponseEntity.ok(listaLancamentos);
	}
 	
	
	@PutMapping("{id}/atualiza-status")
	public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto) {
		return service.buscarPorId(id).map(entity -> {
			StatusLancamento statusSelecionado =  StatusLancamento.valueOf(dto.getStatus());
			if(statusSelecionado == null) {
				return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento. Envie um status válido.");
			}
			try {
				entity.setStatusLancamento(statusSelecionado);
				service.atualizar(entity);
				return ResponseEntity.ok(entity);
			}catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));

	}
	
	
	
	@PostMapping()
	public ResponseEntity salvar(@RequestBody LancamentoDTO dto){
		try {
			Lancamento entidade = converter(dto);
			entidade = service.salvar(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		}catch(RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	
	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) { 
		return service.buscarPorId(id).map( entity -> {
			try{
				Lancamento lancamento = converter(dto);
				lancamento.setId(entity.getId());
				lancamento = service.atualizar(lancamento);
				return ResponseEntity.ok(lancamento);
			}catch(RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return service.buscarPorId(id).map(enity -> {
			service.deletar(enity);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Lancamento não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}
	
	
	
	
	
	
	
	
	private Lancamento converter(LancamentoDTO dto) {
		 Lancamento lancamento = new Lancamento();
		 lancamento.setId(dto.getId());
		 lancamento.setDescricao(dto.getDescricao());
		 lancamento.setMes(dto.getMes());
		 lancamento.setAno(dto.getAno());
		 lancamento.setValor(dto.getValor());
		 
		 Usuario user = usuario.obterPorId(dto.getUsuario())
				 .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o Id informado."));
		 
		 
		 lancamento.setUsuario(user);
		 System.out.println(dto.getTipo());
		 if(dto.getTipo() != null) {
			 lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
		 }
		 if(dto.getStatus() != null) {
			 lancamento.setStatusLancamento(StatusLancamento.valueOf(dto.getStatus()));
		 }
		 return lancamento;
	}
	
	
}
