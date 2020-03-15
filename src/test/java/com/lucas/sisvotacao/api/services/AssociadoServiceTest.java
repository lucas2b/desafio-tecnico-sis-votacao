package com.lucas.sisvotacao.api.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.lucas.sisvotacao.api.entities.Associado;
import com.lucas.sisvotacao.api.repositories.AssociadoRepository;
import com.lucas.sisvotacao.api.services.impl.AssociadoServiceImpl;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class AssociadoServiceTest {
	
	@Autowired
	private AssociadoServiceImpl associadoService;
	
	@MockBean
	private AssociadoRepository associadoRepository;
	
	@BeforeEach
	public void setUp() {
		BDDMockito.given(this.associadoRepository.findById(Mockito.anyInt())).willReturn(new Associado());
		BDDMockito.given(this.associadoRepository.findAll()).willReturn(new ArrayList<Associado>());
	}
	
	@Test
	public void testLocalizarAssociadoPorId() {
		Associado associado = this.associadoService.localizarAssociadoPorId(10);
		
		assertNotNull(associado);
	}
	
	@Test
	public void testListarTodosAssociados() {
		List<Associado> listaAssociados = this.associadoService.listarTodosAssociados();
		
		assertNotNull(listaAssociados);
	}
	
	

}
