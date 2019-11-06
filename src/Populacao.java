import java.util.ArrayList;
import java.util.Random;

public class Populacao extends ArrayList<Individuo>{
	
	private int TAMANHO_POPULACAO;
	private double TAXA_MUTACAO = 0.1;
	private double TAXA_REPRODUCAO = 0.9;
	private Campo campo;
	
	
	public Populacao(int tamanhoPopulacao, int TAMANHO, int PONTOS, Campo campo) {
		// Cria uma população de N individuos, de maneira aleatória
		setTamanhoPopulacao(tamanhoPopulacao);
		setCampo(campo);
		for(int i = 0; i < TAMANHO_POPULACAO; i++)
			add(new Individuo(TAMANHO, PONTOS));
	}
	
	public void setTaxaMutacao(double taxaMutacao) {
		TAXA_MUTACAO = taxaMutacao;
	}
	
	public void setTaxaReproducao(double taxaReproducao) {
		TAXA_REPRODUCAO = taxaReproducao;
	}
	
	private void setTamanhoPopulacao(int tamanhoPopulacao) {
		this.TAMANHO_POPULACAO = tamanhoPopulacao > 0? tamanhoPopulacao : 0;
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
		for (int i = 0; i < TAMANHO_POPULACAO ; i++) {
			this.get(i).calcularAptidao(campo);
		}
	}
	
	public void reproducao() {
		
		Individuo primeiroGanhador = novoTorneio();
		Individuo segundoGanhador  = novoTorneio();
		
		if(geraTaxa() < TAXA_REPRODUCAO)
			reproduza(primeiroGanhador, segundoGanhador);
	}
	
	private void reproduza(Individuo primeiroGanhador, Individuo segundoGanhador) {
		//AQUI
		
	}

	private Individuo novoTorneio() {
		int primeiroIndividuo = geraRand(TAMANHO_POPULACAO);
		int segundoIndividuo = geraOutroRand(TAMANHO_POPULACAO, primeiroIndividuo);
		
		System.out.print("Primeiro Individuo: "+ primeiroIndividuo);
		System.out.println(" X Segundo Individuo: "+ segundoIndividuo);
		
		return duelo(this.get(primeiroIndividuo), this.get(segundoIndividuo));
	}
	
	private Individuo duelo(Individuo primeiroIndividuo, Individuo segundoIndividuo) {
		
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
	
	private int geraOutroRand(int maximo, int randAnterior) {

		Random randomico = new Random();
		int rand;
		
		do {
			rand = randomico.nextInt(maximo);	
		} while(rand == randAnterior);
		
		return rand;
	}
	
}

