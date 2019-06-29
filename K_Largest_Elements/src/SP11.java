package bxr180008;

import java.util.Random;

public class SP11 {			// class to implement insertion sort and merge sort methods
    public static Random random = new Random();			// random number generator object
    public static int numTrials = 1;					//number of times the algorithm is run to calculate the average of those times
    public static int Threshold = 17;
   	private static PriorityQueue<Integer> pq; 			//queue of size k that contains the k largest elements of the given stream

    
    public static void main(String[] args) {
	int n = 10;  									//number of elements in the array to be sorted 
    int k = 7;   									//number of largest elements needed from the stream 

	int choice = 1 + random.nextInt(2);					//choice to choose which sorting method to run
	if(args.length > 0) { n = Integer.parseInt(args[0]); }
	
	
        int[] arr = new int[] {1,4,2,6,3,19,16,15,14,12};							//array to store the elements

//        for(int i=0; i<n; i++) {						
//	    arr[i] = i;										//initializing the elements of array 
//	}
        
	Timer timer = new Timer();							// timer class object
	
	timer.start();
	
	 switch(2) {
	   case 1:
	  	 for(int i = 0; i < numTrials; i++)
	  	 {
	  		 Shuffle.shuffle(arr);			//shuffling the elements of array for sorting
	  		 	select(arr,k);					//method to find k largest elements using array sort
	  	 }
		    break; 
	   case 2:
	  	 for(int i = 0; i < numTrials; i++)
	  	 {
//	  		 Shuffle.shuffle(arr);			//shuffling the elements of array for sorting
	  		 kLargestElements_in_Stream(arr,k);		//method to find k largest elements in a stream using a heap
	  	 
	  	 }
	  	for(int i = 0; i < n; i++)
	  	 {
	  	 System.out.println(arr[i]);
	  	 }
		   break;
	   }

	
	timer.end();				
	timer.scale(numTrials);

	System.out.println("Choice: " + choice + "\n" + timer);
    }

    //Insertion sort
    public static void insertionSort(int[] arr) {
    	insertionSort(arr,0, arr.length-1);    		
    	
    }
    public static void insertionSort(int[] arr, int p,int r) 
    {
    for(int i=p+1;i<=r;i++) {
		int element=arr[i];						//starting with the index 1 element
		int key=i;								//storing the index of element
		while(key>0 && arr[key-1]>element)		//comparing element with the previous elements
		{
			arr[key] = arr[key-1];				//swap the elements if not in sorted order
			key--;								//taking the index back and comparing all the previous elements with that element at key index
		}
		arr[key] = element;						
    }
	}
    
    //method to find k largest elements of array by sorting the array
    public static void select(int[] arr, int k) {
    	if(k<=arr.length)
    	 select(arr,0,arr.length,k);	
    	throw new RuntimeException("No Such Element exists");			//throws exception if the array is empty
	}
        
    private static int select(int[] arr, int p, int n, int k){
    		if(arr.length<Threshold) {
    			insertionSort(arr,p,p+n-1);
    		}
    		else {
    		int q = randomizedPartition(arr,p,p+n-1);			//method that returns the location of pivot element in the array
    		int left = q-p;								//size of subarray to the left of pivot element
    		int right = n-left-1;						//size of subarray to the right of pivot element
    		
    		if(right>=k) {								//if k is less than the size of right subarray, we further partition the right subarray, ignoring the left
    			return select(arr,q+1,right,k);
    		}
    		else if(right+1==k) {						//if k is equal to size of right subarray and the pivot, we return from the pivot element, ignoring the left subarray
    			return q;
    		}
    		else {										//if k is greater than the size of right subarray, we further partition the left subarray
    			return select(arr,p,left,k-right-1);    	
    		}
    		}
    		return 0;
    }

    //method that returns the pivot location
	private static int randomizedPartition(int[] arr, int p, int r) {
		int i = p-1;
		int x=arr[r];				//taking the last element in the array as pivot element
		for(int j=p;j<r;j++){		
			if(arr[j]<=x){			//comparing the pivot with other elements of array and swapping 
				i=i+1;
				Shuffle.swap(arr,i,j);
			}
		}
		Shuffle.swap(arr,i+1,r);	//swap the last element
		return i+1;					//return the pivot element position after placing it in its correct position in the array
	}
    

	//Java's priority queue implementation
    //method to find k largest elements in a stream and compare it with the sorting technique
	private static void kLargestElements_in_Stream(int[] arr, int k)
	{
		 pq = new PriorityQueue<Integer>();
	   	 int n = arr.length;
		 for(int i=0; i<k;i++) {
	   		 pq.add(arr[i]);
	   	 }
//	   	for(int i=0; i<5;i++)
//	   	{
	   		System.out.println(pq);
	   	
	   	 for(int i=k;i<n;i++) {
	   	 int x=arr[i];
	   	 if(x>pq.peek()) {
		    	pq.add(x);
		    	pq.remove();
		    }
	   	 }
	    		   		 
	}
	
   /** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() { if(!ready) { end(); }  return elapsedTime; }

        public long memory()   { if(!ready) { end(); }  return memUsed; }

	public void scale(int num) { elapsedTime /= num; }
	
        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }
    
    /** @author rbk : based on algorithm described in a book
     */


    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {
	
	public static void shuffle(int[] arr) {
	    shuffle(arr, 0, arr.length-1);
	}

	public static<T> void shuffle(T[] arr) {
	    shuffle(arr, 0, arr.length-1);
	}

	public static void shuffle(int[] arr, int from, int to) {
	    int n = to - from  + 1;
	    for(int i=1; i<n; i++) {
		int j = random.nextInt(i);
		swap(arr, i+from, j+from);
	    }
	}

	public static<T> void shuffle(T[] arr, int from, int to) {
	    int n = to - from  + 1;
	    Random random = new Random();
	    for(int i=1; i<n; i++) {
		int j = random.nextInt(i);
		swap(arr, i+from, j+from);
	    }
	}

	static void swap(int[] arr, int x, int y) {
	    int tmp = arr[x];
	    arr[x] = arr[y];
	    arr[y] = tmp;
	}
	
	static<T> void swap(T[] arr, int x, int y) {
	    T tmp = arr[x];
	    arr[x] = arr[y];
	    arr[y] = tmp;
	}

	public static<T> void printArray(T[] arr, String message) {
	    printArray(arr, 0, arr.length-1, message);
	}

	public static<T> void printArray(T[] arr, int from, int to, String message) {
	    System.out.print(message);
	    for(int i=from; i<=to; i++) {
		System.out.print(" " + arr[i]);
	    }
	    System.out.println();
	}
    }
}

