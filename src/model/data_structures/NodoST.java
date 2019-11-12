package model.data_structures;

public class NodoST<K, V> 
{

	private K llave;
	
	private ListaSencillamenteEncadenada<V> elementos;
	
	private NodoST<K, V> siguiente;
	
	public NodoST(K key, V elemento)
	{
		elementos = new ListaSencillamenteEncadenada<V>(elemento);
		
		llave = key;
	}

	public K darLlave()
	{
		return llave;
	}
	
	public void cambiarSiguienteST(NodoST<K, V> siguiente)
	{
		this.siguiente = siguiente;
	}

	public void cambiarElemento(V val)
	{
		elementos = new ListaSencillamenteEncadenada<V>(val);
	}

	public NodoST<K, V> darSiguiente()
	{
		return siguiente;
	}

	public V darElemento()
	{
		return elementos.get(0);
	}

	public void cambiarSiguiente(NodoST<K, V> nuevoSiq)
	{
		siguiente = nuevoSiq;
	}
	
	public ListaSencillamenteEncadenada<V> darElementos()
	{
		return elementos;
	}
}
