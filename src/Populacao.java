import java.util.ArrayList;
import java.util.Random;

public class Populacao extends ArrayList<Individuo>{
	
	private boolean LOG = true;
	
	private int GERACAO = 0;
	private int TAMANHO_POPULACAO;
	private int MELHOR_INDIVIDUO;
	private int PONTOS;
	
	private double TAXA_MUTACAO = 0.01;
	private double TAXA_REPRODUCAO = 0.9;
	
	
	private Campo campo;
	private ArrayList <Individuo> novaGeracao = new ArrayList<Individuo>();
	
	
	
	public Populacao(int tamanhoPopulacao, int PONTOS, Campo campo) {
		// Cria uma população de N individuos, de maneira aleatória
		setTamanhoPopulacao(tamanhoPopulacao);
		setCampo(campo);
		this.PONTOS = PONTOS;
		for(int i = 0; i < TAMANHO_POPULACAO; i++)
			add(new Individuo(campo.getTAMANHO(), PONTOS));
	}
	
	public void setTaxaMutacao(double taxaMutacao) {
		TAXA_MUTACAO = taxaMutacao < 0.3? taxaMutacao : 0.3;
	}
	
	public void setTaxaReproducao(double taxaReproducao) {
		TAXA_REPRODUCAO = taxaReproducao < 0.99? taxaReproducao : 0.99;
	}
	
	private void setTamanhoPopulacao(int tamanhoPopulacao) {
		this.TAMANHO_POPULACAO = tamanhoPopulacao > 5? tamanhoPopulacao : 5;
	}
	
	private void setCampo(Campo campo) {
		this.campo = campo;
	}
	
	public boolean add(Individuo individuo) {
		return super.add(individuo);
	}
	
	public boolean add(int[] heranca, int PONTOS) {
		//Adiciona um individou criado por herança
		Individuo individuo = new Individuo(heranca, PONTOS);		
		return super.add(individuo);
	}

	@Override
	public Individuo get(int index) {
		// TODO Auto-generated method stub
		return super.get(index);
	}
	
	@Override
	public Individuo remove(int index) {
		// TODO Auto-generated method stub
		return super.remove(index);
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}
	
	public void avaliarAptidoes() {
		LOG("AVALIANDO APTIDÕES");
		for (int i = 0; i < TAMANHO_POPULACAO ; i++) {
			if(LOG) {
				System.out.print("["+i+"]>");
				this.get(i).exibirCromossomos();
			}
			
			this.get(i).calcularAptidao(campo);
			
			LOG("Aptidao: "+this.get(i).getAptidao());
		}
		elitismo();
	}
	
	private void elitismo() {
		
		//Busca o melhor individuo
		MELHOR_INDIVIDUO = this.melhorAptidao();
		//Adiciona o melhor individuo a nova população
		novaGeracao.add(this.get(MELHOR_INDIVIDUO));
		LOG("ELITISMO: Melhor Individuo:");
		novaGeracao.get(0).exibirCromossomos();
	}

	private int melhorAptidao() {
		// TODO Auto-generated method stub
		int menor = Integer.MAX_VALUE, melhor = 0;
		for(int i = 0; i < TAMANHO_POPULACAO; i++) {
			if(LOG)
				System.out.print("["+i+"]>");
			if(LOG)
				this.get(i).exibirCromossomos();
			LOG("A:" + this.get(i).getAptidao());
			if(this.get(i).getAptidao() < menor) {
				menor = this.get(i).getAptidao();
				melhor = i;
			}
		}
		LOG(" >>> Melhor : " + melhor);
		return melhor;
	}

	public void selecaoReproducao() {
		
		LOG("SELEÇÃO");
		
		int[] participantes = geraQuatroRand(TAMANHO_POPULACAO);
		LOG("Torneio: ("+participantes[0]+"X"+participantes[1]+") ("+participantes[2]+"X"+participantes[3]+")");
		Individuo primeiroGanhador = torneio(this.get(participantes[0]), this.get(participantes[1]));
		Individuo segundoGanhador  = torneio(this.get(participantes[2]), this.get(participantes[3]));
		
		if(LOG) {
			LOG("Primeiro ganhador");
			primeiroGanhador.exibirCromossomos();
			LOG("Segundo ganhador");
			segundoGanhador.exibirCromossomos();
		}
		
		//Verifica se os individuos irão reproduzir
		if(geraTaxa() < TAXA_REPRODUCAO) {
			LOG("HOUVE REPRODUÇÃO");
			//Realiza a reprodução
			reproducao(primeiroGanhador, segundoGanhador);
		} else {
			LOG("NÃO HOUVE REPRODUÇÃO");
			//Ou repete os pais para próxima geração
			novaGeracao.add(primeiroGanhador);
			novaGeracao.add(segundoGanhador);
		}
			
	}
	
