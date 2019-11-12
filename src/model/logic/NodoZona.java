package model.logic;

public class NodoZona
{
	private String zona;
	
	private Punto coordenada;

	public NodoZona(String pZona, double pLongitud, double pLatitud)
	{
		zona = pZona;
		
		coordenada = new Punto(pLongitud, pLatitud);
	}
	
	public String darZona()
	{
		return zona;
	}
	
	public Punto darCoordenada()
	{
		return coordenada;
	}
	
	public double darLatitud()
	{
		return coordenada.getLatitud();
	}
	
	public double darLongitud()
	{
		return coordenada.getLongitud();
	}
}
