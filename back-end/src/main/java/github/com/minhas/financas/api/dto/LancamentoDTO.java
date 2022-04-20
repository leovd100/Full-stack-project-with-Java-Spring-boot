package github.com.minhas.financas.api.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;


@Data
@Builder
public class LancamentoDTO {

	private Long id;
	private String descricao;
	private Integer mes;
	private Integer ano;
	private BigDecimal valor;
	private Long usuario;
	private String tipo;
	private String status;
	
}
