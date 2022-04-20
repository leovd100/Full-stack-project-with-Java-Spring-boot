package github.com.minhas.financas.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import github.com.minhas.financas.model.entity.Lancamento;
import github.com.minhas.financas.model.enuns.StatusLancamento;

public interface LancamentoService {
	
	Lancamento salvar(Lancamento lancamento);
	
	Lancamento atualizar(Lancamento lancamento);
	
	void deletar(Lancamento lancamento);
	
	List<Lancamento> buscar(Lancamento lancamentoFiltro);
	
	void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento);
	
	void validar(Lancamento lancamento);
	
	Optional<Lancamento> buscarPorId(Long id);
	
	BigDecimal obterSaldoPorUsuario(Long id);
}
