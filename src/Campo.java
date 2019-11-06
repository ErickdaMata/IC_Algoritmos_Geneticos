import java.util.Random;

public class Campo {

	int campo[][];
	int entrada[];
	int saida[];
	
	private final int TAMANHO;
	private final int OBSTACULOS;
	private final int MULTA;
	
	public Campo(int TAMANHO, int OBSTACULOS) {
		// TODO Auto-generated constructor stub
		this.TAMANHO = TAMANHO;
		this.OBSTACULOS = OBSTACULOS;
		this.MULTA = (TAMANHO*TAMANHO)+1;
		
		campo = novoCampo();
		
		this.colocarObstaculos(OBSTACULOS);
		
		this.definirEntrada();
		
		this.definirSaida();
		
	}
	
	public int getTAMANHO() {
		return TAMANHO;
	}
	
	public int getOBSTACULOS() {
		return OBSTACULOS;
	}
	
	public int getCampo(int i, int j) {
		return campo[i][j];
	}
	
	private int[][] novoCampo() {
		int[][] campo  = new int[TAMANHO][TAMANHO];
		
		for (int i = 0; i<TAMANHO; i++) {
			for (int j = 0; j<TAMANHO; j++) {
				campo[i][j] = 1;
			}
		}
		
		return campo;
	}
	
	private void definirEntrada() {
		
		entrada = new int[2];
		
		entrada[0] = geraRand(TAMANHO);
		entrada[1] = geraRand(TAMANHO);
		
		//entrada[0] = 0;
		//entrada[1] = 7;
		
		campo[entrada[0]][entrada[1]] = 1;
	}
	
	private void definirSaida() {
		
		saida = new int[2];
		
		saida[0] = geraRand(TAMANHO);
		saida[1] = geraRand(TAMANHO);
		//int[] saida   = {6,0};
		
		//saida[0] = 6;
		//saida[1] = 0;
		
		campo[saida[0]][saida[1]] = 0;
	}
	
	private void colocarObstaculos(int OBSTACULOS) {
		
		/**
		campo[0][2] = MULTA;
		campo[0][6] = MULTA;
		campo[3][1] = MULTA;
		campo[4][5] = MULTA;
		campo[4][8] = MULTA;
		campo[4][9] = MULTA;
		campo[7][1] = MULTA;
		campo[7][4] = MULTA;
		campo[9][1] = MULTA;
		campo[9][8] = MULTA;
		*/
		
		
		int rndI, rndJ;
		
		//Coloca os obstáculos
		for (int i = 0 ; i<OBSTACULOS;) {
			
			rndI = geraRand(TAMANHO);
			rndJ = geraRand(TAMANHO);
			
			if(campo[rndI][rndJ] != MULTA) {
				campo[rndI][rndJ] = MULTA;
				i++;
			}	
		}
		
	}
	
	public void desenharCampo() {

		//System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
		//System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
		//System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
		
		
		for (int i = 0; i<TAMANHO; i++) {
			System.out.print("|");
			for (int j = 0; j<TAMANHO; j++) {
				
				if(i == entrada[0] && j == entrada[1]) {
					System.out.print("E|");
				}
				else {
					switch (campo[i][j]) {
					
					case 0:
						System.out.print("S");
						break;
					case 1:
						System.out.print("_");
						break;
					case 101:
						System.out.print("O");
						break;
					default:
						break;
					}
					System.out.print("|");
	
				}
								
			}
			System.out.println("");
		}
	}
	
	private int geraRand(int maximo) {

		Random randomico = new Random();
		
		int rand = randomico.nextInt(maximo);
		
		return rand;
	}
}
