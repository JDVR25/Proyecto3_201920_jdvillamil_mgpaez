package model.logic;

public class NodoZona
{
	private String zona;
	
	private Coordenadas coordenada;

	public NodoZona(String pZona, double pLongitud, double pLatitud)
	{
		zona = pZona;
		
		coordenada = new Coordenadas(pLongitud, pLatitud);
	}
	
	public String darZona()
	{
		return zona;
	}
	
	public Coordenadas darCoordenada()
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
