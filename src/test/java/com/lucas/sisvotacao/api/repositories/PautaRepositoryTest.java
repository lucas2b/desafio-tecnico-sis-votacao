package com.lucas.sisvotacao.api.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.lucas.sisvotacao.api.entities.Pauta;

@SpringBootTest
@ActiveProfiles("test")
public class PautaRepositoryTest {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	private static final String DESC_PAUTA = "Pauta Teste 20";
	private static final Date DATA_INICIO = new Date();
	private static final Date DATA_FIM = new Date();
	
	@BeforeEach
	public void setUp() {
		Pauta pauta = new Pauta();
		pauta.setDescricao(DESC_PAUTA);
		this.pautaRepository.save(pauta);
		
		Pauta pauta2 = new Pauta();
		pauta2.setDescricao(DESC_PAUTA);
		this.pautaRepository.save(pauta2);
	}
	
	@AfterEach
    public final void tearDown() { 
		this.pautaRepository.deleteAll();
	}
	
	
	@Test
	public void verificarDescricaoDaPauta() {
		Pauta pauta = this.pautaRepository.findAll().get(0);
		
		assertEquals(DESC_PAUTA, pauta.getDescricao());
	}
	
	@Test
	public void encontrarPautaPorId() {
		Pauta primeiraPautaLista = this.pautaRepository.findAll().get(0);
		int pautaId = primeiraPautaLista.getId();
		
		Pauta pauta = this.pautaRepository.findById(pautaId);
		
		assertEquals(pautaId, pauta.getId());
	}
	
	
	@Test
	public void abrirSessaoPauta() {
		Pauta pauta = this.pautaRepository.findAll().get(0);
		pauta.setInicio(DATA_INICIO);
		
		pauta = this.pautaRepository.save(pauta);
		
		assertEquals(DATA_INICIO, pauta.getInicio());
	}
	

	@Test
	public void listarTodasPauta() {
		List<Pauta> listaPautas = this.pautaRepository.findAll();
		
		assertEquals(2, listaPautas.size());
	}

}
