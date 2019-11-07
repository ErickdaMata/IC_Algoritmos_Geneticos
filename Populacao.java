import java.util.ArrayList;
import java.util.Random;

public class Populacao extends ArrayList<Individuo>{
	
	private int TAMANHO_POPULACAO;
	private int PONTOS;
	private double TAXA_MUTACAO = 0.01;
	private double TAXA_REPRODUCAO = 0.9;
	private Campo campo;
	private ArrayList <Individuo> novaGeracao = new ArrayList<Individuo>();
	
	public Populacao(int tamanhoPopulacao, int PONTOS, Campo campo) {
		// Cria uma popula��o de N individuos, de maneira aleat�ria
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
		//Adiciona um individou criado por heran�a
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
		for (int i = 0; i < TAMANHO_POPULACAO ; i++) {
			this.get(i).calcularAptidao(campo);
		}
		elitismo();
	}
	
	private void elitismo() {
		
		//Adiciona o melhor individuo a nova popula��o
		novaGeracao.add(this.get(melhorAptidao()));
	}

	private int melhorAptidao() {
		// TODO Auto-generated method stub
		int menor = Integer.MAX_VALUE, melhor = 0;
		for(int i = 0; i < TAMANHO_POPULACAO; i++) {
			if(this.get(i).getAptidao() < menor) {
				menor = this.get(i).getAptidao();
				melhor = i;
			}
		}
		return melhor;
	}

	public void selecaoReproducao() {
		
		int[] participantes = geraQuatroRand(TAMANHO_POPULACAO);
		
		Individuo primeiroGanhador = torneio(this.get(participantes[0]), this.get(participantes[1]));
		Individuo segundoGanhador  = torneio(this.get(participantes[2]), this.get(participantes[3]));
		
		//Verifica se os individuos ir�o reproduzir
		if(geraTaxa() < TAXA_REPRODUCAO) {
			//Realiza a reprodu��o
			reproducao(primeiroGanhador, segundoGanhador);
		} else {
			//Ou repete os pais para pr�xima gera��o
			novaGeracao.add(primeiroGanhador);
			novaGeracao.add(segundoGanhador);
		}
			
	}
	
	private void reproducao(Individuo primeiroGanhador, Individuo segundoGanhador) {
		
		int maximoCrossover = (primeiroGanhador.getCromosso().length)-1;
		
		//Gera dois pontos aleat�rios entre 1 e (tamanho do cromossomo - 1);
		int rand1 = geraRand(maximoCrossover) + 1;
		int rand2 = geraOutroRand(maximoCrossover, rand1) + 1;
		
		int ponto1 = rand1 < rand2? rand1 : rand2;
		int ponto2 = rand1 > rand2? rand1 : rand2;
		
		crossover(primeiroGanhador.getCromosso(), segundoGanhador.getCromosso(), ponto1, ponto2);
		
	}
	
	private void crossover(int[] primeiroCromossoma, int[] segundoCromossoma, int ponto1, int ponto2) {
		int aux;
		for (int i = 0; i <= primeiroCromossoma.length; i++) {
			if(i>=ponto1 && i<=ponto1) {
				aux = primeiroCromossoma[i];
				primeiroCromossoma[i] = segundoCromossoma[i];
				segundoCromossoma[i] = aux;
			}
		}
		
		//Nova gera��o recebe os filhos
		novaGeracao.add(new Individuo(mutacao(primeiroCromossoma), PONTOS));
		novaGeracao.add(new Individuo(mutacao(segundoCromossoma), PONTOS));
		
	}
	
	private int[] mutacao(int[] cromossomo) {
		// Verifica para cada ponto do vetor se haver� muta��o
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

	private void exibirCromossomos(int[] cromossomo) {
		System.out.print("Cromossomo> |");
		for(int i =0; i < cromossomo.length; i++) {
			System.out.print(cromossomo[i] + "|");
		}
		System.out.println("");
	}

	public void novaGeracao() {
		// Metodo publico que realiza todos os procedimentos necess�rio
		// para sele��o e reprodu��o dos individuos da popula��o, resultando
		// em uma nova gera��o.
		
		this.avaliarAptidoes();
		while(novaGeracao.size() < TAMANHO_POPULACAO){
			this.selecaoReproducao();	
		};
		
		this.clear();
		
		this.addAll(novaGeracao);
		
		
	}
	
	public Individuo melhorIndividuo() {
		return this.get(melhorAptidao());
	}
}

