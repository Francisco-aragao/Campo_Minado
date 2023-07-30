package projeto.modelo;

import java.util.ArrayList;
import java.util.List;

import projeto.excecao.ExplosaoException;

public class Campo {
	
	private final int linha;
	private final int coluna;
	
	private boolean minado;
	private boolean aberto = false;
	private boolean marcado; //marco campo quando sei que tem mina
	
	private List<Campo> vizinhos = new ArrayList<>();
	
	Campo(int linha, int coluna) {
		this.coluna = coluna;
		this.linha = linha;
	}
	
	//vizinho se esta na diagonal, mesma linha ou coluna
	boolean adicionarVizinho (Campo possivelVizinho) { 
		boolean linhaDif = this.linha != possivelVizinho.linha;
		boolean colunaDif = this.coluna != possivelVizinho.coluna;
		boolean estaDiagonal = linhaDif && colunaDif;
		
		int deltaLinha = Math.abs(linha- possivelVizinho.linha);
		int deltaColuna = Math.abs(coluna- possivelVizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if ((deltaGeral == 1 && estaDiagonal == false) || (deltaGeral == 2 && estaDiagonal == true)) {
			vizinhos.add(possivelVizinho);
			return true;
		} else   {
			return false;
		}
	}
	
	void mudarMarcacao () {
		if (!aberto) marcado = !marcado;
	}
	
	boolean vizinhosSeguros () {
		return vizinhos.stream().allMatch(vizinho -> vizinho.minado == false);
	}
	
	boolean abrir () {
		if (aberto || marcado) {
			return false;
		}
		aberto = true;
		if (minado) {
			throw new ExplosaoException();
		}
		
		//abro recursivamente todos os vizinhos que estão seguros
		if (vizinhosSeguros()) {
			vizinhos.forEach(vizinho -> vizinho.abrir());
		}
		
		return true;
	}
	
	public boolean isMarcado() {
		return marcado;
	}
	
	void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	
	public boolean isAberto() {
		return aberto;
	}
	
	public boolean isFechado() {
		return !isAberto();
	}
	
	void minar() {
		minado = true;
	}
	
	public boolean isMinado() {
		return minado;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcancadoCampo() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		
		return desvendado || protegido;
	}
	
	long minasVizinhos() {
		//filtro vizinhos minados conto qtd minas
		return vizinhos.stream().filter(vizinho -> vizinho.minado).count();
	}
	
	void reiniciar() {
		aberto = minado = marcado = false;
	}
	
	public String toString() {
		if (marcado) {
			return "X";
		}
		if (aberto && minado) {
			return "*";
		}
		if (aberto && minasVizinhos() > 0) {
			return Long.toString(minasVizinhos());
		}
		
		if(aberto) {
			return " ";
		}
		
		return "?";
	}
	
}


