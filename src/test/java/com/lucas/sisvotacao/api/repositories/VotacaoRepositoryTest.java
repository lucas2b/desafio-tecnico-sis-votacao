package com.lucas.sisvotacao.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.lucas.sisvotacao.api.entities.Associado;
import com.lucas.sisvotacao.api.entities.Pauta;
import com.lucas.sisvotacao.api.entities.Votacao;

@SpringBootTest
@ActiveProfiles("test")
public class VotacaoRepositoryTest {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private VotacaoRepository votacaoRepository;
	
	private static final String DESCRICAO = "descricaoTeste";
	
	@BeforeEach
	public void setUp() {
		Associado associado1 = new Associado();
		associado1.setNome("Lucas");
		associado1.setCpf("01127022016");
		associado1 = this.associadoRepository.save(associado1);
		
		Associado associado2 = new Associado();
		associado2.setNome("Maiara");
		associado2.setCpf("4321");
		associado2 = this.associadoRepository.save(associado2);
		
		Associado associado3 = new Associado();
		associado3.setNome("Joao");
		associado3.setCpf("543234");
		associado3 = this.associadoRepository.save(associado3);
		
		Pauta pauta1 = new Pauta();
		pauta1.setDescricao(DESCRICAO);
		pauta1 = this.pautaRepository.save(pauta1);
		
		//-------- Votações 
		
		Votacao votacao1 = new Votacao();
		votacao1.setAssociado(associado1);
		votacao1.setPauta(pauta1);
		votacao1.setVoto(true);
		this.votacaoRepository.save(votacao1);
		
		Votacao votacao2 = new Votacao();
		votacao2.setAssociado(associado2);
		votacao2.setPauta(pauta1);
		votacao2.setVoto(false);
		this.votacaoRepository.save(votacao2);
		
		
		Votacao votacao3 = new Votacao();
		votacao3.setAssociado(associado3);
		votacao3.setPauta(pauta1);
		votacao3.setVoto(false);
		this.votacaoRepository.save(votacao3);
		
	}
	
	@AfterEach
    public final void tearDown() { 
		this.associadoRepository.deleteAll();
		this.pautaRepository.deleteAll();
		this.votacaoRepository.deleteAll();
	}

	
	@Test
	public void somarVotosPositivos() {
		
		List<Votacao> listaVotacao = this.votacaoRepository.findAll();
		int idPauta = listaVotacao.get(0).getPauta().getId();
		
		int totalSim = this.votacaoRepository.findByVotosSim(idPauta);
		
		assertEquals(1, totalSim);
	}
	
	@Test
	public void somarVotosNegativos() {
		
		List<Votacao> listaVotacao = this.votacaoRepository.findAll();
		int idPauta = listaVotacao.get(0).getPauta().getId();
		
		int totalNao = this.votacaoRepository.findByVotosNao(idPauta);
		
		assertEquals(2, totalNao);
	}
	
	@Test
	public void verificarQuantasVezesAssociadoVotouEmPauta() {
		
		List<Votacao> listaVotacao = this.votacaoRepository.findAll();
		int idPauta = listaVotacao.get(0).getPauta().getId();
		int idAssociado = listaVotacao.get(0).getAssociado().getId();
		
		int numeroDeVotacoes = this.votacaoRepository.findByAssociadoAndPauta(idPauta, idAssociado);
		
		assertEquals(1, numeroDeVotacoes);
		
	}
	

}
