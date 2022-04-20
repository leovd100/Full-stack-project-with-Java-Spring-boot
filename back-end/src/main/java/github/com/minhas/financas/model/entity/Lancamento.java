package github.com.minhas.financas.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import github.com.minhas.financas.model.enuns.StatusLancamento;
import github.com.minhas.financas.model.enuns.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lancamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lancamento {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "descricao_lancamento")
	private String descricao;
	
	@Column(name = "mes_lancamento")
	private Integer mes;
	
	@Column(name = "ano_lancamento")
	private Integer ano;
	
	
	//Um usuário pode ter vários lançamentos e um lançamento pode ter um único usuário
	@ManyToOne // muitos lançamentos para um usuário
	@JoinColumn(name = "id_usuario") // nome da coluna de relacionamento
	private Usuario usuario;
	
	@Column(name = "valor")
	private BigDecimal valor;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class) // Converte o LocalDate para o banco de dados
	private LocalDate dataCadastro;
	
	@Column(name = "tipo_lancamento")
	@Enumerated(value = EnumType.STRING)
	private TipoLancamento tipo;
	
	@Column(name = "status_lancamento")
	@Enumerated(value = EnumType.STRING)
	private StatusLancamento statusLancamento;


	
	
	
	
	
	
	
	
	
	
}
