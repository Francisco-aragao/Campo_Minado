package projeto.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import projeto.excecao.ExplosaoException;
import projeto.excecao.SairException;
import projeto.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner entrada = new Scanner (System.in);
	
	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		
		executarJogo();
	}
	
	private void executarJogo() {
		try {
			boolean continuarJogo = true;
			
			while (continuarJogo) {
				
				iniciarJogo();
				
				System.out.println("Quer jogar outra partida? (S / n)");
				String resposta = entrada.nextLine();
				
				if ("n".equalsIgnoreCase(resposta)) {
					continuarJogo = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
		} catch (SairException e) {
			System.out.println("Fim do jogo!");
		} finally { 
			entrada.close(); 
		}
	}
	
	private void iniciarJogo() {
		try {
			
			while (tabuleiro.objetivoAlcancado() == false) {
				System.out.println(tabuleiro);
				
				String digitado = lerEntradas("Digite as coordenadas no formato = x,y: \n");
				
				//como recebo as coordenadas na entrada, preciso filtrar os valores e converter pra int
				//trabalho com iterator, acesso x e y
				Iterator<Integer> xy = Arrays.stream(digitado.split(","))
					.map(coordenada -> Integer.parseInt(coordenada.trim())).iterator();
				
				digitado = lerEntradas("Digite 1 para abrir ou 2 para marcar ou desmarcar\n");
				
				if ("1".equals(digitado)) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if ("2".equals(digitado)) {
					tabuleiro.marcar(xy.next(), xy.next());
				}
			}
			
			System.out.println(tabuleiro);
			System.out.println("Você ganhou!!");
		} catch (ExplosaoException e) {
			System.out.println(tabuleiro);
			System.out.println("Você perdeu :( ");
		}
	}
	
	private String lerEntradas(String texto) {
		System.out.println(texto);
		
		String valorDigitado = entrada.nextLine();
		if ("sair".equalsIgnoreCase(valorDigitado)) {
			throw new SairException();
		}
		
		return valorDigitado;
	}
	
}
