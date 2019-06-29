package bxr180008;

import java.util.*;

//If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {
// Add fields of MDS here
HashMap<Long, Money> idPriceMap;
HashMap<Long, HashSet<Long>> idDescMap;
HashMap<Long, HashSet<Long>> descIdMap;
TreeSet<Long> ids;

// Constructors
public MDS() {
 idPriceMap = new HashMap<>();
 idDescMap = new HashMap<>();
 descIdMap = new HashMap<>();
 ids = new TreeSet<>();
}

/*
* Public methods of MDS. Do not change their signatures.
* __________________________________________________________________ a.
* Insert(id,price,list): insert a new item whose description is given in the
* list. If an entry with the same id already exists, then its description and
* price are replaced by the new values, unless list is null or empty, in which
* case, just the price is updated. Returns 1 if the item is new, and 0
* otherwise.
*/
public int insert(long id, Money price, java.util.List<Long> list) {
 // change list to set
 // insert id if not present in treeset
 // insert/update id - price
 // insert / update id - description (need old and new listIds)
 // iterate list -> remove and add!
	Money m = idPriceMap.put(id, price);
	HashSet<Long> newDesc = new HashSet<>();
	HashSet<Long> temp;
	if(!list.isEmpty())
	{
		for(long listId : list)
 	{
 		newDesc.add(listId);
 		HashSet<Long> hSet = descIdMap.get(listId);
 		if(hSet == null)  
 		{
 			temp = new HashSet<>();
 			temp.add(id);
 			descIdMap.put(listId, temp);
 		}
 		else
 		{
 			hSet.add(id);
 		}
 	}
 	if(m != null)
 	{
 		HashSet<Long> oldDesc = idDescMap.get(id);
 		if(!oldDesc.isEmpty())
 			oldDesc.removeAll(newDesc);
 		for(long k : oldDesc)
 		{
 			descIdMap.get(k).remove(id);
 		}
 	}
 	idDescMap.put(id, newDesc);
	}
	if(m == null)
	{
		ids.add(id);
		return 1;
	}
	else
	{
		return 0;
	}
}

// b. Find(id): return price of item with given id (or 0, if not found).
public Money find(long id) {
 // get from id - price
	Money m = idPriceMap.get(id);
	if(m != null)
	{
		return m;
	}
 return new Money();
}

/*
* c. Delete(id): delete item from storage. Returns the sum of the long ints
* that are in the description of the item deleted, or 0, if such an id did not
* exist.
*/
public long delete(long id) {
 // delete from treeset
 // return if it does not exist

 // delete from id-price
 // get all descriptions before deleting
 // delete from id-decription
 // iterate list that we got and delete from descr-id
	if(!idDescMap.containsKey(id)) return 0;
	long sum = 0;
	HashSet<Long> desc = idDescMap.get(id);
	for(long k :  desc)
	{
		sum += k;
		descIdMap.get(k).remove(id);
	}
	idDescMap.remove(id);
	ids.remove(id);
	idPriceMap.remove(id);
 return sum;
}

/*
* d. FindMinPrice(n): given a long int, find items whose description contains
* that number (exact match with one of the long ints in the item's
* description), and return lowest price of those items. Return 0 if there is no
* such item.
*/
public Money findMinPrice(long n) {
 // get list from descr-id map
 // iterate and find min price O(n)
	HashSet<Long> hSet = descIdMap.get(n);
	if(hSet == null || hSet.isEmpty()) return new Money();
	Iterator<Long> iter = hSet.iterator();
	Money minPrice = idPriceMap.get(iter.next());
	while(iter.hasNext())
	{
		Money m = idPriceMap.get(iter.next());
		if(minPrice.compareTo(m) == 1)
		{
			minPrice = m;
		}
	}
	return minPrice;
}

