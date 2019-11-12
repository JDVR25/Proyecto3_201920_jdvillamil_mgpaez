package model.data_structures;

public interface IEstructura<E> extends Iterable<E>
{
	public int size();

	public E set(int index, E element) throws IndexOutOfBoundsException;

	public boolean isEmpty();

	public E get(int index) throws IndexOutOfBoundsException;

	public boolean contains(Object o);

	public void clear();

	public boolean addLast(E elemento);

	public void add(int index, E elemento);

	public boolean remove(Object objeto);

	public E remove(int pos);

	public Object[] toArray();
	
	public int indexOf(E element);
	
	public boolean addFirst(E elemento);
		
	public boolean removeFirst();
	
	public boolean removeLast();
}
