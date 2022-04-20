package github.com.minhas.financas.model.ropository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import github.com.minhas.financas.model.entity.Lancamento;
import github.com.minhas.financas.model.enuns.StatusLancamento;
import github.com.minhas.financas.model.enuns.TipoLancamento;
import github.com.minhas.financas.model.ropositories.LancamentoRepository;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest // Ele cria uma base de dados de teste e ao final de cada teste, ele dá um rowback na base
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LancamentoRepositoryTest {
	
	
	@Autowired
	LancamentoRepository repository;
	
	@Autowired
	TestEntityManager entityManager;
	
	
	@Test
	public void deveSalvarUmLancamento() {
		
		Lancamento lancamento = gerarLancamento();
		
		lancamento = repository.save(lancamento);
		
		assertThat(lancamento.getId()).isNotNull();
	}
	
	
	@Test
	public void deveDeletarUmLancamento() {
		
		Lancamento lancamento = gerarEPersistirUmLancamento();
		
		lancamento = entityManager.find(Lancamento.class, lancamento.getId());
		
		repository.delete(lancamento);
		
		Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
		
		assertThat(lancamentoInexistente).isNull();
		
	}
	
	@Test
	public void deveAtualizarUmLancamento() {
		Lancamento lancamento = gerarEPersistirUmLancamento();
		
		lancamento.setAno(2018);
		lancamento.setDescricao("Teste atualizar");
		lancamento.setStatusLancamento(StatusLancamento.CANCELADO);
		
		
		repository.save(lancamento);
		
		Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
		
		
		assertThat(lancamentoAtualizado.getAno()).isEqualTo(2018);
		assertThat(lancamentoAtualizado.getDescricao()).isEqualTo(lancamento.getDescricao());
		assertThat(lancamentoAtualizado.getStatusLancamento()).isEqualTo(lancamento.getStatusLancamento());
		
		
	}
	
	@Test
	public void deveBuscarUmLancamentoPorId() {
		Lancamento lancamento = gerarEPersistirUmLancamento();

		Optional<Lancamento> lancamentoBuscado = repository.findById(lancamento.getId());
		
		assertThat(lancamentoBuscado.isPresent()).isTrue();
	}
	
	
	
	
	
	
	public static Lancamento gerarLancamento() {
		Lancamento lancamento = Lancamento.builder()
				.ano(2019)
				.ano(1)
				.descricao("Lancamento qualquer")
				.valor(BigDecimal.valueOf(10))
				.tipo(TipoLancamento.RECEITA)
				.statusLancamento(StatusLancamento.PENDENTE)
				.dataCadastro(LocalDate.now())
				.build();
		return lancamento;
	}
	
	
	
	public Lancamento gerarEPersistirUmLancamento() {
		Lancamento lancamento = gerarLancamento();
		entityManager.persist(lancamento);
		return lancamento;
	}
	
}
