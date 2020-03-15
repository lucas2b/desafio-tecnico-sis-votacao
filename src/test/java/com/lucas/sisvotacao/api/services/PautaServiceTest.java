package com.lucas.sisvotacao.api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.lucas.sisvotacao.api.entities.Pauta;
import com.lucas.sisvotacao.api.repositories.PautaRepository;
import com.lucas.sisvotacao.api.services.impl.PautaServiceImpl;

@SpringBootTest
@ActiveProfiles("test")
public class PautaServiceTest {
	
	@MockBean
	private PautaRepository pautaRespository;
	
	@Autowired
	private PautaServiceImpl pautaService;
	
	@BeforeEach
	public void setUp() throws Exception{
		BDDMockito.given(this.pautaRespository.save(Mockito.any(Pauta.class))).willReturn(new Pauta());
		BDDMockito.given(this.pautaRespository.findById(Mockito.anyInt())).willReturn(new Pauta());
		BDDMockito.given(this.pautaRespository.findAll()).willReturn(new ArrayList<Pauta>());
	}
	
	@Test
	public void testarCadastrarNovaPauta() {
		Pauta pauta = this.pautaService.cadastrarNovaPauta(new Pauta());
		assertNotNull(pauta);
	}
	
	@Test
	public void testarAbrirSessaoDeVotacaoEmPauta() {
		Pauta pauta = pautaService.localizarPautaPorId(10);
		pauta.setInicio(new Date());
		pauta.setFim(new Date());
		
		Pauta pautaComSessaoAbertta = this.pautaService.abrirSessaoDeVotacaoEmPauta(pauta);
		assertNotNull(pautaComSessaoAbertta);
	}
	
	@Test
	public void testarLocalizarPautaPorId() {
		Pauta pauta = this.pautaService.localizarPautaPorId(10);
		assertNotNull(pauta);
	}
	
	@Test
	public void testarListarTodasPautas() {
		List<Pauta> listaPauta = this.pautaService.listarTodasPautas();
		
		assertNotNull(listaPauta);
		
	}
	
	

}
