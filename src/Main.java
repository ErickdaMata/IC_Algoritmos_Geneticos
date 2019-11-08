import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	
	final static int TAMANHO_CAMPO = 10;
	final static int OBSTACULOS = 10;
	
	final static int TAMANHO_POPULACAO = 7;
	final static int PONTOS = 3;
	final static int MULTA = TAMANHO_CAMPO * TAMANHO_CAMPO;
	
	static int GERACOES = 3;
	static boolean AVALIAR = true;
	
	public static void main(String[] args) 
	{
		
		
		Scanner sc = new Scanner(System.in);
		
		Campo campo = new Campo(TAMANHO_CAMPO, OBSTACULOS);
		
		campo.desenharCampo();

		/**
		int[] caminho = {0,0,3,4,5,3,1,1,0,1};
		Individuo i = new Individuo(caminho, PONTOS);
		
		i.calcularAptidao(campo);
		i.exibirCromossomos();
		LOG("Aptidao: " + i.getAptidao());
		*/
		
		
		Populacao populacao = new Populacao(TAMANHO_POPULACAO, PONTOS, campo);
		
		int geracao = 0;
		do {
			while(geracao < GERACOES) {
				//LOG("Geracao>" + geracao + " - GERACOES>" + GERACOES);
				populacao.novaGeracao();
				geracao++;
				LOG("Finalizou Gera��o ______________________________");
				populacao.melhorIndividuo().exibirCromossomos();
				LOG("Melhor Aptid�o: " + populacao.melhorIndividuo().getAptidao());
			}
			
			geracao = 0;
			
			LOG("Finalizou Era");
			populacao.melhorIndividuo().exibirCromossomos();
			LOG("Melhor Aptid�o: " + populacao.melhorIndividuo().getAptidao());
			LOG("");
			LOG("(1)Finalizar            (2)N�mero de Gera��es");
			LOG("(3)Taxa de Reprodu��o   (4)Taxa de Muta��o");
			LOG("(Qualquer numero) Continuar buscando");
			
			int resposta = sc.nextInt();
			
			switch (resposta) {
			case 1:
				AVALIAR = false;
				break;
			case 2:
				System.out.print("N�MERO DE GERA��ES: ");
				GERACOES = sc.nextInt();
				break;
			case 3:
				System.out.print("TAXA DE REPRODU��O (max 0.99): ");
				populacao.setTaxaReproducao(sc.nextDouble());
				break;
			case 4:
				System.out.print("TAXA DE MUTA��O (max 0.3): ");
				populacao.setTaxaMutacao(sc.nextDouble());
				break;
			default:
				LOG("Op��o inv�lida, finalizou");
				break;
			}
			
		}while(AVALIAR);
		
		
		
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


