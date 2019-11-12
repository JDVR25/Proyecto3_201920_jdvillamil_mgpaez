package model.logic;

public class Punto
{
	private double longitud;
	
	private double latitud;
	
	public Punto(double pLong, double pLatitud)
	{
		longitud = pLong;
		
		latitud = pLatitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}
	
	public String toString()
	{
		return latitud + "-" + longitud;
	}
}
