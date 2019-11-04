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
		int heranca[] = {3,7,6,6,5,8,0,0,1,0};
		Individuo individuo = new Individuo(heranca); 
		
		individuo.exibirCromossomos();
		
		calcularAptidao(individuo, campo);
		
	}
	
	
	static public void calcularAptidao(Individuo individuo, Campo campo) {
		
		int p, d = 0, direcao;
		int pesoCaminho = 0;
		int [] caminho = individuo.getCromosso();
		
		//Sempre inicia da entrada
		int [] origem = campo.entrada;
		int [] destino = new int[2];
		
		d = (PONTOS*2);
		
		//Inicializa a apitidão do individuo
		individuo.setAptidao(0);
		
		//Para quantos pontos de caminho houverem:
		for(p = 0; p < (PONTOS*2); p++) {
			
			//Obtem o destino
			destino[0] = caminho[p];
			destino[1] = caminho[++p];

			direcao = caminho[d];
			
			//calcula o peso do caminho
			pesoCaminho = pontuaDistancia(campo, origem, destino, direcao);
			
			//soma a apitidão do individuo
			individuo.somaAptidao(pesoCaminho);
			
			//Nova origem passa ser o destino anterior, em valores para evitar ponteiro
			origem[0] = destino[0];
			origem[1] = destino[1];
			
			d++;
		}
		
		destino = campo.saida;
		direcao = caminho[d];

		//calcula o peso do caminho
		pesoCaminho = pontuaDistancia(campo, origem, destino, direcao);
		
		//soma a apitidão do individuo
		individuo.somaAptidao(pesoCaminho);
		
		LOG("Apitidão: " +individuo.getAptidao());
	}
	

	static private int pontuaDistancia(Campo campo, int[] origem, int[] destino, int direcao) {
		
		//origem[0] = 3;
		//origem[1] = 7;
		
		//destino[0] = 6;
		//destino[1] = 6;
		
		//direcao = 0;
		
		int pontos = 0;
		int passo;
		int deslocI = destino[0]-origem[0];
		int deslocJ = destino[1]-origem[1];
		int parada;
		
		LOG("Deslocamento I: " + deslocI);
		LOG("Deslocamento J: " + deslocJ);
		LOG("Direção: " + direcao);
		
		//Verifica de a diretação é tomada em I ou J
		if(direcao == 1){
			
			if(deslocI > 0){
				for(passo = (origem[0]+1); passo <= destino[0]; passo++)
					pontos += campo.getCampo(passo, origem[1]);
			} else{
				for(passo = origem[0]-1; passo >= destino[0]; passo--)
					pontos += campo.getCampo(passo, origem[1]);
			}
			parada = destino[0];
			if(deslocJ > 0){
				for(passo = (origem[1]+1); passo <= destino[1]; passo++)
					pontos += campo.getCampo(parada, passo);
			} else {
				for(passo = (origem[1]-1); passo >= destino[1]; passo--)
					pontos += campo.getCampo(parada, passo);
			}
			
		} else {

			if(deslocJ > 0){
				for(passo = (origem[1]+1); passo <= destino[1]; passo++)
					pontos += campo.getCampo(origem[0], passo);
			} else {
				for(passo = (origem[1]-1); passo >= destino[1]; passo--){
					pontos += campo.getCampo(origem[0], passo);
					
				}
					
			}
			parada = destino[1];
			if(deslocI > 0){
				for(passo = (origem[0]+1); passo <= destino[0]; passo++)
					pontos += campo.getCampo(passo, parada);
			} else{
				for(passo = origem[0]-1; passo >= destino[0]; passo--)
					pontos += campo.getCampo(passo, parada);
			}
			
		}
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

	static void LOG(String s){
		System.out.println(s);
	}
	
	static void LOG(int[] v){
		System.out.println(" ------- ");
		for(int i=0; i < v.length; i++){
			System.out.println(" >" +v[i] );
		}
	}
}