	private void reproducao(Individuo primeiroGanhador, Individuo segundoGanhador) {
		
		int maximoCrossover = (primeiroGanhador.getCromosso().length)-1;
		
		//Gera dois pontos aleatórios entre 1 e (tamanho do cromossomo - 1);
		int rand1 = geraRand(maximoCrossover) + 1;
		int rand2 = geraOutroRand(maximoCrossover, rand1) + 1;
		
		int ponto1 = rand1 < rand2? rand1 : rand2;
		int ponto2 = rand1 > rand2? rand1 : rand2;
		
		LOG("CROSSOVER - p[" + ponto1 + ","+ponto2+"]");
		
		crossover(primeiroGanhador.getCromosso(), segundoGanhador.getCromosso(), ponto1, ponto2);
		
	}
	
	private void crossover(int[] primeiroCromossoma, int[] segundoCromossoma, int ponto1, int ponto2) {
		int aux;
		for (int i = 0; i <= primeiroCromossoma.length; i++) {
			if(i>=ponto1 && i<=ponto2) {
				aux = primeiroCromossoma[i];
				primeiroCromossoma[i] = segundoCromossoma[i];
				segundoCromossoma[i] = aux;
			}
		}
		
		Individuo primeiroFilho = new Individuo(mutacao(primeiroCromossoma), PONTOS);  
		Individuo segundoFilho = new Individuo(mutacao(segundoCromossoma), PONTOS);
		
		primeiroFilho.calcularAptidao(this.campo);
		segundoFilho.calcularAptidao(this.campo);
		
		if(LOG) {
			LOG("Primeiro filho");
			primeiroFilho.exibirCromossomos();
			LOG("Segundo filho");
			segundoFilho.exibirCromossomos();
		}
		
		//Nova geração recebe os filhos
		novaGeracao.add(primeiroFilho);
		novaGeracao.add(segundoFilho);
		
	}
	
	private int[] mutacao(int[] cromossomo) {
		// Verifica para cada ponto do vetor se haverá mutação
		for (int i = 0; i < cromossomo.length; i++) {
			if (geraTaxa() < TAXA_MUTACAO) {
				if(i < (PONTOS*2)) {
					cromossomo[i] = geraRand(campo.getTAMANHO());
				} else {
					cromossomo[i] = geraRand(2);
				}
			}
		}
		return cromossomo;
	}

	private Individuo torneio(Individuo primeiroIndividuo, Individuo segundoIndividuo) {
		return primeiroIndividuo.getAptidao() < segundoIndividuo.getAptidao() ? primeiroIndividuo : segundoIndividuo;
	}
	
	private double geraTaxa() {

		Random randomico = new Random();
		
		double rand = randomico.nextDouble();
		
		return rand;
	}

	private int geraRand(int maximo) {

		Random randomico = new Random();
		
		int rand = randomico.nextInt(maximo);
		
		return rand;
	}
	
	private int geraOutroRand(int maximo, int anterior) {

		Random randomico = new Random();
		
		int rand;
		
		do {
			rand = randomico.nextInt(maximo);
		}while (rand == anterior);
		
		return rand;
	}
	
	private int[] geraQuatroRand(int maximo) {

		int i,j;
		
		Random randomico = new Random();
		int[] sorteados = {-1,-1,-1,-1};
		int numero;
		
		for(i = 0; i < 4; i++) {
			numero = randomico.nextInt(maximo);
			for(j = 0; j < i; j++) {
				if(numero == sorteados[j]) {
					i--;
					break;
				}		
			}
			if(j==i)
				sorteados[i] = numero;
		}
		
		return sorteados;
	}


	public void novaGeracao() {
		// Metodo publico que realiza todos os procedimentos necessário
		// para seleção e reprodução dos individuos da população, resultando
		// em uma nova geração.

		this.avaliarAptidoes();

		while(novaGeracao.size() < TAMANHO_POPULACAO){
			this.selecaoReproducao();	
		};

		substituirIndividuos(novaGeracao);
		
		//Busca o melhor individuo
		MELHOR_INDIVIDUO = this.melhorAptidao();
	}
	
	private void substituirIndividuos(ArrayList<Individuo> novaGeracao) {
		// TODO Auto-generated method stub
		this.clear();

		for(int i = 0; i < TAMANHO_POPULACAO; i++)
			this.add(new Individuo(novaGeracao.get(i).getCromosso(),PONTOS,novaGeracao.get(i).getAptidao()));
		
		novaGeracao.clear();
		
		LOG("GERAÇÃO: " + (++GERACAO));
	}

	public Individuo melhorIndividuo() {
		return this.get(MELHOR_INDIVIDUO);
	}
	
	private void LOG(String s){
		if (LOG)
			System.out.println(s);
	}
	
}

