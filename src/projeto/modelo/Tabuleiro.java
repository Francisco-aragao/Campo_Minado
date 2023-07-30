package projeto.modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import projeto.excecao.ExplosaoException;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private int minas;
	
	private final List<Campo> campos = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;
		
		gerarCampos();
		associarVizinhos();
		sortearMinas();
	}

	private void gerarCampos() {
		for (int i = 0; i < linhas; i++) {
			for (int j = 0; j < colunas; j++) {
				campos.add(new Campo(i,j));
			}
		}
	}
	
	public void abrir(int linha, int coluna) {
		try {
			campos.stream().filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
			.findFirst() //retorna optional entao trabalho com ele se houver
			.ifPresent(campo -> campo.abrir());
		} catch (ExplosaoException e) {
			campos.forEach(campo -> campo.setAberto(true));
			throw e;
		}
	}
	
	public void marcar(int linha, int coluna) {
		campos.stream().filter(campo -> campo.getLinha() == linha && campo.getColuna() == coluna)
		.findFirst() //retorna optional entao trabalho com ele se houver
		.ifPresent(campo -> campo.mudarMarcacao());
	}
	
	//tento associar todos com todos
	private void associarVizinhos() {
		for (Campo c1: campos) {
			for (Campo c2: campos) {
				c1.adicionarVizinho(c2);
			}
		}
	}
	
	private void sortearMinas() {
		long minasArmadas = 0;
		
		do {
			
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar(); //mino campos aleatorios do tabuleiro
			
			minasArmadas = campos.stream().filter(campo -> campo.isMinado()).count();
		} while (minasArmadas < this.minas);
	}

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(campo -> campo.objetivoAlcancadoCampo());
	}
	
	public void reiniciar() {
		campos.stream().forEach(campo -> campo.reiniciar());
		sortearMinas();
	}
	
	public String toString () {
		StringBuilder sb = new StringBuilder(); //vou usar essa classe pois esse metodo faz muitas concatenacoes
		
		//imprimindo indices colunas
		sb.append(" ");
		for (int i = 0; i < colunas; i++) {
			sb.append(" ");
			sb.append(i);
		}
		sb.append("\n");
		
		int indice = 0;
		for (int i = 0; i < linhas; i++) {
			sb.append(i); //imprimindo indices linhas
			sb.append(" ");
			for (int j = 0; j < colunas; j++) {
				sb.append("");
				sb.append(campos.get(indice));
				sb.append(" ");
				indice++;
			}
			sb.append("\n");
		}
			
		return sb.toString();
	}
	
}
