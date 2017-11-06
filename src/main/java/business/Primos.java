package business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Primos {

	public static void main(String[] args) {
		List<Integer> primos = new ArrayList<Integer>();
		
		primos.add(2);
		primos.add(3);
		
		for (int i=5; i<=5002; i++) {
			Iterator<Integer> it = primos.iterator();
			
			boolean isPrimo = true;
			while (it.hasNext()) {
				Integer p = it.next();
				
				if ((i%p) == 0) {
					isPrimo = false;
					break;
				}
			}
			
			if (!isPrimo) {
				continue;
			}
			
			if (i >= 41) {
				System.out.println(i);
			}
			
			primos.add(i);
		}
	}

}
