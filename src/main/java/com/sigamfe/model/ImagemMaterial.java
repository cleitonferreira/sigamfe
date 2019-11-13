package com.sigamfe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.sigamfe.model.base.BaseEntity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

@Setter(AccessLevel.NONE)
@Entity
@Table(name = "materialimagem")
@Data
@ToString(callSuper = false, exclude = { "imagem", "material" })
@EqualsAndHashCode(callSuper = false, of = "id")
public class ImagemMaterial extends BaseEntity<Integer> {

	private static final long serialVersionUID = -2843596916310581046L;

	@Id
	@Column(name = "IDMATERIAL")
	private Integer id;

	@NotNull
	@OneToOne
	@JoinColumn(name = "IDMATERIAL", nullable = false, insertable = false, updatable = false)
	private Material material;

	@NotEmpty
	@Column(name = "IMAGEM", nullable = false, columnDefinition = "mediumblob")
	private Byte[] imagem;

	@Version
	@Column(name = "VERSION")
	private Long version;

	/**
	 * Atribui o material a esta entidade.
	 *
	 * @param material
	 *            O novo material.
	 */
	public void setMaterial(Material material) {
		onFieldChange("material", material);
		this.material = material;
		this.id = material.getId();
	}

	@Override
	public void setId(Integer id) {
		onFieldChange("id", id);
		this.id = id;
	}

	public void setImagem(Byte[] imagem) {
		onFieldChange("imagem", imagem);
		this.imagem = imagem;
	}

	@Override
	public void setVersion(Long version) {
		onFieldChange("version", version);
		this.version = version;
	}

}
