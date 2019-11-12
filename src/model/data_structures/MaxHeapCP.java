package model.data_structures;


//TODO revisar
public class MaxHeapCP<T extends Comparable<T>>
{
	private T[] elementos;

	private int tamano;

	public MaxHeapCP()
	{
		tamano = 0;
		elementos = (T[]) new Object[200022];
	}
	
	public int size()
	{
		return tamano;
	}

	public int darNumElementos()
	{
		return tamano;
	}

	public boolean esVacia()
	{
		return tamano == 0? true: false;
	}

	public T sacarMax()
	{
		T maximo = null;
		if(!esVacia())
		{
			maximo = elementos[0];
			elementos[0] = elementos[tamano - 1];
			elementos[tamano -1] = null;
			sink(0);
			tamano--;
		}

		return maximo;
	}

	public T darMax()
	{
		return elementos[0];
	}

	public void agregar(T elemento)
	{
		if(tamano > elementos.length - 10)
		{
			expand();
		}
		elementos[tamano] = elemento;
		swim(tamano);
		tamano++;
	}
	
	public void expand()
	{
		T[] temp = (T[]) new Object[elementos.length*2];
		for(int i = 0; i < tamano; i++)
		{
			temp[i] = elementos[i];
		}
		elementos = temp;
	}

	private void sink(int pos)
	{
		int heap = pos+1;
		boolean listo = false;
		while(!listo && (heap*2)-1 < tamano)
		{
			int hijoMayor = (heap*2)-1;
			if(elementos[(heap*2)] != null && elementos[(heap*2)].compareTo(elementos[hijoMayor])>0)
			{
				hijoMayor = heap*2;
			}
			if(elementos[hijoMayor].compareTo(elementos[heap-1]) > 0)
			{
				T temp = elementos[heap-1];
				elementos[heap-1] = elementos[hijoMayor];
				elementos[hijoMayor] = temp;
				heap = hijoMayor + 1;
			}
			else
			{
				listo = true;
			}
		}
	}

	private void swim(int pos)
	{
		int heap = pos+1;
		boolean listo = false;
		while(!listo && (int)(heap/2)-1 >= 0)
		{
			if(elementos[heap-1].compareTo(elementos[(heap/2)-1]) > 0)
			{
				T temp = elementos[heap-1];
				elementos[heap-1] = elementos[(heap/2)-1];
				elementos[(heap/2)-1] = temp;
				heap = (heap/2);
			}
			else
			{
				listo = true;
			}
		}
	}
}
