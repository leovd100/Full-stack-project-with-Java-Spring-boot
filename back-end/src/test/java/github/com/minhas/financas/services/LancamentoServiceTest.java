package github.com.minhas.financas.services;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import github.com.minhas.financas.exception.RegraNegocioException;
import github.com.minhas.financas.model.entity.Lancamento;
import github.com.minhas.financas.model.entity.Usuario;
import github.com.minhas.financas.model.enuns.StatusLancamento;
import github.com.minhas.financas.model.ropositories.LancamentoRepository;
import github.com.minhas.financas.model.ropository.LancamentoRepositoryTest;
import github.com.minhas.financas.service.impl.LancamentoServiceImp;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class LancamentoServiceTest {

	@SpyBean
	LancamentoServiceImp service; //

	@MockBean
	LancamentoRepository repository; // Simular o comportamento do repository

	@Test
	public void deveSalvarUmLancamento() {
		// Cenário
		Lancamento lancamentoASalvar = LancamentoRepositoryTest.gerarLancamento();

		Mockito.doNothing().when(service).validar(lancamentoASalvar);

		Lancamento lancamentoSalvo = LancamentoRepositoryTest.gerarLancamento();
		lancamentoSalvo.setId(1L);
		lancamentoSalvo.setStatusLancamento(StatusLancamento.PENDENTE);
		Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);
		// Execução
		Lancamento lancamento = service.salvar(lancamentoASalvar);

		// Verificação
		Assertions.assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
		Assertions.assertThat(lancamento.getStatusLancamento()).isEqualTo(StatusLancamento.PENDENTE);
	}

	@Test
	public void naoDeveSalvarUmLancamentoQuandoHouverUmErroDeValidacao() {
		// Cenário
		Lancamento lancamentoASalvar = LancamentoRepositoryTest.gerarLancamento();
		Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);

		// Execução
		Assertions.catchThrowableOfType(() -> service.salvar(lancamentoASalvar), RegraNegocioException.class);

		// Verificação
		Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar); // Garantir que ele não salvou

	}

	@Test
	public void deveAtualizarUmLancamento() {
		// Cenário
		Lancamento lancamento = LancamentoRepositoryTest.gerarLancamento();
		lancamento.setId(1L);
		lancamento.setStatusLancamento(StatusLancamento.PENDENTE);

		// Quando ele salvar a entidade lancamento, não lançar erro de validação
		Mockito.doNothing().when(service).validar(lancamento);

		Mockito.when(repository.save(lancamento)).thenReturn(lancamento);
		// Execução
		service.atualizar(lancamento);

		// Verificação
		Mockito.verify(repository, Mockito.times(1)).save(lancamento);
	}

	@Test
	public void deveLancarErroAoTentarAtualizarLancamentoQueAindaNaoFoiSalvo() {
		// Cenário
		Lancamento lancamento = LancamentoRepositoryTest.gerarLancamento();

		// Execução

		// Chamado do método | Resposta esperada
		Assertions.catchThrowableOfType(() -> service.atualizar(lancamento), NullPointerException.class);

		// Verificação
		Mockito.verify(repository, Mockito.never()).save(lancamento); // Garantir que ele não salvou

	}

	@Test
	public void deveDeletarUmLancamento() {
		Lancamento lancamento = LancamentoRepositoryTest.gerarLancamento();
		lancamento.setId(1L);

		// Execução
		service.deletar(lancamento);

		// Verifica se foi chamado o método deletar passando o lancamento
		Mockito.verify(repository).delete(lancamento);
	}

	@Test
	public void deveLancarErroAoTentarDeletarLancamentoQueAindaNaoFoiSalvo() {
		Lancamento lancamento = LancamentoRepositoryTest.gerarLancamento();

		// Execução

		Assertions.catchThrowableOfType(() -> service.deletar(lancamento), NullPointerException.class);

		// Verifica se foi chamado o método deletar passando o lancamento
		Mockito.verify(repository, Mockito.never()).delete(lancamento);
	}

	@Test
	public void deveFiltrarLancamentos() {
		// cenario
		Lancamento lancamento = LancamentoRepositoryTest.gerarLancamento();
		lancamento.setId(1L);

		// Execução
		List<Lancamento> lista = Arrays.asList(lancamento);

		Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);

		List<Lancamento> resultado = service.buscar(lancamento);

		// Verificação
		Assertions.assertThat(resultado).isNotEmpty().hasSize(1).contains(lancamento);
	}

	@Test
	public void deveAtualizarOStatusDeUmLancamento() {
		// cenario
		Lancamento lancamento = LancamentoRepositoryTest.gerarLancamento();
		lancamento.setId(1L);
		lancamento.setStatusLancamento(StatusLancamento.PENDENTE);

		StatusLancamento novoStatus = StatusLancamento.EFETIVADO;
		Mockito.doReturn(lancamento).when(service).atualizar(lancamento);

		// Execução
		service.atualizarStatus(lancamento, novoStatus);

		Assertions.assertThat(lancamento.getStatusLancamento()).isEqualToComparingFieldByField(novoStatus);
		Mockito.verify(service).atualizar(lancamento);

	}

	@Test
	public void deveObterUmLancamentoPorId() {

		Long id = 1L;
		Lancamento lancamento = LancamentoRepositoryTest.gerarLancamento();
		lancamento.setId(id);

		Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamento));

		// Execução

		Optional<Lancamento> resultado = service.buscarPorId(id);

		Assertions.assertThat(resultado.isPresent()).isTrue();

	}

	@Test
	public void deveRetornarVazioQuandoOLancamentoNaoExiste() {
		Long id = 1L;
		Lancamento lancamento = LancamentoRepositoryTest.gerarLancamento();
		lancamento.setId(id);

		Mockito.when(repository.findById(id)).thenReturn(Optional.empty());

		// Execução

		Optional<Lancamento> resultado = service.buscarPorId(id);

		Assertions.assertThat(resultado.isPresent()).isFalse();
	}

	@Test
	public void deveRetornarUmErrosAoValidarLancamento() {
		Lancamento lancamento = new Lancamento();// LancamentoRepositoryTest.gerarLancamento();

		// Execução
		Throwable erro = null;
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));

		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class)
				.hasMessage("Informe uma Descrição válida.");

		lancamento.setDescricao("Salario");

		// mes = null
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));

		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");

		// mes < 1
		lancamento.setMes(-5);
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));

		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");
		// mes > 12
		lancamento.setMes(13);
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));

		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");

		lancamento.setMes(5);
		
		//Ano = null
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano válido.");
		
		//Ano.length != 4
		lancamento.setAno(98);
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano válido.");
		
		lancamento.setAno(2020);
		//Usuario = null
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Usuário.");
		
		Usuario usuario = new Usuario(); // Usuario sem Id
		lancamento.setUsuario(usuario);
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Usuário.");
		
		lancamento.setUsuario(null);
		usuario.setId(1L);
		lancamento.setUsuario(usuario);
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Valor válido.");
		
		lancamento.setValor(BigDecimal.valueOf(-10L));
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Valor válido.");
		
		lancamento.setValor(BigDecimal.TEN);
		erro = Assertions.catchThrowable(() -> service.salvar(lancamento));
		Assertions.assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Tipo de lancamento.");
		
		

	}

}
