package com.lucas.sisvotacao.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.sisvotacao.api.entities.Associado;

@Transactional(readOnly = true)
public interface AssociadoRepository extends JpaRepository<Associado, Integer>{
	
	@Transactional(readOnly = true)
	Associado findById(int id);
	
	@Transactional(readOnly = true)
	Associado findByCpf(String cpf);

}
