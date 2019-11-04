import java.util.Random;

public class Individuo {

	private int cromosso[];
	private int aptidao;
	
	//Gera um individuo aleatorio
	public Individuo(int tamanhoCromossomo) {
		aptidao = -1;
		cromosso = new int[tamanhoCromossomo];
		for(int i =0;i<cromosso.length;i++) {
			cromosso[i] = 0;
		}
	}
	
	//Gera um individuo que herdou cromossomos
	public Individuo(int[] heranca) {
		aptidao = -1;
		cromosso = new int[heranca.length];
		for(int i =0;i<cromosso.length;i++) {
			cromosso[i] = heranca[i];
		}
	}
	
	public int getAptidao() {
		return aptidao;
	}
	
	public int[] getCromosso() {
		return cromosso;
	}
	
	public void setAptidao(int aptidao) {
		this.aptidao = aptidao;
	}
	
	public void somaAptidao(int valor) {
		this.aptidao += valor;
	}
	
	public void setCromosso(int[] cromosso) {
		this.cromosso = cromosso;
	}
	
	public void gerarCromossomo(int TAMANHO, int PONTOS) {
		int i;
		
		//Gera os pontos de passagem
		for(i =0; i < (cromosso.length-(PONTOS+1)); i++) {
			cromosso[i] = geraRand(TAMANHO);
			LOG(""+ i);
		}
		
		//Gera as direções para chegar aos pontos
		for(; i < (cromosso.length); i++) {
			cromosso[i] = geraRand(2);
		}
		
	}
	
	public void exibirCromossomos() {
		System.out.print("Cromossomo> |");
		for(int i =0; i < cromosso.length; i++) {
			System.out.print(cromosso[i] + "|");
		}
	}
	
	static int geraRand(int maximo) {

		Random randomico = new Random();
		
		int rand = randomico.nextInt(maximo);
		
		return rand;
	}

	void LOG(String s) {
		System.out.println(s);
	}
	
}
