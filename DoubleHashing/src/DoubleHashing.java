package BXR180008;

import java.math.BigInteger;
import java.util.Scanner;
import javax.net.ssl.SSLEngineResult.Status;

public class DoubleHashing<T > {
private Entry<T>[] hashTable;

int size=0;

enum Status{
FILLED,EMPTY,DELETED;
	}

class Entry<E> {
	E element;
	Status status;


public Entry(E element){
	this.element=element;
	this.status = status.FILLED;
}
}
public DoubleHashing(int tableSize) {
	hashTable = new Entry[tableSize];
}

 
public void showHashTable()
{
       for(int i = 0; i < hashTable.length; i++)
       {
    	   if(hashTable[i]==null || hashTable[i].status == Status.DELETED || hashTable[i].status == Status.EMPTY) {
                  System.out.println(i+ " "+ null);
    	   }
    	   else {
               System.out.println(i + " " + hashTable[i].element);
    	   }
       }
}
 
static int hash(int h) {
	// This function ensures that hashCodes that differ only by
	// constant multiples at each bit position have a bounded
	// number of collisions (approximately 8 at default load factor).
	h ^= (h >>> 20) ^ (h >>> 12);
	return h ^ (h >>> 7) ^ (h >>> 4);
	}

static int indexFor(int h, int length) { // length = table.length is a power of 2
	return h & (length-1);
	}
 
public int hashCode2(T x) {
	int y = getPrime(hashTable.length);
    int hash2 = (y - (x.hashCode() % y)) ;
        return hash2;
    }
public int getPrime(int x)
{
	int prime=0;
	if(x!=0) {
	for(int i=hashTable.length-1; i>0; i--) {
		int count=0;
		for(int j=1; j<x; j++) {
		if(i%j==0) {
			count++;
			prime=i;
		}
		}
		if(count==2) {
			return prime;
		}
	}
	}
	return 0;
}
public int find(T x ) {
    while (true) {
        //System.out.println("hash1" +  indexFor(hash(x.hashCode()), hashTable.length)+ "\t");
        int index1 = indexFor(hash(x.hashCode()), hashTable.length);
      //  System.out.println("hash1 "+ index1);
        //System.out.println("hashlength "+ hashTable.length);

        if(hashTable[index1] == null) {
                   return index1;
        }
        else {
        	if(hashTable[index1].element == x) {
        		return index1;
        	}
        	else {
                   int index2 = 0;
                   for( int k = 1; k < hashTable.length; k++) {
                                 index2 = (index1 + k * hashCode2(x)) % hashTable.length;
                                 //System.out.println(index2);
                                 if(hashTable[index2] == null )
                                 {
                                	 return index2; 
                                 }
                                 else if(hashTable[index2].element == x ) {
                                	 return index2;
                                 	}
                               
                   }
                   
                   //.System.out.println("hash2 " + index2);
                   return index2;
        }
    
}
}
}
private void rehash() {
	Entry[] allElements = new Entry[size];
	// System.out.println("SIZE:"+size);
	int capacity = hashTable.length;
	for(int i = 0, j = 0; i < hashTable.length; i++) {
		if (hashTable[i] != null && hashTable[i].status == Status.FILLED) {
			allElements[j++] = hashTable[i];
			hashTable[i] = null;
		}
	}

	capacity = (int) Math.pow(2,capacity);
	size = 0;
	hashTable = new Entry[capacity];

	for (int i = 0; i < allElements.length; i++) {
		add((T)allElements[i].element);
	}
}
    

         
	

public boolean contains(T x) {
           int location = find(x);
           System.out.println("contains location"+location);
           if(hashTable[location]!=null && hashTable[location].element.equals(x) ){
                      return true;
           }
           else {
        	   System.out.println("contains");
                      return false;  
            }
        	   
         
}
public boolean add(T x) {
           int location = find(x);
           //System.out.println("location" + location);
           double loadFactor = 0.5;
           int tableSize = hashTable.length;
        	   if(contains(x)) {
        		   return false;
        	   }
        		
        	   else if(hashTable[location] == null || hashTable[location].status == Status.DELETED || hashTable[location].status == Status.EMPTY) {
                      hashTable[location] = (DoubleHashing<T>.Entry<T>) new Entry<T>(x);
                      size++;
//                      if(size > (loadFactor * tableSize)) {
//                      rehash();
//                      }
                  	//System.out.println(hashTable[0].status);
                     return true;
        	}
			return true;
           
}
public T remove(T x) {
           int location = find(x);
       
           if(hashTable[location] != null) {
           if(hashTable[location].element==x) {
                      T result = hashTable[location].element;
                      hashTable[location].element=null;
                      hashTable[location].status = Status.DELETED;
                      size--;
                      return result;
           }
           }
           return null;
}
public static void main(String[] args) {
	//Integer[] liste = {1,2,3,4,5,6};
	DoubleHashing<Integer> dh = new DoubleHashing<>(5);

//for(int i=0; i<liste.length; i++) {
//		System.out.println(dh.add(123));
//		System.out.println(dh.add(124));
//		System.out.println(dh.add(128));
//
//		System.out.println(dh.add(125));
//		System.out.println(dh.add(126));
//		System.out.println(dh.add(127));
//		System.out.println(dh.add(129));
//		System.out.println(dh.remove(129));
//		System.out.println(dh.add(129));
	System.out.println(dh.add(12497));
//	System.out.println(dh.add(28754));
//	System.out.println(dh.add(34678));
//	System.out.println(dh.add(45500));
//	System.out.println(dh.add(56699));
//    System.out.println(dh.add(67891));
//	System.out.println(dh.add(70011));
//	System.out.println(dh.add(81209));
// 	System.out.println(dh.add(99194));
	System.out.println("contains: "+dh.contains(12497));

//System.out.println(dh.remove(99194));
//
//	System.out.println(dh.add(18608));
//	System.out.println(dh.add(14608));
//	System.out.println(dh.add(10608));
//	


	


	dh.showHashTable();

}
 
}
 
