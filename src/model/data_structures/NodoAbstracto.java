package model.data_structures;

import java.io.Serializable;
import java.util.Collection;

/**
 * Clase que unifica las propiedades comunes de todos los nodos
 * @param <K> tipo del identificador del elemento que se almacenará en el nodo. El identificador debe poder ser comparable
 * @param <T> Tipo del elemento que almacenará en el nodo
 */
public abstract class NodoAbstracto<K extends Comparable<K>, T> implements Serializable
{

	/**
	 * Constante de serialización.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Identificador del nodo
	 */
	//TODO 3.1.1 Declare el atributo identificador
	protected K identificador;

	/**
	 * Elemento que almacena el nodo
	 */
	//TODO 3.1.2 Declare el atrivuto elemento
	protected T elemento;

	/**
	 * Crea un nuevo nodo a partir del elemento que llega por parámetro
	 * @param identificador Identificador del elemento que se almacenará en el nodo. identificador != null
	 * @param elemento el elemento que almacenará el nodo. elemento != null
	 */
	public NodoAbstracto( K identificador, T elemento)
	{
		//TODO 3.1.3 inicialice el identificador y el elemento a partir del elemento que llega como parámetro   	
		this.identificador = identificador;
		this.elemento = elemento;
	}

	/**
	 * Devuelve el identificador del nodo
	 * @return identificador
	 */
	public K darIdentificador() 
	{
		//TODO 3.1.4 complete según la documentación
		return identificador;
	}

	/**
	 * Devuelve el elemento almacenado en el nodo
	 * @return elemento
	 */
	public T darElemento() 
	{
		//TODO 3.1.5 complete según la documentación
		return elemento;
	}

	/**
	 * Indica recursivamente si el árbol que inicia en este nodo tiene el identificador
	 * @param id el identificador buscado. id != null
	 * @return true si el árbol que inicia en el este nodo tiene el identificador o false en caso contrario
	 */
	public abstract boolean contieneIdentificador(K id);

	/**
	 * Indica recursivamente si el árbol que inicia en este nodo contiene el elemento
	 * @param elem el elem buscado. id != null
	 * @return true si el árbol que inicia en el este tiene el elemento o false en caso contrario
	 */
	public abstract boolean contieneElemento(T elem);

	/**
	 * Acumula recursivamente los nodos en la colección que llega por parámetro. Los nodos se acumulan en inorden.
	 * @param nodos la colección donde se acumulan los nodos. nodos != null
	 */
	public abstract void darNodos(Collection<NodoAbstracto<K, T>> nodos);

	/**
	 * Busca el elemento en el árbol que inicia en el nodo y lo devuelve
	 * @param id el identificador del elemento buscado. id != null
	 * @return El elemento que tiene como id el que llega como parámetro o null en caso contrario
	 */
	public abstract T buscar(K id);

	/**
	 * Acumula recursivamente los identificadores en la colección que llega por parámetro. Los identificadores se agregan en inorden.
	 * @param nodos la colección donde se acumulan los identificadores. nodos != null
	 */
	public abstract void darIdentificadores(Collection<K> ids);

	/**
	 * Calcula recursivamente el peso del árbol que inicia en el nodo.
	 * @return el peso del árbol que inicia en el nodo.
	 */
	public abstract int darPeso();

	/**
	 * Acumula recursivamente en preorden los elementos en la colección que llega por parámetro
	 * @param elems la colección donde se acumulan los elementos. elems != null
	 */
	public abstract void darPreorden(Collection<T> elems);

	/**
	 * Agrega el elemento que llega por parámetro al árbol.
	 * @param elem el elemento que se desea agregar. elem != null
	 * @param ids Los identificadores necesarios para agregar el elemento. id != null
	 */
	@SuppressWarnings("unchecked")
	public abstract void agregar(T elem, K... ids);

	/**
	 * Acumula recursivamente en posorden los elementos en la colección que llega por parámetro.
	 * @param elems la colección donde se acumulan los elementos. elems != null
	 */
	public abstract void darPosorden(Collection<T> elems);

	/**
	 * Construye el camino para llegar al nodo con el identificador que llega por parámetro. Los elementos los acumula en preorden en la colección.
	 * @param id el identificador del nodo al que se quiere llegar. id != null
	 * @param elems la colección donde se acumula el camino de elementos para llegar al nodo
	 */
	public abstract void darCamino(K id, Collection<T> elems);

	/**
	 * Devuelve el nodo que tiene el elemento con el id que llega como parámetro.
	 * @param id el identificador del elemento del que se quiere conocer el nodo.
	 * @return El nodo que tiene el elemento con el identificador que llega como parámetro.
	 */
	public abstract NodoAbstracto<K,T> darNodo(K id);

	
	//Basado en el ejercicio de nivel de apo2
}
