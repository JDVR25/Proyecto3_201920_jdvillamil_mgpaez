package model.data_structures;


import java.util.Iterator;


public class ListaSencillamenteEncadenada<E> extends ListaEncadenadaAbstracta<E> implements Comparable<ListaSencillamenteEncadenada<E>>
{

	/**
	 * Constante de serializaci�n.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construye la lista vac�a.
	 * <b>post: </b> Se ha inicializado el primer nodo en null
	 */
	public ListaSencillamenteEncadenada() 
	{
		primerNodo = null;
		ultimoNodo = null;
	}

	/**
	 * Se construye una nueva lista cuyo primer nodo  guardar� al elemento que llega por par�metro. Actualiza el n�mero de elementos.
	 * @param nPrimero el elemento a guardar en el primer nodo
	 * @throws NullPointerException si el elemento recibido es nulo
	 */
	public ListaSencillamenteEncadenada(E nPrimero) throws NullPointerException
	{
		primerNodo = new Nodo<E>(nPrimero);
		ultimoNodo = primerNodo;
		cantidadElementos = 1;
	}



	/**
	 * Agrega un elemento que llega como parametro en el indice que llega como parametro. 
	 * @param index 
	 * @param elemento
	 */
	@Override
	public void add(int index, E elemento) 
	{
		Nodo<E> nuevo = new Nodo<E>(elemento);
		if(index < 0 || index >= size())
		{
			throw new IndexOutOfBoundsException();
		}
		else if(index == 0)
		{
			nuevo.cambiarSiguiente(primerNodo);
			primerNodo = nuevo;
			if(cantidadElementos == 0)
			{
				ultimoNodo = nuevo;
			}
			cantidadElementos++;
		}
		else if(index == cantidadElementos - 1)
		{
			ultimoNodo.cambiarSiguiente(nuevo);
			ultimoNodo = nuevo;
			cantidadElementos++;
		}
		else
		{
			int posicion = 1;
			Nodo<E> anterior = primerNodo;
			while(posicion < index)
			{
				anterior = anterior.darSiguiente();
				posicion++;
			}
			nuevo.cambiarSiguiente(anterior.darSiguiente());
			anterior.cambiarSiguiente(nuevo);
			cantidadElementos++;
		}
	}

	/**
	 * Remueve el objeto que el usuario especifica en el parametro de la lista sencillamente encadenada.
	 * @param objeto 
	 */
	@Override
	public boolean remove(Object objeto) 
	{
		boolean respuesta = false;

		E elemento = (E)objeto;
		if(primerNodo != null)
		{
			if(primerNodo.darElemento().equals(elemento))
			{
				if(cantidadElementos == 1)
				{
					ultimoNodo = null;
				}
				primerNodo = primerNodo.darSiguiente();
				respuesta = true;
				cantidadElementos--;
			}
			if(ultimoNodo.darElemento().equals(elemento))
			{
				ultimoNodo = darNodo(cantidadElementos - 2);
				ultimoNodo.cambiarSiguiente(null);
				cantidadElementos--;
			}
			else
			{
				Nodo<E> anterior = primerNodo;
				while(anterior != null && !respuesta && anterior.darSiguiente() != null)
				{
					
					if(anterior.darSiguiente().darElemento().equals(elemento))
					{
						anterior.cambiarSiguiente(anterior.darSiguiente().darSiguiente());
						respuesta = true;
						cantidadElementos--;
					}
					anterior = anterior.darSiguiente();
				}
			}
		}

		return respuesta;
	}

	/**
	 * Remueve el objeto que esta en la posicion que el usuario ingresa como parametro. 
	 * @param pos. 
	 */
	@Override
	public E remove(int pos) 
	{
		E borrado = null;
		if(pos < 0 || pos > size() - 1 || primerNodo == null)
		{
			throw new IndexOutOfBoundsException();
		}
		else if(pos == 0)
		{
			if(cantidadElementos == 1)
			{
				ultimoNodo = null;
			}
			borrado = primerNodo.darElemento();
			primerNodo = primerNodo.darSiguiente();
			cantidadElementos--;
		}
		else if(pos == cantidadElementos - 1)
		{
			borrado = ultimoNodo.darElemento();
			ultimoNodo = darNodo(cantidadElementos - 2);
			cantidadElementos--;
		}
		else
		{
			int posicion = 1;
			Nodo<E> anterior = primerNodo;
			while(posicion < pos)
			{
				anterior = anterior.darSiguiente();
				posicion++;
			}
			borrado = anterior.darSiguiente().darElemento();
			anterior.cambiarSiguiente(anterior.darSiguiente().darSiguiente());
			cantidadElementos--;
		}

		return borrado;
	}

	/**
	 * Agrega un elemento que llega como parametro a la ultima posicion de la lista
	 * @param elemento 
	 */
	@Override
	public boolean addLast(E elemento) 
	{
		boolean agregado = false;

		if(primerNodo == null)
		{
			primerNodo = new Nodo<E>(elemento);
			ultimoNodo = primerNodo;
			cantidadElementos = 1;
			agregado = true;
		}
		else
		{
			Nodo<E> nuevo = new Nodo<E>(elemento);
			ultimoNodo.cambiarSiguiente(nuevo);
			ultimoNodo = nuevo;
			cantidadElementos++;
			agregado = true;
		}

		return agregado;
	}

	@Override
	public int indexOf(E o) 
	{
		int posicion = -1;
		Nodo<E> actual = primerNodo;
		int posActual = 0;
		boolean listo = false;
		while(actual != null && !listo)
		{
			if(actual.darElemento().equals((o)))
			{
				posicion = posActual;
				listo = true;
			}
			else
			{
				posActual ++;
				actual = actual.darSiguiente();
			}
		}

		return posicion;
	}

	/**
	 * Agrega un elemento que llega como parametro a la primera posicion de la lista
	 * @param elemento 
	 */
	@Override
	public boolean addFirst(E elemento) 
	{
		boolean agregado = false;

		if(primerNodo == null)
		{
			primerNodo = new Nodo<E>(elemento);
			ultimoNodo = primerNodo;
			cantidadElementos = 1;
			agregado = true;
		}
		else
		{
			Nodo<E> nuevoPrimero= new Nodo<E>(elemento);
			nuevoPrimero.cambiarSiguiente(primerNodo);
			primerNodo = nuevoPrimero; 
			cantidadElementos++; 
			agregado = true;
		}
		return agregado;
	}

	/**
	 * Remueve el primer elemento de la lista
	 */
	@Override
	public boolean removeFirst() 
	{
		boolean removed = false;
		if (primerNodo != null)
		{
			if(cantidadElementos == 1)
			{
				ultimoNodo = null;
			}
			primerNodo = primerNodo.darSiguiente();
			removed = true;
			cantidadElementos--;
		}
		return removed; 
	}

	/**
	 * Remueve el ultimo elemento de la lista
	 */
	@Override
	public boolean removeLast() 
	{
		boolean removed;
		if (primerNodo == null)
		{
			removed = false; 
		}
		else if(primerNodo.darSiguiente() == null)
		{
			ultimoNodo = null;
			primerNodo = null; 
			removed = true; 
			cantidadElementos--;
		}
		else 
		{
			ultimoNodo = darNodo(cantidadElementos - 2);
			ultimoNodo.cambiarSiguiente(null);
			removed = true; 
			cantidadElementos--;
		}
		return removed;
	}

	@Override
	public int compareTo(ListaSencillamenteEncadenada<E> o)
	{
		return size() - o.size();
	}
	
	//Codigo basado en lo desarrollado en el ejercicio de nivel 9 de APO 2 (Honores) 201910
}