/*
* e. FindMaxPrice(n): given a long int, find items whose description contains
* that number, and return highest price of those items. Return 0 if there is no
* such item.
*/
public Money findMaxPrice(long n) {
 // get list from descr-id map
 // iterate and find max price O(n)
	HashSet<Long> hSet = descIdMap.get(n);
	if(hSet == null || hSet.isEmpty()) return new Money();
	Iterator<Long> iter = hSet.iterator();
	Money maxPrice = idPriceMap.get(iter.next());
	while(iter.hasNext())
	{
		Money m = idPriceMap.get(iter.next());
		if(maxPrice.compareTo(m) == -1)
		{
			maxPrice = m;
		}
	}
	return maxPrice ;
}

/*
* f. FindPriceRange(n,low,high): given a long int n, find the number of items
* whose description contains n, and in addition, their prices fall within the
* given range, [low, high].
*/
public int findPriceRange(long n, Money low, Money high) {
 // get list from descr-id map
 // iterate and find min, max price in the same iteration O(n)
	HashSet<Long> hSet = descIdMap.get(n);
	int sum = 0;
	for(long k : hSet)
	{
		Money m = idPriceMap.get(k);
		if(m.compareTo(low) >= 0 && m.compareTo(high) <= 0)
		{
			sum += 1;
		}
	}
 return sum;
}

/*
* g. PriceHike(l,h,r): increase the price of every product, whose id is in the
* range [l,h] by r%. Discard any fractional pennies in the new prices of items.
* Returns the sum of the net increases of the prices.
*/
public Money priceHike(long l, long h, double rate) {
 // use subset of treeset to get ids
 // iterate and update id-price map
 // sum the difference ∑(new - old)
 TreeSet<Long> hSet = (TreeSet<Long>) ids.subSet(l, true, h, true);
 long oldPriceInCents, newPriceInCents, diff;
 long net = 0;
 for(long k : hSet)
 {
   Money m = idPriceMap.get(k);
   oldPriceInCents = m.d * 100 + m.c;
   diff = (long)(oldPriceInCents * (0.01 * rate)); 								//diff for the difference between newPriceInCents and oldPriceInCents
   newPriceInCents = oldPriceInCents + diff;
   Money priceObj = new Money(newPriceInCents / 100, (int)(newPriceInCents % 100));
   net += diff;
   idPriceMap.put(k, priceObj);
 }

 long netIncreaseInDollars = net / 100;
 return new Money(netIncreaseInDollars, 0);
}

/*
* h. RemoveNames(id, list): Remove elements of list from the description of id.
* It is possible that some of the items in the list are not in the id's
* description. Return the sum of the numbers that are actually deleted from the
* description of id. Return 0 if there is no such id.
*/
public long removeNames(long id, java.util.List<Long> list) {
 // remove from id-descr map
 // descr-id remove elem from list
	HashSet<Long> desc = idDescMap.get(id);
	if(desc == null || list.isEmpty()) return 0;
	long sum = 0;
	HashSet<Long> hSet = new HashSet<>(list); 
	hSet.retainAll(desc);    // hSet contains common elements of list and listIds of the given id
	for(long k : hSet)
	{
		sum += k;
		desc.remove(k);             //removing names(descriptions) from idDescMap
		descIdMap.get(k).remove(id);//removing id from the descriptions that are removed
	}
 return sum;
}

// Do not modify the Money class in a way that breaks LP3Driver.java
public static class Money implements Comparable<Money> {
 long d;
 int c;

 public Money() {
   d = 0;
   c = 0;
 }

 public Money(long d, int c) {
   this.d = d;
   this.c = c;
 }

 public Money(String s) {
   String[] part = s.split("\\.");
   int len = part.length;
   if (len < 1) {
     d = 0;
     c = 0;
   } else if (part.length == 1) {
     d = Long.parseLong(s);
     c = 0;
   } else {
     d = Long.parseLong(part[0]);
     c = Integer.parseInt(part[1]);
   }
 }

 public long dollars() {
   return d;
 }

 public int cents() {
   return c;
 }

 public int compareTo(Money other) { // Complete this, if needed
   if (!(other instanceof Money))
     return -1;

   if (this.amountInCents() > other.amountInCents())
     return 1;
   else if(this.amountInCents() < other.amountInCents())
     return -1;
   else
   	return 0;
 }

 private long amountInCents() {
   return d * 100 + c;
 }

 public String toString() {
   return d + String.format(".%02d", c);
 }
}

}