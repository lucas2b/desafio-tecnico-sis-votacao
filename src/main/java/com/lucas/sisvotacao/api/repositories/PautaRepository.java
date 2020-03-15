package com.lucas.sisvotacao.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.sisvotacao.api.entities.Pauta;

@Transactional(readOnly = true)
public interface PautaRepository extends JpaRepository<Pauta, Integer>{
		
	@Transactional(readOnly = true)
	Pauta findById(int id);
	
	@Transactional(readOnly = true)
	Pauta findByDescricao(String descricao);


}
