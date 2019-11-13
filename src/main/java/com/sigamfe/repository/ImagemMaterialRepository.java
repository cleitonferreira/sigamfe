package com.sigamfe.repository;

import org.springframework.stereotype.Repository;

import com.sigamfe.model.ImagemMaterial;
import com.sigamfe.model.Material;
import com.sigamfe.repository.base.BaseRepository;

@Repository
public interface ImagemMaterialRepository
		extends BaseRepository<Integer, ImagemMaterial>, ImagemMaterialRepositoryCustom {

	ImagemMaterial findByMaterial(Material material);

}
