package com.lucas.sisvotacao.api.services;

import com.lucas.sisvotacao.api.entities.Votacao;

public interface VotacaoService {
	
	Votacao receberVotoDeAssociadoEmPauta(Votacao votacao);
	
	int contabilizarVotosPositivosPorPauta(int idPauta);
	
	int contabilizarVotosNegativosPorPauta(int idPauta);
	
	int verificarNumeroDeVezesAssociadoVotouEmPauta(int idPauta, int idAssociado);

}
