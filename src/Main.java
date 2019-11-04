import java.beans.DesignMode;
import java.util.Random;

public class Main {
	
	final static int TAMANHO = 10;
	final static int OBSTACULOS = 10;
	
	final static int PONTOS = 3;
	final static int MULTA = TAMANHO * TAMANHO;
	
	
	public static void main(String[] args) 
	{
		int i, j;
		
		Campo campo = new Campo(TAMANHO, OBSTACULOS);
		
		campo.desenharCampo();
		
		//Cada individo terá um caminho em seu cromossomo:
		//C = |P1i|P1j|P2i|P2j|P3i|P3j|d0|d1|d2|d3|
		//Individuo individuo = new Individuo((PONTOS*2)+(PONTOS)+1);
		
		//individuo.gerarCromossomo(TAMANHO, PONTOS);
		int heranca[] = {0,1,8,0,9,2,0,1,0,1};
		Individuo individuo = new Individuo(heranca); 
		
		individuo.exibirCromossomos();
		
	}
	
	
	public void calcularApitidao(Individuo individuo, Campo campo) {
		
		int p, d = 0;
		int pesoCaminho = 0;
		int [] caminho = individuo.getCromosso();
		
		//Sempre inicia da entrada
		int [] origem = campo.entrada;
		int [] destino = new int[2];

		int direcao = (PONTOS*2);
		
		//Inicializa a apitidão do individuo
		individuo.setAptidao(0);
		
		//Para quantos pontos de caminho houverem:
		for(p = 0; p < (PONTOS); p++) {
			
			//Obtem o destino
			destino[0] = caminho[p];
			destino[1] = caminho[p++];
			
			direcao = caminho[d];
			
			//calcula o peso do caminho
			pesoCaminho = pontuaDistancia(campo, origem, destino, direcao);
			
			//soma a apitidão do individuo
			individuo.somaAptidao(pesoCaminho);
			
			origem = destino;
			
			d++;
		}
		
		destino = campo.saida;
		direcao = caminho[d];

		//calcula o peso do caminho
		pesoCaminho = pontuaDistancia(campo, origem, destino, direcao);
		
		//soma a apitidão do individuo
		individuo.somaAptidao(pesoCaminho);
		
	}
	

	private int pontuaDistancia(Campo campo, int[] origem, int[] destino, int direcao) {
		
		int pontos = 0;
		int deslocI = origem[0]-destino[0];
		int deslocJ = origem[1]-destino[1];
		
		
		//Verifica de a diretação é tomada em I ou J
		
		//Soma as casas em J por onde o caminho passa
		
		
		
		return pontos;
	}


	static boolean temObstaculo(char[][] campo, int[] pontoA, int[]pontoB) {
		
		for(int i = 0; i < (pontoB[1]-pontoA[1]);i++ ) {
			if (campo[pontoA[0]][pontoA[1]+i] == 'O')
				System.out.print("Obstalculo");
				return true;
		}
		for(int i = (pontoB[0]-pontoA[0]); i > 0 ;i-- ) {
			if (campo[pontoA[0]+i][pontoA[1]] == 'O')
				System.out.print("Obstalculo");
				return true;
		}
		return false;
	}
	
	static int[] gerarCaminho(int[][] campo) {
		
		int[] caminho = new int[PONTOS*2];
		
		for(int i = 0; i < (PONTOS*2); i++) {
			caminho[i] = geraRand(TAMANHO);
		}
		
		return caminho;
		
	}
	

	
	static int geraRand(int maximo) {

		Random randomico = new Random();
		
		int rand = randomico.nextInt(maximo);
		
		return rand;
	}

}


