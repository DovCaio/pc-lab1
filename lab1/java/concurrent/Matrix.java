import java.util.Random;

public class Matrix{



    public static void main(String[] args){
    
    	int size = 10;
	int[][] matrix = generateMatrix(size);

	for(int i = 0; i < size; i++){
	
		for (int j = 0; j < size; j++){
		
			System.out.println(matrix[i][j]);
		
		}
	
	}

	int min = Min(matrix);

	int max =  Max(matrix);
	System.out.println("VALOR MINIMO = " + min);	
	System.out.println("VALOR MAXIMO = " + max);
    
    }

    
   public static class ThreadsGeraVetor implements Runnable{
  
	private int[] vetor;

	ThreadsGeraVetor(int[] vetor){
	
		this.vetor = vetor;
	
	}


	public void randomizaVetores(){
	    Random rand = new Random();
	    for (int i  = 0; i < vetor.length; i++){
	    
		vetor[i] = rand.nextInt(29500 - 250 + 1) + 250;
	    
	    } 
	
	
	}

   	@Override
	public void run(){
	
	    
	    randomizaVetores();
	

	}
   
   }



    public static int[][] generateMatrix(int size) {
        int[][] matrix = new int[size][size];
        Random random = new Random();
	
	

        for (int i = 0; i < size; i++) {
            ThreadsGeraVetor tgv = new ThreadsGeraVetor(matrix[i]);
	    Thread threadGV = new Thread(tgv);
	    threadGV.start();
        }

        return matrix;
    }

    public static class MinOrMaxThread implements Runnable{
    
	private int[] vector;
	private int[] resultados;
	private int indexResultados;
	private MinOrMax mm;

    	MinOrMaxThread (int[] vector, int[] resultados, int indexResultados, MinOrMax mm){
	
		this.vector = vector;
		this.resultados = resultados;
		this.indexResultados = indexResultados;
		this.mm = mm;
	
	}

	@Override
	public void run (){
	
		int menorOuMaior  =  vector[0];
		for (int i = 1 ; i <  vector.length; i++ ){
		
			if (mm.execute(menorOuMaior, vector[i])){
			
				menorOuMaior = vector[i];
			
			}
		
		}

		resultados[indexResultados] = menorOuMaior;
	
	
	}
    
    }

    interface MinOrMax{
    
	    public boolean execute(int valorAtual, int valorNovo);
    
    }

    public static class Minimo implements MinOrMax{
    
	    public boolean execute(int valorAtual, int valorNovo){
	    
		    return valorAtual > valorNovo;
	    
	    } 
    
    }

    public static class Maximo implements MinOrMax{
    
    	public boolean execute(int valorAtual, int valorNovo){
	
		return valorAtual < valorNovo;

	}
    
    }

    public static int Min(int[][] matrix) {
        int smallest = Integer.MAX_VALUE;

	int [] resultados = new int[matrix.length];
	Minimo minimo = new Minimo();

        for (int i = 0; i < matrix.length; i++) {
            MinOrMaxThread momt = new MinOrMaxThread(matrix[i], resultados, i, minimo);
	    Thread t =  new Thread(momt);
	    t.start();
	    try{
	    	t.join();
	    }catch (InterruptedException IE){
	    
		    System.out.println("Resolve ai!");
	    
	    }
        }


	for (int i = 0 ; i < resultados.length; i++ ){
	
		if(minimo.execute(smallest, resultados[i])){
		
			smallest = resultados[i];
		
		}
	
	}

        return smallest;
    }

    public static int Max(int[][] matrix) {
        int largest = Integer.MIN_VALUE;

	int[] resultados = new int[matrix.length];
	Maximo maximo = new Maximo();

        for (int i = 0; i < matrix.length; i++) {

		MinOrMaxThread momt = new MinOrMaxThread(matrix[i], resultados, i, maximo);
		Thread t = new Thread(momt);
		t.start();
		try{
		
			t.join();
		
		}catch (InterruptedException IE){
		
			System.out.println("Resolve ai!");
		
		}
            
        }

	for (int i = 0; i < resultados.length ; i++){
	
		if (maximo.execute(largest, resultados[i])){
		
			largest = resultados[i];
		
		}
	
	}

        return largest;
    }
}
