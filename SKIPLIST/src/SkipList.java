
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;



// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {
	static final int PossibleLevels = 33;
	Entry<T> head, tail; // dummy nodes head and tail
	int size; // size of the skipList
	int maxLevel;
	Entry<T>[] last; // used by find()
	int[] spans; // used by find() to store spans while traversing levels
	Random random;

	static class Entry<E> {
		E element;
		Entry<E>[] next;
		Entry<E> prev;
		int span[];
		int index;

		public Entry(E x, int lev) {
			element = x;
			next = new Entry[lev];
			span = new int[lev];
		}

		public E getElement() {
			return element;
		}
	}

	// Constructor
	public SkipList() {
		head = new Entry<T>(null, PossibleLevels);
		tail = new Entry<T>(null, PossibleLevels);
		size = 0;
		maxLevel = 1;
		last = new Entry[PossibleLevels];
		spans = new int[PossibleLevels];
		// Arrays.fill(spans, 1);
		random = new Random();
		// setting head.next to tail
		for (int i = 0; i < PossibleLevels; i++) {
			head.next[i] = tail;
		}
		tail.prev = head;
	}

	// Helper function to search for element x
	public void find(T x) {
		Entry<T> temp = head;
		int i = maxLevel - 1;
		int span = 0;
		while (i >= 0) {
			// int span = temp.span[i];
			while (temp.next[i] != null && temp.next[i].element != null && x.compareTo(temp.next[i].element) > 0) {
				span += temp.span[i] + 1;
				temp = temp.next[i];
			}
			last[i] = temp;
			// Total distance between nodes when jumping levels
			spans[i] = span;
			i--;
		}
	}

	// Choose the Level of an entry
	public int chooseLevel() {
		int level = 1 + Integer.numberOfTrailingZeros(random.nextInt());
		if (level > maxLevel)
			maxLevel = level;
		return level;
	}

	// Add x to list. If x already exists, reject it. Returns true if new node is
	// added to list
//	public boolean workingAdd(T x) {
//		int level = chooseLevel();
//		// Exit if already exist
//		if (contains(x))
//			return false;
//		Entry<T> ent = new Entry<T>(x, level);
//		for (int i = 0; i < level; i++) {
//			ent.next[i] = last[i].next[i];
//			ent.span[i] = last[i].span[i] == 1 ? 1 : last[i].span[i] - spans[i] + 1;
//			last[i].next[i] = ent;
//			last[i].span[i] = last[i].span[i] == 1 ? 1 : spans[i];
//		}
//		ent.next[0].prev = ent;
//		ent.prev = last[0];
//		size += 1;
//		return true;
//	}

	// Add x to list. If x already exists, reject it. Returns true if new node is
	// added to list
//	public boolean add(T x) {
//		int level = chooseLevel();
//
//		// Exit if already exist
//		if (contains(x))
//			return false;
//
//		Entry<T> ent = new Entry<T>(x, level);
//		for (int i = 0; i < level; i++) {
//			boolean isTailNode = last[i].next[i].equals(tail);
//			ent.next[i] = last[i].next[i];
//			ent.span[i] = isTailNode ? size - spans[0] + spans[i] : spans[i] + last[i].span[i] - spans[0];
//			last[i].next[i] = ent;
//			last[i].span[i] = spans[0] - spans[i];
//			// System.out.println("ent: " + ent.span[i]);
//		}
//		ent.next[0].prev = ent;
//		ent.prev = last[0];
//		size += 1;
//
//		for (int i = level; i < PossibleLevels; i++) {
//			if (i < maxLevel) {
//				last[i].span[i] += 1;
//			} else {
//				head.span[i] += 1;
//			}
//		}
//				return true;
//	}

	// Find smallest element that is greater or equal to x
	public T ceiling(T x) {
		if (contains(x))
			return x;
		return last[0].next[0].element;
	}

	// Does list contain x?
	public boolean contains(T x) {
		find(x);
		if (last[0].next[0].element == null)
			return false;

		return x.compareTo(last[0].next[0].element) == 0;
	}

	// Return first element of list
	public T first() {
		return head.next[0].element;
	}

	// Find largest element that is less than or equal to x
	public T floor(T x) {
		if (contains(x))
			return x;
		return last[0].element;
	}

	// Return element at index n of list. First element is at index 0.
	public T get(int n) {
		if (n < 0 || n >= size)
			throw new NoSuchElementException();
		return getLog(n);
	}

	// O(n) algorithm for get(n)
	public T getLinear(int n) {
		Entry<T> temp = head;
		for (int i = 0; i <= n; i++) {
			temp = temp.next[0];
		}
		return temp.element;
	}

	// Optional operation: Eligible for EC.
	// O(log n) expected time for get(n). Requires maintenance of spans, as
	// discussed in class.
	public T getLog(int n) {
		Entry<T> temp = head;
		int i = maxLevel - 1;
		n = n + 1;
		int distance = 0;

		while (i >= 0) {
			while (distance + temp.span[i] + 1 <= n) {
				distance += temp.span[i] + 1;
				if (distance == n) {
					temp = temp.next[i];
					return temp == null ? null : temp.element;
				}
				temp = temp.next[i];
			}
			// System.out.println("n: " + n + " distance: " + distance + " sz: " + size);
			i--;
		}
		return null;
	}

	// Is the list empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// Iterate through the elements of list in sorted order
	public Iterator<T> iterator() {
		return new SkipListIterator();
	}

	protected class SkipListIterator implements Iterator<T> {

		Entry<T> cursor;
		boolean ready;

		SkipListIterator() {
			cursor = head;
			ready = false;
		}

		public boolean hasNext() {
			return cursor.next[0].element != null;
		}

		public T next() {
			if (cursor.next == null)
				throw new NoSuchElementException();
			cursor = cursor.next[0];
			ready = true;
			return cursor.element;
		}

		public void remove() {
			if (!ready)
				throw new IllegalStateException();
			find(cursor.element);
			Entry<T> ent = last[0].next[0];
			int len = ent.next.length;
			for (int i = 0; i < len; i++) {
				last[i].next[i] = ent.next[i];
			}
			ent.next[0].prev = last[0];
			size -= 1;
			cursor = ent.prev;
			ready = false; // next() should be called atleast once inorder to call remove()
		}

	}

	// Return last element of list
	public T last() {
		return tail.prev.element;
	}

