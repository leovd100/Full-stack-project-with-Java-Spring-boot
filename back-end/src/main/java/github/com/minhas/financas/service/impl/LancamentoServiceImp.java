package github.com.minhas.financas.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import github.com.minhas.financas.exception.RegraNegocioException;
import github.com.minhas.financas.model.entity.Lancamento;
import github.com.minhas.financas.model.enuns.StatusLancamento;
import github.com.minhas.financas.model.enuns.TipoLancamento;
import github.com.minhas.financas.model.ropositories.LancamentoRepository;
import github.com.minhas.financas.service.LancamentoService;

@Service
public class LancamentoServiceImp implements LancamentoService{
	
	private LancamentoRepository repository;
		
	public LancamentoServiceImp(LancamentoRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Lancamento salvar(Lancamento lancamento) {	
		validar(lancamento);
		lancamento.setStatusLancamento(StatusLancamento.PENDENTE);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public Lancamento atualizar(Lancamento lancamento) {
		Objects.requireNonNull(lancamento.getId()); // Se for passado um lancamento sem ID, será lancado um null point exception
		validar(lancamento);
		return repository.save(lancamento);
	}

	@Override
	@Transactional
	public void deletar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		Objects.requireNonNull(lancamento.getId());
		repository.delete(lancamento);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
		// TODO Auto-generated method stub
		
		Example example = Example.of(lancamentoFiltro, ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING)); 
		
		
		return repository.findAll(example);
	}

	@Override
	public void atualizarStatus(Lancamento lancamento, StatusLancamento statusLancamento) {
		// TODO Auto-generated method stub
		lancamento.setStatusLancamento(statusLancamento);
		atualizar(lancamento);
	}

	@Override
	public void validar(Lancamento lancamento) {
		// TODO Auto-generated method stub
		
		if(lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
			throw new RegraNegocioException("Informe uma Descrição válida.");
		}
		if(lancamento.getMes() == null || lancamento.getMes() < 1 || lancamento.getMes() > 12) {
			throw new RegraNegocioException("Informe um Mês válido.");
			
		}
		
		if(lancamento.getAno() == null || lancamento.getAno().toString().length() != 4 ) {
			throw new RegraNegocioException("Informe um Ano válido.");
		}
		if(lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
			throw new RegraNegocioException("Informe um Usuário.");
		}
		if(lancamento.getValor() == null || lancamento.getValor().compareTo(BigDecimal.ZERO) < 1) {
			throw new RegraNegocioException("Informe um Valor válido.");
		}
		if(lancamento.getTipo() == null) {
			throw new RegraNegocioException("Informe um Tipo de lancamento.");
		}
		
		
	}

	@Override
	public Optional<Lancamento> buscarPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal obterSaldoPorUsuario(Long id) {
		BigDecimal receitas = repository.obterSaldoPorTipoLancamentoUsuario(id,TipoLancamento.RECEITA);
		BigDecimal despesas = repository.obterSaldoPorTipoLancamentoUsuario(id, TipoLancamento.DESPESA);
		
		if(receitas == null)  {
			receitas = BigDecimal.ZERO;
		}
		
		
		if (despesas == null) { 
			despesas = BigDecimal.ZERO;
		}
	
		
		
		return receitas.subtract(despesas);
	}

}
