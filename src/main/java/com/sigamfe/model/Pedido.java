package com.sigamfe.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sigamfe.model.base.AuditableBaseEntity;
import com.sigamfe.model.converter.LocalDateTimeConverter;
import com.sigamfe.model.enums.EntregaPedido;
import com.sigamfe.model.enums.FormaPagamento;
import com.sigamfe.model.enums.IndicadorSN;
import com.sigamfe.model.enums.converter.EntregaPedidoConverter;
import com.sigamfe.model.enums.converter.FormaPagamentoConverter;
import com.sigamfe.model.enums.converter.IndicadorSNConverter;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Setter(AccessLevel.NONE)
@Entity
@Table(name = "pedido")
@Data
@ToString(callSuper = true,
		exclude = { "cliente", "materiaisPedido", "estados", "pagamentos", "usuarioCriacao", "usuarioAtualizacao" })
@EqualsAndHashCode(callSuper = false, of = "id")
@AttributeOverrides(value = {
		@AttributeOverride(name = "dataCriacao", column = @Column(name = "DATACRIACAO", nullable = false) ),
		@AttributeOverride(name = "dataAtualizacao", column = @Column(name = "DATAATUALIZACAO", nullable = false) ) })
public class Pedido extends AuditableBaseEntity<Integer> {

	private static final long serialVersionUID = 849368848948346967L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "CLIENTE", nullable = false)
	private Cliente cliente;

	@NotNull
	@Convert(converter = FormaPagamentoConverter.class)
	@Column(name = "FORMAPAGAMENTO", nullable = false, length = 2)
	private FormaPagamento formaPagamento;

	@NotNull
	@Convert(converter = IndicadorSNConverter.class)
	@Column(name = "INDICADORPRAZO", nullable = false, length = 1)
	private IndicadorSN prazo;

	@NotNull
	@Digits(fraction = 2, integer = 6)
	@Column(name = "DESCONTO", nullable = false, precision = 8, scale = 2)
	private BigDecimal desconto;

	@NotNull
	@Digits(fraction = 2, integer = 6)
	@Column(name = "TOTALBRUTO", nullable = false, precision = 8, scale = 2)
	private BigDecimal totalBruto;

	@NotNull
	@Convert(converter = EntregaPedidoConverter.class)
	@Column(name = "TURNOENTREGA", nullable = false, length = 1)
	private EntregaPedido turnoEntrega;

	@ManyToOne
	@JoinColumn(name = "ENDERECOENTREGA", nullable = true)
	private Endereco enderecoEntrega;

	@Digits(fraction = 2, integer = 6)
	@Column(name = "TAXAENTREGA", nullable = true, precision = 8, scale = 2)
	private BigDecimal taxaEntrega;

	@NotNull
	@Digits(fraction = 2, integer = 6)
	@Column(name = "TOTAL", nullable = false, precision = 8, scale = 2)
	private BigDecimal total;

	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "DATAENTREGA", nullable = true)
	private LocalDateTime dataEntrega;

	@NotNull
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(name = "DATADEVOLUCAO", nullable = false)
	private LocalDateTime dataDevolucao;

	@Size(max = 500)
	@Column(name = "OBSERVACAO", nullable = true, length = 500)
	private String observacao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "USUARIOCRIACAO", nullable = false)
	private Usuario usuarioCriacao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "USUARIOATUALIZACAO", nullable = false)
	private Usuario usuarioAtualizacao;

	@Version
	@Column(name = "VERSION")
	private Long version;

	@OneToMany(mappedBy = "pedido")
	private List<PedidoMaterial> materiaisPedido;

	@OneToMany(mappedBy = "pedido")
	private List<PedidoEstado> estados;

	@OneToMany(mappedBy = "pedido")
	private List<PedidoPagamento> pagamentos;

	@Override
	public void setId(Integer id) {
		onFieldChange("id", id);
		this.id = id;
	}

	public void setCliente(Cliente cliente) {
		onFieldChange("cliente", cliente);
		this.cliente = cliente;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		onFieldChange("formaPagamento", formaPagamento);
		this.formaPagamento = formaPagamento;
	}

	public void setPrazo(IndicadorSN prazo) {
		onFieldChange("prazo", prazo);
		this.prazo = prazo;
	}

	public void setDesconto(BigDecimal desconto) {
		onFieldChange("desconto", desconto);
		this.desconto = desconto;
	}

	public void setTotalBruto(BigDecimal totalBruto) {
		onFieldChange("totalBruto", totalBruto);
		this.totalBruto = totalBruto;
	}

	public void setTurnoEntrega(EntregaPedido turnoEntrega) {
		onFieldChange("turnoEntrega", turnoEntrega);
		this.turnoEntrega = turnoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		onFieldChange("enderecoEntrega", enderecoEntrega);
		this.enderecoEntrega = enderecoEntrega;
	}

	public void setTaxaEntrega(BigDecimal taxaEntrega) {
		onFieldChange("taxaEntrega", taxaEntrega);
		this.taxaEntrega = taxaEntrega;
	}

	public void setTotal(BigDecimal total) {
		onFieldChange("total", total);
		this.total = total;
	}

	public void setDataEntrega(LocalDateTime dataEntrega) {
		onFieldChange("dataEntrega", dataEntrega);
		this.dataEntrega = dataEntrega;
	}

	public void setDataDevolucao(LocalDateTime dataDevolucao) {
		onFieldChange("dataDevolucao", dataDevolucao);
		this.dataDevolucao = dataDevolucao;
	}

	public void setObservacao(String observacao) {
		onFieldChange("observacao", observacao);
		this.observacao = observacao;
	}

	@Override
	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		onFieldChange("usuarioCriacao", usuarioCriacao);
		this.usuarioCriacao = usuarioCriacao;
	}

	@Override
	public void setUsuarioAtualizacao(Usuario usuarioAtualizacao) {
		onFieldChange("usuarioAtualizacao", usuarioAtualizacao);
		this.usuarioAtualizacao = usuarioAtualizacao;
	}

	@Override
	public void setVersion(Long version) {
		onFieldChange("version", version);
		this.version = version;
	}

	public void setMateriaisPedido(List<PedidoMaterial> materiaisPedido) {
		onFieldChange("materiaisPedido", materiaisPedido);
		this.materiaisPedido = materiaisPedido;
	}

	public void setEstados(List<PedidoEstado> estados) {
		onFieldChange("estados", estados);
		this.estados = estados;
	}

	public void setPagamentos(List<PedidoPagamento> pagamentos) {
		onFieldChange("pagamentos", pagamentos);
		this.pagamentos = pagamentos;
	}

}
