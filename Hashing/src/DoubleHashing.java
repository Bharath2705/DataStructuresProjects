


package BXR180008;
import BXR180008.Timer;
import java.util.HashSet;
import java.util.Set;



public class DoubleHashing<T> {    //class to perform double hashing
	private class Entry<E>{    	//class to create nodes
		E element;
		boolean isDeleted;
		
		 
		public Entry(E element)   // constructor that creates the node for every insertion
		{
			this.element = element;
			this.isDeleted = false;					//the status of each node is set to false by default
		}
	}
	

private Entry<T>[] hashTable;   			//hashtable to store the elements that are passed to it
private int hash2;							//value of the second hash function
int size =0;
public DoubleHashing(int tableSize){		//constructor to define the hashTable with some size
	hashTable= new Entry[tableSize];		//definition of a hashtable of specific size
}

//method to get nearest prime number smaller than the hashtable size
public int getPrime(int x)					
{
	int prime=0;
	if(x!=0) {
	for(int i=hashTable.length; i>0; i--) {		//loop that iterates starting from the table size to get the nearest prime
		int count=0;
		for(int j=1; j<x; j++) {
		if(i%j==0) {
			count++;					//incrementing the count of each number for prime 
			prime=i;
		}
		}
		if(count==2) {						//checking whether the number is prime or not
			return prime;					//returns the nearest prime number smaller than the hashtable size
		}
	}
	}
	return 0;								//returns 0 when there is no prime number found in that range
}

//method to find the second hash using the hash function2
public int hashCode2(T x) {					
	int prime = getPrime(hashTable.length);	
    hash2 = (prime - (x.hashCode() % prime)) ;  //random second hash function
	return hash2;								//returns the hash value calculated from the hash function2
}

//method that checks for an element x in the table and returns an index for that element 
public int find(T x ) {						
	int k=0;																// k is used to iterate through the index2 values until a new index is generated
	int index1 = indexFor(hash(x.hashCode()), hashTable.length);			//first hash function that returns an index 
	int index2;																// index value created from the hash function2 
	int xspot;
	while (true) {
		index2=(index1 + k* hashCode2(x)) % hashTable.length;				//index generated using the second hash function and index1
		if( hashTable[index2] == null || (hashTable[index2]).element.equals(x)) {		//checks if index2 location is null or if x is already in the table
			return index2;																//returns the same index if element already found 
		}
		else if ( hashTable[index2].isDeleted == true) {								//checks if the element at the index2 position is deleted  
			break;																		//goes out of the while loop to add an element at the deleted position
		}
		else k++;																		//if the above conditions are not met, if the index values are repeated, k is incremented and a new index value is generated
		
		if(k != 0 && k >hashTable.length)												//if a unique index is not found, rehashing is done so that the range of indexes change
		{
			//System.out.println("rehashed called from find because of infinite looping");
			rehash();																	//calling the rehash method
			
		}
	}
	
	// to insert an element at the deleted position
	 xspot = index2;																	
	 while (true) {																		
		 k++;
		 if(hashTable[index2].element.equals(x))										//Checks if the position has the same element before
			 return index2;																//returns the same index value
		 if(hashTable[index2].isDeleted==true) {										// checks if the previous element in that position is deleted
			 return xspot;																//returns that position where the element is deleted
		 }
	 }
}

//checks if element x is present in the hash table and returns true, if present, otherwise false
public boolean contains(T x) {														
	int location = find(x);																//calling the find method to check the position of the element x in the hash table if present
	if(hashTable[location] != null && hashTable[location].isDeleted== false && hashTable[location].element.equals(x)) //checking if the element at the location,returned from find method, contains the search element 
		return true;																						//returns true if found
	else 
		return false;																						//returns false if not found
}

//adds an element x to the table
public boolean add(T x) {																												
	int location = find(x);										//finding the index of the table to add the element x 
	if(hashTable[location] != null && hashTable[location].isDeleted== false)			//if there is an element already present, it returns false
		return false;
	else {
		hashTable[location] = new Entry(x);							//creates a new node and adds it to the location returned from find method
		if(hashTable[location].isDeleted== true)						// if the previous element is deleted and new element needs to be added, loop breaks and goes out of the loop
			hashTable[location].isDeleted = false;						//making the status of node false to add a new element at that position
				size++;													//incrementing the number of elements added to the table
		
		if(size>hashTable.length * 0.75) {
//			showHashTable();
			rehash();												//rehashing is done when 3/4th of the elements of the table are filled...***loadfactor can be changed as needed
		}
		return true;												//returns true if element is succesfully added to the table
	}
}

//removes the element x from the table and returns the element if deleted, otherwise null
public T remove(T x) {						 		
	int location = find(x);											//taking the index value of that element x
	if(hashTable[location] != null && hashTable[location].element.equals(x) ) {			//checking if the table has that element,deletes and returns that element if present
		T result = hashTable[location].element;												//taking the value of the element to be deleted
		hashTable[location].isDeleted= true;
		return result;																		//returns the deleted element	
	}
	return null;																			// if element is not found, returns null
}

//method to  print the hashTable elements
public void showHashTable()
{
	for(int i = 0; i < hashTable.length; i++)
	{
		if(hashTable[i] != null && hashTable[i].isDeleted == false)
			System.out.println(i + " " + hashTable[i].element);
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
	
	//method to perform rehash of elements to increase the size of the hashTable
	public void rehash()			
    {
		size=0;														
        //create a new (larger) hash table new_Hash_Table;
		int presenttableSize = hashTable.length;				//taking the present size of the hashtable
		Entry<T>[] oldHashTable = hashTable;		//creating another hashtable to store the present elements
		for( int i=0; i<presenttableSize; i++)
		 hashTable= new Entry[2* oldHashTable.length];			//increasing the size of the hashTable to twice
		for (int i = 0; i < presenttableSize; i++) {
			if(oldHashTable[i] != null && oldHashTable[i].isDeleted == false) {
				add(oldHashTable[i].element);						//adding the values again to the old hashtable with a new capacity
				
			}
				
		}
    }
	// Key x is stored at table[ hash( x.hashCode( ) ) & ( table.length − 1 ) ].

	}
	

