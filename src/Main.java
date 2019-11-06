import java.util.Random;

public class Main {
	
	final static int TAMANHO_CAMPO = 10;
	final static int OBSTACULOS = 10;
	
	final static int TAMANHO_POPULACAO = 10;
	final static int PONTOS = 3;
	final static int MULTA = TAMANHO_CAMPO * TAMANHO_CAMPO;
	
	public static void main(String[] args) 
	{
		
		Campo campo = new Campo(TAMANHO_CAMPO, OBSTACULOS);
		
		campo.desenharCampo();
		
		Populacao populacao = new Populacao(TAMANHO_POPULACAO, TAMANHO_CAMPO, PONTOS, campo);
		
		populacao.avaliarAptidoes();
		
		populacao.reproducao();
		
		//Individuo individuo = new Individuo(TAMANHO, PONTOS);
		
		//individuo.gerarCromossomo(TAMANHO, PONTOS);
		//int heranca[] = {3,7,6,0,6,0,0,1,1,0};
		//Individuo individuo = new Individuo(heranca);
		
		//individuo.calcularAptidao(campo);
		
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


