package model.data_structures;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Clase que contiene lo com�n entre las listas encadenadas.
 * @param <E> Elemento que se almacenar� en la lista, el elemento debe ser �nicamente identificado.
 */
public abstract class ListaEncadenadaAbstracta<E> implements IEstructura<E>, Serializable
{

	/**
	 * Constante de serializaci�n.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Atributo que indica la cantidad de elementos que han sido almacenados en la lista.
	 */
	protected int cantidadElementos;

	/**
	 * Primer nodo de la lista.
	 */
	protected Nodo<E> primerNodo;
	
	protected Nodo<E> ultimoNodo;

	@SuppressWarnings("unchecked")
	public Object[] toArray() 
	{
		E[] arreglo = (E[]) new Object[size()];
		Nodo<E> actual = primerNodo;
		int pos = 0;
		while(actual != null)
		{
			arreglo[pos] = actual.darElemento();
			actual = actual.darSiguiente();
			pos ++;
		}

		return arreglo;
	}
	
	@Override
	public int size() 
	{
		return cantidadElementos;
	}

	public E set(int index, E element) throws IndexOutOfBoundsException 
	{
		Nodo<E> nodo = primerNodo;
		E retornar = null;

		int i = 0;
		if(index >= size() || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			while(i != index)
			{
				nodo = nodo.darSiguiente();
				i++;
			}
			retornar = nodo.darElemento();
			nodo.cambiarElemento(element);
		}

		return retornar;
	}

	@Override
	public Iterator<E> iterator() 
	{
		return new IteradorSencillo<E>(primerNodo);
	}

	@Override
	public boolean isEmpty() 
	{
		return cantidadElementos == 0? true: false;
	}

	public E get(int index) throws IndexOutOfBoundsException
	{
		Nodo<E> nodo = primerNodo;
		E retornar = null;

		int i = 0;
		if(index >= size() || index < 0)
		{
			throw new IndexOutOfBoundsException();
		}
		else
		{
			while(i != index)
			{
				nodo = nodo.darSiguiente();
				i++;
			}
			retornar = nodo.darElemento();
		}

		return retornar;
	}

	/**
	 * Devuelve el nodo de la posici�n dada
	 * @param pos la posici�n  buscada
	 * @return el nodo en la posici�n dada 
	 * @throws IndexOutOfBoundsException si index < 0 o index >= size()
	 */
	public Nodo<E> darNodo(int index)
	{
		if(index < 0 || index > cantidadElementos)
		{
			throw new IndexOutOfBoundsException("Se est� pidiendo el indice: " + index + " y el tama�o de la lista es de " + cantidadElementos);
		}

		Nodo<E> actual = primerNodo;
		int posActual = 0;
		while(actual != null && posActual < index)
		{
			actual = actual.darSiguiente();
			posActual ++;
		}

		return actual;
	}

	public boolean contains(Object o) 
	{
		boolean contiene = false;
		int i = 0;
		Nodo<E> nodo = primerNodo;
		if(primerNodo != null)
		{

			while(i < size() && !contiene && nodo != null)
			{
				if(nodo.darElemento().equals(o))
				{
					contiene = true;
				}
				nodo = nodo.darSiguiente();

				i++;
			}
		}
		return contiene;
	}	

	@Override
	public void clear() 
	{
		primerNodo = null;
		cantidadElementos = 0;
	}
	
	
	//Codigo basado en lo desarrollado en el ejercicio de nivel 9 de APO 2 (Honores) 201910
}
