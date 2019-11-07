import java.util.Random;

public class Individuo {

	private int aptidao = 0;
	private int cromosso[];
	private int PONTOS;
	
	
	//Gera um individuo aleatorio
	public Individuo(int TAMANHO_CAMPO, int PONTOS) {
		
		//Cada individo terá um caminho em seu cromossomo:
		//C = |P1i|P1j|P2i|P2j|P3i|P3j|d0|d1|d2|d3|
		int tamanhoCromossomo = (PONTOS*2)+(PONTOS)+1;
		
		this.PONTOS = PONTOS;
		
		cromosso = new int[tamanhoCromossomo];
		
		gerarCromossomo(TAMANHO_CAMPO, PONTOS);
		
	}
	
	//Gera um individuo que herdou cromossomos
	public Individuo(int[] heranca, int PONTOS) {
		this.PONTOS = PONTOS;
		setCromosso(heranca);
	}
	
	public int getPONTOS() {
		return PONTOS;
	}
	
	public int getAptidao() {
		return aptidao;
	}
	
	public int[] getCromosso() {
		return cromosso;
	}
	
	public void esqueceAptidao() {
		this.aptidao = 0;
	}
	
	public void somaAptidao(int valor) {
		this.aptidao += valor;
	}
	
	public void setCromosso(int[] cromosso) {
		this.cromosso = cromosso;
	}
	
	public void gerarCromossomo(int TAMANHO_CAMPO, int PONTOS) {
		int i;
		
		//Gera os pontos de passagem
		for(i =0; i < (cromosso.length-(PONTOS+1)); i++) {
			cromosso[i] = geraRand(TAMANHO_CAMPO);
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
		System.out.println("");
	}
	
	public void calcularAptidao(Campo campo) {

		//Caso a aptidão já tenha sido calculada
		if(aptidao > 0)
			return;
		
		int p, d = 0, direcao;
		int pesoCaminho = 2;
		int [] caminho = getCromosso();
		
		//Sempre inicia da entrada
		int [] origem = campo.entrada;
		int [] destino = new int[2];
		
		d = (PONTOS*2);
		
		//Para quantos pontos de caminho houverem:
		for(p = 0; p < (PONTOS*2); p++) {
			
			//Obtem o destino
			destino[0] = caminho[p];
			destino[1] = caminho[++p];

			direcao = caminho[d];
			
			//calcula o peso do caminho
			pesoCaminho = pontuaDistancia(campo, origem, destino, direcao);
			
			//soma a apitidão do individuo
			somaAptidao(pesoCaminho);
			
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
		somaAptidao(pesoCaminho);
		
		//exibirCromossomos();
		//LOG("Apitidão: " +getAptidao());
	}
	

	private int pontuaDistancia(Campo campo, int[] origem, int[] destino, int direcao) {
		
		
		int pontos = 0;
		int passo;
		int deslocI = destino[0]-origem[0];
		int deslocJ = destino[1]-origem[1];
		int parada;
		
		//Verifica de a diretação é tomada em I ou J
		if(direcao == 1){
			
			//Soma as casas em I , depois em J
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

			//Soma as casas em J , depois em I
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
		return pontos;
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
