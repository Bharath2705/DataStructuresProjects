/**
	 * Implement a bounded-sized queue BoundedQueue<T>, using arrays with the following operations: 
	 * To avoid "generic array cannot be created" error, declare the array to be Object[] and 
	 * type-cast where needed to avoid type warnings. 
	 * 
	 * BoundedQueue(int size): 
	 * 		Constructor for queue of given size 
	 * boolean offer(T x): 
	 * 		add a new element x at the rear of the queue 
	 * 		returns false if the element was not added because the queue is full 
	 * T poll(): 
	 * 		remove and return the element at the front of the queue 
	 * 		return null if the queue is empty 
	 * T peek(): 
	 * 		return front element, without removing it (null if queue is empty) 
	 * int size(): 
	 * 		return the number of elements in the queue 
	 * boolean isEmpty(): 
	 * 		check if the queue is empty 
	 * void clear(): 
	 * 		clear the queue (size=0) 
	 * void toArray(T[] a):	
	 * 		fill user supplied array with the elements of the queue, in queue order
	 */
	
package BXR180008;

import java.util.Scanner;

public class BoundedQueue<T> {
	private int maxSize;
	private int front;
	private int rear;
	
	T[] bQ;
	
	
	
	public BoundedQueue(int size) {
		
		maxSize = size;
		bQ = (T[]) new Object[maxSize];
		front = -1;
		rear = -1; // Empty Queue condition/ Initialization 
		//lastOperationAdded = false;
	}
	
	/**
	 * Add a new element x at the rear of the queue 
	 * @param x
	 * @return false if the element was not added because the queue is full
	 */
	boolean offer(T x) {
		if (size() == maxSize) {
			return false;
		}
		else {
			rear = (rear + 1) % maxSize;			
			if (front==-1) { 
				front++; 
				}
			bQ[rear] = x;
		}
 		return true;
		
	}
	
	/**
	 * Remove and return the element at the front of the queue 
	 * @return null if the queue is empty
	 */
	public T poll() {
		T element;
		if (!this.isEmpty()) {
			element = (T) bQ[front];
			
			if (front == rear) front = rear = -1; // Empty Queue condition/ Initialization
			else front = (front+1) % maxSize;
			
			return element;
		}
		return null;	
	}
	
	/**
	 * return front element, without removing it (null if queue is empty)
	 * @return the front element
	 */
	public T peek() {
		T element;
		if (!this.isEmpty()) {
			element = (T) bQ[front];
			return element;
		}
		return null;
	}
	
	// return the number of elements in the queue 
	public int size() {
		if (front == -1 && rear == -1) { 
			return 0; }
		
		if (rear < front) {
			return maxSize + (rear - front) + 1;
		}
		return (rear - front + 1);
	}
	
	// check if the queue is empty
	public boolean isEmpty() {
		if (size() == 0) return true;
		return false;
	}
	
	// clear the queue (size=0)
	public void clear() {
		front = rear = -1;
	}
	
	/*
	  fill user supplied array with the elements of the queue, in queue order
	  @param a the supplied array
	 */
	public void toArray(T[] a) {
		int l = size();
		for (int i=0; i<l; i++) {
			a[i] = (T) bQ[(front+i) % maxSize];
			System.out.println(a[i]);
		}
	}
	
	public static void main(String args[]) {
		Integer a[];
		Scanner in;
		
		in = new Scanner(System.in);
		
		System.out.println("Enter maxSize of BoundedQueue: ");
		int maxSize = in.nextInt();
		BoundedQueue<Integer> b = new BoundedQueue<>(maxSize);
		
		System.out.println("Enter choices 1. offer() 2. poll() 3. peek() 4. size() 5. isEmpty() 6. clear() 7. toArray(): 8. Quit");
		int choice = in.nextInt();
		
		while(choice != 8) {
			
			switch(choice) {
			case 1: // to offer(x)
				//Integer e = (Integer) in.nextInt();
				System.out.println("x");
				int ele = in.nextInt();
				System.out.println(b.offer(ele));
				//System.out.println("(f,r) = "+b.front+" "+b.rear);
				break;
			
			case 2: // poll()
				System.out.println(b.poll());
				//System.out.println("(f,r) = "+b.front+" "+b.rear);
				break;
				
			case 3: // peek()
				System.out.println(b.peek());
				//System.out.println("(f,r) = "+b.front+" "+b.rear);
				break;
				
			case 4: // size()
				System.out.println("Size: "+b.size());;
				//System.out.println("(f,r) = "+b.front+" "+b.rear);
				break;
				
			case 5: // isEmpty()
				System.out.println(b.isEmpty());
				//System.out.println("(f,r) = "+b.front+" "+b.rear);
				break;
				
			case 6: // clear()
				b.clear();
				//System.out.println("(f,r) = "+b.front+" "+b.rear);
				break;
				
			case 7: // toArray()
				int size=in.nextInt();
				a = new Integer[size];
				int qsize=b.size();
				if(size<qsize) {
					System.out.println("Cannot copy-Array size is less than queue size");
				break;
				}
				else {
				b.toArray(a);
				}
				//System.out.println("(f,r) = "+b.front+" "+b.rear);
				break;
				
			case 8: // Exit
				break;
			}
			
			System.out.println("Enter choices 1. offer() 2. poll() 3. peek() 4. size() 5. isEmpty() 6. clear() 7. toArray(): 8. Quit");
			choice = in.nextInt();
			
		}
	}
}
