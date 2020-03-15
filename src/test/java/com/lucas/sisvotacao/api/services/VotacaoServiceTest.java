package com.lucas.sisvotacao.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


import com.lucas.sisvotacao.api.entities.Votacao;
import com.lucas.sisvotacao.api.repositories.AssociadoRepository;
import com.lucas.sisvotacao.api.repositories.PautaRepository;
import com.lucas.sisvotacao.api.repositories.VotacaoRepository;
import com.lucas.sisvotacao.api.services.impl.VotacaoServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
public class VotacaoServiceTest {
	
	@MockBean
	private VotacaoRepository votacaoRepository;
	
	@MockBean
	private PautaRepository pautaRepository;
	
	@MockBean 
	private AssociadoRepository associadoRepository;
	
	
	@Autowired
	private VotacaoServiceImpl votacaoService;
	
	@BeforeEach
	public void setUp() {
		
		BDDMockito.given(this.votacaoRepository.save(Mockito.any(Votacao.class))).willReturn(new Votacao());

		BDDMockito.given(this.votacaoRepository.findByAssociadoAndPauta(Mockito.anyInt(), Mockito.anyInt())).willReturn(Integer.valueOf(2));
		
		BDDMockito.given(this.votacaoRepository.findByVotosSim(Mockito.anyInt())).willReturn(Integer.valueOf(10));
		
		BDDMockito.given(this.votacaoRepository.findByVotosNao(Mockito.anyInt())).willReturn(Integer.valueOf(20));
	}
	
	@Test
	public void testReceberVotoDeAssociadoEmPauta() {
		Votacao votacao = this.votacaoService.receberVotoDeAssociadoEmPauta(new Votacao());
		
		assertNotNull(votacao);
	}
	
	@Test
	public void testContabilizarVotosPositivosPorPauta() {
		
		int votosSim = this.votacaoService.contabilizarVotosPositivosPorPauta(43);
		
		assertEquals(10, votosSim);
	}
	
	@Test
	public void testContabilizarVotosNegativosPorPauta() {
		
		int votosNao = this.votacaoService.contabilizarVotosNegativosPorPauta(33);
		
		assertEquals(20, votosNao);
	}
	
	@Test
	public void testVerificarNumeroDeVezesAssociadoVotouEmPauta() {
		
		int numeroDeVotosDeAssociado = this.votacaoService.verificarNumeroDeVezesAssociadoVotouEmPauta(100, 100);
		
		assertEquals(2, numeroDeVotosDeAssociado);
	}
	
	

}
