package model.data_structures;

//Codigo basado en "https://github.com/kevin-wayne/algs4/blob/master/src/main/java/edu/princeton/cs/algs4/SequentialSearchST.java"
public class TablaSimbolos<K extends Comparable<K>, V>
{
	private int n;
	private NodoST<K, V> primero; 

	public TablaSimbolos()
	{
	}

	public int size()
	{
		return n;
	}

	public boolean isEmpty()
	{
		return size() == 0;
	}

	public boolean contains(K key)
	{
		boolean respuesta = false;
		if(key != null)
		{
			respuesta = get(key) != null;
		}
		return respuesta;
	}

	public V get(K key)
	{
		V buscado = null;
		if (key != null)
		{
			NodoST<K, V> temp = primero;
			boolean encontrado = false;
			while(temp != null && !encontrado)
			{
				if (key.equals(temp.darLlave()))
				{
					if(temp.darElementos().size() == 1)
					{
						buscado = temp.darElemento();
					}
					encontrado = true;
				}
				temp = (NodoST<K, V>) temp.darSiguiente();
			}
		}

		return buscado;
	}

	public void put(K key, V val)
	{
		if (key != null)
		{ 
			if(val != null)
			{
				NodoST<K, V> temp = primero;
				boolean encontrado = false;
				while(temp != null && !encontrado)
				{
					if (key.equals(temp.darLlave()) )
					{
						if(temp.darElementos().size() == 1)
						{
							temp.cambiarElemento(val);
						}
						encontrado = true;
					}
					temp = (NodoST<K, V>) temp.darSiguiente();
				}
				if(!encontrado)
				{
					NodoST<K,V> nuevo = new NodoST<K,V>(key, val);
					nuevo.cambiarSiguienteST(primero);
					primero = nuevo;
					n++;
				}
			}
		}
	}

	public V delete(K key) 
	{
		V borrado = null;
		if(key != null)
		{
			if(key.equals(primero.darLlave()))
			{
				if(primero.darElementos().size() == 1)
				{
					borrado = primero.darElemento();
					primero = (NodoST<K, V>) primero.darSiguiente();
					n--;
				}
			}
			else
			{
				int posicion = 1;
				NodoST<K, V> anterior = primero;
				while(posicion < n && borrado == null)
				{
					if(key.equals(anterior.darSiguiente().darLlave()) && anterior.darSiguiente().darElementos().size() == 1)
					{
						borrado = anterior.darSiguiente().darElemento();
						anterior.cambiarSiguiente(anterior.darSiguiente().darSiguiente());
						n--;
					}
					else
					{
						anterior = (NodoST<K, V>) anterior.darSiguiente();
					}
					posicion++;
				}

			}
		}
		return borrado;
	}

	public Iterable<K> keys()  {
		ListaSencillamenteEncadenada<K> lista = new ListaSencillamenteEncadenada<K>();
		NodoST<K, V> temp = primero;
		while(temp != null)
		{
			lista.addLast(temp.darLlave());
			temp = (NodoST<K, V>) temp.darSiguiente();
		}
		return lista;
	}

}
