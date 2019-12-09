package model.logic;

public class NodoMallaVial implements Comparable<NodoMallaVial>
{
	private int id;
	
	private Coordenadas coordenada;

	public NodoMallaVial(int pId, double pLongitud, double pLatitud)
	{
		id = pId;
		
		coordenada = new Coordenadas(pLongitud, pLatitud);
	}
	
	public int darId()
	{
		return id;
	}
	
	public Coordenadas darCoordenada()
	{
		return coordenada;
	}
	
	//TODO pendiente
	@Override
	public int compareTo(NodoMallaVial o)
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String toString()
	{
		return id + " " + coordenada;
	}
}
