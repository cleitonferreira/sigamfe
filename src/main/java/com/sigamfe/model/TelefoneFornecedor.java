package com.sigamfe.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sigamfe.model.TelefoneFornecedor.TelefoneFornecedorPK;
import com.sigamfe.model.base.BaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter(AccessLevel.NONE)
@Entity
@Table(name = "telefonefornecedor")
@Data
@ToString(callSuper = false, exclude = "fornecedor")
@EqualsAndHashCode(callSuper = false, of = "id")
public class TelefoneFornecedor extends BaseEntity<TelefoneFornecedorPK> {

	private static final long serialVersionUID = 4519541791492468821L;

	@Data
	@Embeddable
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString(callSuper = false)
	@EqualsAndHashCode(callSuper = false, of = { "idFornecedor", "telefone" })
	public static class TelefoneFornecedorPK implements Serializable {

		private static final long serialVersionUID = 1793948713323650141L;

		@NotNull
		@Digits(fraction = 0, integer = 10)
		@Column(name = "IDFORNECEDOR", nullable = false, precision = 10, scale = 0)
		private Integer idFornecedor;

		@NotNull
		@Digits(fraction = 0, integer = 11)
		@Column(name = "TELEFONE", nullable = false, precision = 11, scale = 0)
		private Long telefone;
	}

	@EmbeddedId
	private TelefoneFornecedorPK id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "IDFORNECEDOR", nullable = false, insertable = false, updatable = false)
	private Fornecedor fornecedor;

	@Size(max = 200)
	@Column(name = "OBSERVACOES", length = 200, nullable = true)
	private String observacoes;

	@Version
	@Column(name = "VERSION")
	private Long version;

	/**
	 * Atribui o fornecedor a esta entidade
	 *
	 * @param fornecedor
	 *            o novo fornecedor.
	 */
	public void setFornecedor(Fornecedor fornecedor) {
		onFieldChange("fornecedor", fornecedor);
		this.fornecedor = fornecedor;
		if (this.id == null) {
			this.id = new TelefoneFornecedorPK();
		}
		this.getId().setIdFornecedor(fornecedor == null ? null : fornecedor.getId());
	}

	@Override
	public void setId(TelefoneFornecedorPK id) {
		onFieldChange("id", id);
		this.id = id;
	}

	public void setObservacoes(String observacoes) {
		onFieldChange("observacoes", observacoes);
		this.observacoes = observacoes;
	}

	@Override
	public void setVersion(Long version) {
		onFieldChange("version", version);
		this.version = version;
	}

}
