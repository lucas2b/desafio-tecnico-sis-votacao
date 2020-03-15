package com.lucas.sisvotacao.api.services;

import java.util.List;

import com.lucas.sisvotacao.api.entities.Pauta;

public interface PautaService {
	
	Pauta cadastrarNovaPauta(Pauta pauta);
	
	Pauta abrirSessaoDeVotacaoEmPauta(Pauta pauta);
	
	Pauta localizarPautaPorId(int idPauta);
	
	List<Pauta> listarTodasPautas();

}
