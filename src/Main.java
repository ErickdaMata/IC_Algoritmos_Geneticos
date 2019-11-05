import java.util.Random;

public class Main {
	
	final static int TAMANHO = 10;
	final static int OBSTACULOS = 10;
	
	final static int PONTOS = 3;
	final static int MULTA = TAMANHO * TAMANHO;
	
	
	public static void main(String[] args) 
	{
		
		Campo campo = new Campo(TAMANHO, OBSTACULOS);
		
		campo.desenharCampo();
		
		//Cada individo terá um caminho em seu cromossomo:
		//C = |P1i|P1j|P2i|P2j|P3i|P3j|d0|d1|d2|d3|
		Individuo individuo = new Individuo((PONTOS*2)+(PONTOS)+1, TAMANHO, PONTOS);
		
		//individuo.gerarCromossomo(TAMANHO, PONTOS);
		//int heranca[] = {3,7,6,0,6,0,0,1,1,0};
		//Individuo individuo = new Individuo(heranca); 
		
		//individuo.exibirCromossomos();
		
		individuo.calcularAptidao(campo);
		
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


