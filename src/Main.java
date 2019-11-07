
public class Main {
	
	final static int TAMANHO_CAMPO = 10;
	final static int OBSTACULOS = 10;
	
	final static int TAMANHO_POPULACAO = 40;
	final static int PONTOS = 3;
	final static int MULTA = TAMANHO_CAMPO * TAMANHO_CAMPO;
	
	static int GERACOES = 20;
	
	public static void main(String[] args) 
	{
		
		Campo campo = new Campo(TAMANHO_CAMPO, OBSTACULOS);
		
		campo.desenharCampo();
		
		Populacao populacao = new Populacao(TAMANHO_POPULACAO, PONTOS, campo);
		
		int geracao = 0;
		
		while(geracao < GERACOES) {
			//LOG("Geracao>" + geracao + " - GERACOES>" + GERACOES);
			populacao.novaGeracao();
			geracao++;
		}
		
		
		populacao.melhorIndividuo().exibirCromossomos();
		LOG("Melhor Aptidão: " + populacao.melhorIndividuo().getAptidao());
		
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


