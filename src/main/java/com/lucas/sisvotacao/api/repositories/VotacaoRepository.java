package com.lucas.sisvotacao.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.lucas.sisvotacao.api.entities.Votacao;

public interface VotacaoRepository extends JpaRepository<Votacao, Integer>{
	
	@Transactional(readOnly = true)
	Votacao findById(String id);
	
	@Query("SELECT COUNT(u.voto) FROM Votacao u WHERE u.voto = true AND u.pauta.id = :idPauta")
	public int findByVotosSim(@Param("idPauta") int idPauta);
	
	@Query("SELECT COUNT(u.voto) FROM Votacao u WHERE u.voto = false AND u.pauta.id = :idPauta")
	public int findByVotosNao(@Param("idPauta") int idPauta);
	
	
	//Descobre se Associado já votou na Pauta
	@Query("SELECT COUNT(*) FROM Votacao u WHERE u.pauta.id = :idPauta AND u.associado.id = :idAssociado")
	public int findByAssociadoAndPauta(@Param("idPauta") int idPauta, @Param("idAssociado") int idAssociado);

}
