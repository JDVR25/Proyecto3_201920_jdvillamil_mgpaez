package model.data_structures;

import java.util.Iterator;

public interface IArbolBalanceado <K extends Comparable<K>, V>
{
	public int size();
	
	public boolean isEmpty();
	
	public V get(K key);
	
	public int getHeight(K key);
	
	public boolean contains(K key);
	
	public void put(K key, V val);
	
	public int height();
	
	public K min();
	
	public K max();
	
	public Iterator <K> keys();
	
	public Iterator<V> valuesInRange(K init, K end);
	
	public Iterator<K> keysInRange(K init, K end);
}
