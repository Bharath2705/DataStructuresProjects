
/*
 * 	Prateek Sarna : pxs180012
 *	Bharath Rudra : bxr180008
 */

package pxs180012;

import java.util.HashSet;
import java.util.Set;

public class DHDriver_partone {
	public static void main(String[] args) {
	    DoubleHashing<Integer> dh = new DoubleHashing<>(1000);

	    Set<Integer> hashSet = new HashSet<>();
	   Timer timerForDoubleHashing = new Timer();
	   timerForDoubleHashing.start();
	   for(int i=0 ; i<= 10000;i++) {
		   dh.add(i);
	   }
	   timerForDoubleHashing.end();

	  
	   Timer timeForHashSet = new Timer();
	   timeForHashSet.start();
	   for( int i=0; i<=10000; i++) {
		   hashSet.add(i);
	   }
	   timeForHashSet.end();
	   
	   System.out.println("******");
	   System.out.println("Timer for DoublHashing");
	   System.out.println(timerForDoubleHashing);
	 
	  
	   
	   System.out.println("******");
	   System.out.println("Timer for HashSet");
	   System.out.println(timeForHashSet); 
	   
	  

	}

}
