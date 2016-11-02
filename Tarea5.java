import java.util.*;
public class Tarea5 {

	public static int count = 0;
	
	public static ArrayandOB Quicksort(ArrayList<Integer> arrayob){
		ArrayandOB result = new ArrayandOB();
		int n = arrayob.size();
		if(n < 1){
			return result = new ArrayandOB(arrayob, count);
		}
		int centro = n/2;
		int pivote = arrayob.get(centro);
		ArrayList<Integer> menores = new ArrayList<Integer>();
		ArrayList<Integer> mayores = new ArrayList<Integer>();
		arrayob.remove(centro);
		for(int i = 0; i < n-1; i++){
			count = count+1;
			if (arrayob.get(i) < pivote)
				menores.add(arrayob.get(i));
			else
				mayores.add(arrayob.get(i));
;		}
		result = Quicksort(menores);
		result.array.add(pivote);
		result.array.addAll(Quicksort(mayores).array);
		result.ob = count;
		return result;
	}
	public static ArrayList<Integer> generateArray(int n){
		    int k=n;  //auxiliar;
	        ArrayList<Integer> numbers=new ArrayList<Integer>();
	        ArrayList<Integer> result=new ArrayList<Integer>();
	        Random random=new Random();
	        int res;
	        
	        //se rellena una matriz ordenada del 1 al 31(1..n)
	        for(int i=0;i<n;i++){
	            numbers.add(i+1);
	        }
	        
	        for(int i=0;i<n;i++){
	            res=random.nextInt(k);            
	            result.add(i, numbers.get(res));
	            numbers.remove(res);
	            k--;      
	        }
	        return result;
	}
	
	public static ArrayList<Duplas> Executions(){
		int n = 4;
		double average = 0.0;
		ArrayList<Integer> randomArray = new ArrayList<Integer>();
		ArrayList<Duplas> resultsDuplas = new ArrayList<Duplas>();
		for(n = 4; n <= 1004; n = n + 20){
			System.out.println("Dupla: " + n + ", guardada.");
			for(int i = 0; i < 50; i++){
				randomArray = generateArray(n);
				average = average + Quicksort(randomArray).ob;
			}
			average = average/50;
			resultsDuplas.add(new Duplas(n,average));
			average = 0.0;
			
		}
		return resultsDuplas;
	}
	
	
}


























