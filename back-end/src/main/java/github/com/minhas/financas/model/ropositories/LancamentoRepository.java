package github.com.minhas.financas.model.ropositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import github.com.minhas.financas.model.entity.Lancamento;
import github.com.minhas.financas.model.enuns.StatusLancamento;
import github.com.minhas.financas.model.enuns.TipoLancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	
	@Query(value = "select sum(l.valor) from Lancamento l join l.usuario u where "
			+ " u.id = :idUsuario and l.tipo = :tipo and l.statusLancamento =:status group by u")
	BigDecimal obterSaldoPorTipoLancamentoUsuarioEStatus(@Param("idUsuario") Long idUsuario,@Param("tipo")  TipoLancamento tipo, @Param("status") StatusLancamento status  );
}
