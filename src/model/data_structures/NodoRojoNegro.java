package model.data_structures;

//basado en https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/RedBlackBST.java

public class NodoRojoNegro <K extends Comparable<K>, V>
{
	public K key;
	public V val;
	public NodoRojoNegro<K, V> left;
	public NodoRojoNegro<K, V> right;
	public boolean color;
	public int size;

	public NodoRojoNegro(K key, V val, boolean color, int size)
	{
		this.key = key;
		this.val = val;
		this.color = color;
		this.size = size;

	}
}