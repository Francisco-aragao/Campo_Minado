package projeto.modelo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projeto.excecao.ExplosaoException;

public class CampoTeste {
	
	private Campo campoTeste;
	
	@BeforeEach
	void iniciarCampoTeste() {
		campoTeste = new Campo(3,3);
	}
	
	@Test
	void testeVizinhoRealDistancia1() {
		Campo vizinho= new Campo(3,2);
		
		boolean result = campoTeste.adicionarVizinho(vizinho);
		
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoRealDistancia2() {
		Campo vizinho= new Campo(2,2);
		
		boolean result = campoTeste.adicionarVizinho(vizinho);
		
		assertTrue(result);
	}
	
	@Test
	void testeVizinhoFalsoDistancia3() {
		Campo vizinho= new Campo(1,2);
		
		boolean result = campoTeste.adicionarVizinho(vizinho);
		
		assertFalse(result);
	}
	
	@Test
	void testeMudarMarcacaoFalse() {
		assertFalse(campoTeste.isMarcado());
	}
	
	@Test
	void testeMudarMarcacaoTrue() {
		campoTeste.mudarMarcacao();
		assertTrue(campoTeste.isMarcado());
	}
	
	@Test
	void testeAbrirCampoNaoMinadoNaoMarcado() {
		
		assertTrue(campoTeste.abrir());
	}
	
	@Test
	void testeAbrirCampoNaoMinadoMarcado() {
		campoTeste.mudarMarcacao();
		assertFalse(campoTeste.abrir());
	}
	
	@Test
	void testeAbrirCampoMinadoMarcado() {
		campoTeste.mudarMarcacao();
		campoTeste.minar();
		assertFalse(campoTeste.abrir());
	}
	
	@Test
	void testeAbrirCampoMinadoNaoMarcado() {
		campoTeste.minar();
		assertThrows(ExplosaoException.class, () -> {
			campoTeste.abrir();
		});
	}
	
	@Test
	void testeAbrirComVizinho() {
		Campo vizinho1 = new Campo(2,2);
		Campo vizinho2 = new Campo(1,2);

		campoTeste.adicionarVizinho(vizinho1);
		vizinho1.adicionarVizinho(vizinho2);
		
		campoTeste.abrir();
		
		assertTrue(vizinho2.isAberto() && vizinho1.isAberto());
	}
	
	@Test
	void testeAbrirComVizinhoMina() {
		Campo vizinho1 = new Campo(2,2);
		Campo vizinho2 = new Campo(1,2);
		vizinho2.minar();

		campoTeste.adicionarVizinho(vizinho1);
		vizinho1.adicionarVizinho(vizinho2);
		
		campoTeste.abrir();
		
		assertTrue(vizinho2.isFechado() && vizinho1.isAberto());
	}
	
}
