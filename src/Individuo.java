import java.util.Random;

public class Individuo {

	private int cromosso[];
	private int aptidao;
	private int PONTOS;
	
	//Gera um individuo aleatorio
	public Individuo(int tamanhoCromossomo ,int TAMANHO, int PONTOS) {
		aptidao = -1;
		this.PONTOS = PONTOS;
		cromosso = new int[tamanhoCromossomo];
		
		gerarCromossomo(TAMANHO, PONTOS);
		
	}
	
	//Gera um individuo que herdou cromossomos
	public Individuo(int[] heranca, int PONTOS) {
		aptidao = -1;
		this.PONTOS = PONTOS;
		setCromosso(heranca);
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
		}
		
		//Gera as direções para chegar aos pontos
		for(; i < (cromosso.length); i++) {
			cromosso[i] = geraRand(2);
		}
		
		exibirCromossomos();
	}
	
	public void exibirCromossomos() {
		System.out.print("Cromossomo> |");
		for(int i =0; i < cromosso.length; i++) {
			System.out.print(cromosso[i] + "|");
		}
		System.out.println("");
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

	public void calcularAptidao(Campo campo) {
		
		int p, d = 0, direcao;
		int pesoCaminho = 1;
		int [] caminho = getCromosso();
		
		//Sempre inicia da entrada
		int [] origem = campo.entrada;
		int [] destino = new int[2];
		
		d = (PONTOS*2);
		
		//Inicializa a apitidão do individuo
		setAptidao(0);
		
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
		
		LOG("Apitidão: " +getAptidao());
	}
	

	private int pontuaDistancia(Campo campo, int[] origem, int[] destino, int direcao) {
		
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
	
	
	
}