//	
//	 public boolean addEntry(T newElement,int newLevel) {
//	        Entry<T> newEntry = new Entry(newElement,newLevel);
//	        newEntry.index = last[0].index + 1; //new index is the index of the largest element less than it plus one
//
//	        //for each level from the top to the bottom of the new entry, set its corresponding pointers
//	        //at each level, we set the new entry's next value to the previous entry's next
//	        //then, we set the previous entry's next to the new entry
//	        int currentLevel = 0;
//	        //for each level of the entry
//	        for(;currentLevel < newLevel; currentLevel++) {
//	            //update the spans
//	            last[currentLevel].span[currentLevel]++;
//	            int tempSpan = last[currentLevel].span[currentLevel];
//	            last[currentLevel].span[currentLevel] = newEntry.index - last[currentLevel].index; //new span for last[i] is newIndex - oldIndex
//	            newEntry.span[currentLevel] = tempSpan - last[currentLevel].span[currentLevel]; //new span for newEntry is the oldSpan - span of last[i]
//
//	            //re-adjust the pointers
//	            newEntry.next[currentLevel] = last[currentLevel].next[currentLevel];
//	            last[currentLevel].next[currentLevel] = newEntry;
//	        }
//
//	        //update the spans for all levels higher than the new level of the entry
//	        for(;currentLevel < PossibleLevels;currentLevel++) {
//	            last[currentLevel].span[currentLevel]++;
//	        }
//	        //set the previous pointers to the new values at the bottom level
//	        newEntry.next[0].prev = newEntry;
//	        newEntry.prev = last[0];
//	        size++;
//	        return true;
//	    }
//	
//    
//    // Optional operation: Reorganize the elements of the list into a perfect skip list
// 	// Not a standard operation in skip lists. Eligible for EC.	
//    public void rebuild() {
//        SkipList newSkipList = new SkipList();
//        int maxLevel = 0;
//       
//        int size = this.size();
//        int size1=size;
//        //to find the new height, subtract 1 from the size, and then divide by 2 so that we can make two halves of the list
//        while (size != 0) {
//            size--;
//            size/=2;
//            maxLevel++; //new max height of the list
//        }
//        
//        newSkipList.maxLevel = maxLevel;
//        System.out.println(maxLevel);
//        rebuild_help(newSkipList, maxLevel, 0, size1-1); //recursive helper function to get the median of the list and add it to the new list recursively
//        
//    }
//    
//    private void rebuild_help(SkipList perfectSkipList,int currentLevel, int start, int end) {
//        if (currentLevel > 0) {
//            //Find median element and add it to the perfect skiplist with the correct height
//            int medianIndex = (start + end) / 2;
//            T median = this.get(medianIndex);
//            perfectSkipList.find(median);
//            perfectSkipList.addEntry(median, currentLevel); // add entry with height currentLevel
//            currentLevel--; //Go down a level
//
//            //make recursive calls to the lower levels and other entries of the skip list
//            rebuild_help(perfectSkipList,currentLevel,start,medianIndex-1);
//            rebuild_help(perfectSkipList,currentLevel,medianIndex+1,end);
//        }
//    }
	public boolean insertEntry(T newElement,int newLevel) {
        Entry<T> newEntry = new Entry(newElement,newLevel);
        newEntry.index = last[0].index + 1; //new index is the index of the largest element less than it plus one

        //for each level from the top to the bottom of the new entry, set its corresponding pointers
        //at each level, we set the new entry's next value to the previous entry's next
        //then, we set the previous entry's next to the new entry
        int currentLevel;
        //for each level of the entry
        for(currentLevel = 0;currentLevel < newLevel; currentLevel++) {
            //update the spans
            last[currentLevel].spans[currentLevel]++;
            int tempSpan = last[currentLevel].spans[currentLevel];
            last[currentLevel].spans[currentLevel] = newEntry.index - last[currentLevel].index; //new span for last[i] is newIndex - oldIndex
            newEntry.spans[currentLevel] = tempSpan - last[currentLevel].spans[currentLevel]; //new span for newEntry is the oldSpan - span of last[i]

            //re-adjust the pointers
            newEntry.next[currentLevel] = last[currentLevel].next[currentLevel];
            last[currentLevel].next[currentLevel] = newEntry;
        }
        System.out.println(currentLevel);

        //update the spans for all levels higher than the new level of the entry
        for(currentLevel = 0;currentLevel < PossibleLevels;currentLevel++) {
            last[currentLevel].spans[currentLevel]++;
        }
        //set the previous pointers to the new values at the bottom level
        newEntry.next[0].prev = newEntry;
        newEntry.prev = last[0];
        size++;
        return true;
    }

    //Function to add an entry of newElement to the skip list with a probabalistically determined height
    //Returns true if added, false otherwise
    public boolean add(T newElement) {
        //Reject duplicates to the list
        if (contains(newElement)) {
            return false;
        }
        int newLevel = chooseHeightOfNewEntry();
        System.out.println(newLevel);

        insertEntry(newElement,newLevel); //add an entry with newLevel as its height
        return true;
    }

	public void rebuild() {
        SkipList newSkipList = new SkipList();
        int newMaxLevel = calculateMaxLevel();
        newSkipList.maxLevel = newMaxLevel;
        rebuild_helper(newSkipList,newMaxLevel,0,size-1); //recursive helper function
        //skipListCopier(newSkipList);
    }

    //Recursive helper function for rebuild()
    //Runs in O(NlogN) time
    //Finds the median element of the skip list from startIndex...endIndex, and recursively builds a skip list
    //At each function call, correctly adds an entry with height currentLevel to the perfectSkipList based on the startIndex and endIndex of the current skip list elements
    private void rebuild_helper(SkipList perfectSkipList,int currentLevel, int startIndex, int endIndex) {
        if (currentLevel > 0) {
            //Find median element and add it to the perfect list with the correct height
            int medianIndex = (startIndex + endIndex) / 2;
            T medianElement = this.get(medianIndex);
            perfectSkipList.find(medianElement);
            perfectSkipList.insertEntry(medianElement,currentLevel); // add entry with height currentLevel
            currentLevel--; //Go down a level

            //make two recurswive calls to the lower levels and other entries of the skip list
            rebuild_helper(perfectSkipList,currentLevel,startIndex,medianIndex-1);
            rebuild_helper(perfectSkipList,currentLevel,medianIndex+1,endIndex);
        }
    }

    //Helper function for rebuild().
    //Calculates the max level of the perfect skip list based on its size
    private int calculateMaxLevel() {
        int currentMax = 0; //currently has height 1
        int size = this.size();
        //to find the new height, subtract 1 from the size (the peak), and then divide by 2 (the 2 halves)
        while (size != 0) {
            size--;
            size/=2;
            currentMax++; //increment our counter  height
        }
        return currentMax;
    }
    private int chooseHeightOfNewEntry() {
        //take advantage of hardware instructions to quickly choose a new level
        int newLevel = Integer.numberOfTrailingZeros(random.nextInt()) + 1;
        //make sure the maxLevel does not increase too quickly
        System.out.println("chooseHeightOfNewEntry");
        if (newLevel > maxLevel){
            maxLevel++;
            newLevel = maxLevel;
        }
        return newLevel;
    }

	// Remove x from list. Removed element is returned. Return null if x not in list
	public T remove(T x) {
		if (!contains(x))
			return null;
		Entry<T> ent = last[0].next[0];
		int len = ent.next.length;
		for (int i = 0; i < len; i++) {
			last[i].next[i] = ent.next[i];
			last[i].span[i] += ent.span[i];
		}
		ent.next[0].prev = last[0];
		int index = len;
		// Adjust remaining nodes from level to maxLevel
		while (index < maxLevel) {
			last[index].span[index] -= 1;
			index++;
		}
		size -= 1;
		return ent.element;
	}

	public void printList() {
		Entry<T> temp = head;
		while (!temp.equals(tail)) {
			System.out.println(" element " + temp.element + " Level Size " + temp.next.length);
			int n = temp.next.length;
			for (int i = 0; i < n; i++) {
				System.out.print("i " + i + " span " + temp.span[i] + " ");
			}
			System.out.println();
			temp = temp.next[0];
		}
	}

	// Return the number of elements in the list
	public int size() {
		return size;
	}

	public static void main(String[] args) throws NoSuchElementException {

		SkipList<Integer> sl = new SkipList<>();
		for (int i = 1; i <= 10; i++) {
			sl.add(Integer.valueOf(i));
		}

		//sl.printList();

	}
}
