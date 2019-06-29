package BXR180008;

import java.util.Random;

public class SP9 {			// class to implement insertion sort and merge sort methods
    public static Random random = new Random();			// random number generator object
    public static int numTrials = 10;					//number of times the algorithm is run to calculate the average of those times
    int Threshold=15;
    public static void main(String[] args) {
	int n = 100;  									//number of elements in the array to be sorted 
	int choice = 1 + random.nextInt(4);					//choice to choose which sorting method to run
	if(args.length > 0) { n = Integer.parseInt(args[0]); }	
	if(args.length > 1) { choice = Integer.parseInt(args[1]); }
        int[] arr = new int[n];							//array to store the elements
        
        for(int i=0; i<n; i++) {						
	    arr[i] = i;										//initializing the elements of array 
	}
        
	Timer timer = new Timer();			// timer class object
	switch(4) {
	case 1:								//choice for insertion sort
	    Shuffle.shuffle(arr);			//shuffling the elements of array for sorting
	    numTrials = 1;					
	    insertionSort(arr);				//invoking insertion sort method
	    
	    break;
	    
	case 2:								//choice for merge sort take 1
	    for(int i=0; i<numTrials; i++) { //running the merge sort algorithm numTrials times
		Shuffle.shuffle(arr);
		mergeSort1(arr);				//invoking merge sort take 1 method
	    }

	    break;  
	    
	case 3:										//choice for merge sort take 2
	    for(int i=0; i<numTrials; i++) {			//running the merge sort algorithm numTrials time
		Shuffle.shuffle(arr);
		mergeSort2(arr);				//invoking merge sort take 2 method
	    }
	    break;
	    
	case 4:									//choice for merge sort take 3
	    for(int i=0; i<numTrials; i++) {		//running the merge sort algorithm numTrials time
		Shuffle.shuffle(arr);
		mergeSort3(arr);						//invoking merge sort take 3 method
	    }
	    
	    for(int i=0; i<n; i++) {
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
    
    
    //merge sort take1
    public static void mergeSort1(int[] arr) {
    	mergeSort(arr,0,arr.length-1);
    }
    
    public static void mergeSort(int[] arr,int p,int r) {
    	if(p<r) {										//checking if there are elements in the array
    		int q=(p+r)/2;									//taking the index of middle element
    		mergeSort(arr,p,q);								//sort into arr
    		mergeSort(arr,q+1,r);							//sort into arr
    		merge(arr,p,q,r);								//merge into arr
    	}
    }
    public static void merge(int[] arr, int p,int q, int r) {
    	int n1=q-p+1;
    	int n2=r-q;
    	int[] L=new int[n1];					//array to store one half of sorted array
    	int[] R=new int[n2];				// array to store second half of sorted array
    	System.arraycopy(arr, p, L, 0, n1);			//copying the elements of first half to L
    	System.arraycopy(arr, q+1, R, 0, n2);		//copying the elements of second half to R
    	int i = 0, j = 0;
    	for(int k=p; k<=r;k++) {
    	if(j>=n2 || (i<n1 && L[i]<=R[j]))	{		//comparing the elements of L and R
    		arr[k] = L[i++];						//arranging the elements into the the array in sorted order
    	}
    	else {
    		arr[k] = R[j++];			//arranging the elemnts into the the array in sorted order
    	}
    	}
    	
    }
    
    //merge sort take2
    public static void mergeSort2(int[] arr) {
    	int[] arr1 = new int[arr.length];	//temporary array 
    	mergeSort2(arr,arr1,0,arr.length-1);
    	
    }
    public static void mergeSort2(int[] arr,int[] arr1,int p,int r) {
    	
    	if((r-p+1)<Threshold) {							//taking a threshold value to limit to insertion sort instead of merge sort
    		insertionSort(arr, p, r);
    	}
    	else {
    		int q=(p+r)/2;				//taking the index of middle element
    		mergeSort2(arr,arr1,p,q);		//sort into arr
    		mergeSort2(arr,arr1,q+1,r);		//sort into arr
    		merge2(arr, arr1, p, q, r);			//merge into arr
    	}
    }
    
    public static void merge2(int[] arr,int[] arr1, int leftend,int mid, int rightend) {
    	System.arraycopy(arr, leftend, arr1, leftend, rightend-leftend+1);			//copying the elemnts of array to temporary array for comparing
    	
    	int i=leftend, j=mid+1;
    	for(int k=leftend; k<=rightend; k++) {
    		if(j>rightend || (i<=mid && arr1[i]<=arr1[j])) {				//comparing the elements of the two arrays
    			arr[k]=arr1[i++];
    		}
    		else {
    			arr[k]=arr1[j++];
    	}
       }
    }

    
 //merge sort take3
    public static void mergeSort3(int[] arr) {
    	int[] arr1 = new int[arr.length];
        System.arraycopy(arr, 0, arr1, 0, arr.length);					//copying the elements of array to temporary array for comparing
    	mergeSort3(arr,arr1,0,arr.length);
    	
    }
 public static void mergeSort3(int[] arr, int[] arr1, int p, int n) {
    	
    	if(n<Threshold) {									//taking a threshold value to limit to insertion sort instead of merge sort
    		insertionSort(arr, p, p+n-1);
    	}
    	else {
    		int q=n/2;								//taking the index of middle element
    		mergeSort3(arr1,arr,p,q);				//sort into arr1
    		mergeSort3(arr1,arr,p+q,n-q);			//sort into arr1
    		merge3(arr,arr1,p, p+q-1, p+n-1);		//merging into arr
    	} 
    }
    public static void merge3(int[] arr,int[] arr1, int leftend,int mid, int rightend) {
    	int i=leftend, j=mid+1, k=leftend;
    	
    	while(i<=mid && j<=rightend) {				
    		if(arr1[i]<=arr1[j]) {						//comparing the elements of the two arrays
    			arr[k++]=arr1[i++];
    		}
    		else {
    			arr[k++]=arr1[j++];
    		}
    	}
    	while(i<=mid) {							//going through the array for unvisited elements in the left half
    		arr[k++]=arr1[i++];
    	}
    	while(j<=rightend) {				//going through the array for unvisited elements in the right half
    		arr[k++]=arr1[j++];
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

