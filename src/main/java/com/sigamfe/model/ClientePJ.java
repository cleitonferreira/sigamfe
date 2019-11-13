package com.sigamfe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

/**
 * Classe ClientePJ. Representa um cliente pessoa jurídica.
 */

@Entity
@Table(name = "clientepj")
@Data
@Setter(AccessLevel.NONE)
@EqualsAndHashCode(callSuper = true, exclude = "cnpj")
@ToString(callSuper = true)
@PrimaryKeyJoinColumn(name = "ID")
public class ClientePJ extends Cliente {

	private static final long serialVersionUID = 8965389116825072094L;

	@CNPJ
	@NotBlank
	@Size(max = 16)
	@Column(name = "CNPJ", nullable = false, unique = true, length = 16)
	private String cnpj;

	public void setCnpj(String cnpj) {
		onFieldChange("cnpj", cnpj);
		this.cnpj = cnpj;
	}

}
