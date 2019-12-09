package model.logic;

public class Coordenadas
{
	private double longitud;
	
	private double latitud;
	
	public Coordenadas(double pLong, double pLatitud)
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
	public boolean coincide(double pLatitud, double pLongitud)
	{
		boolean respuesta = false;
		double latitudTrun = latitud;
		latitudTrun=latitudTrun*100;
		latitudTrun = (int)latitudTrun;
		latitudTrun = latitudTrun/100;

		double longitudTrun = longitud;
		longitudTrun=longitudTrun*100;
		longitudTrun = (int)longitudTrun;
		longitudTrun = longitudTrun/100;
		
		double otraLatitud = pLatitud;
		otraLatitud=otraLatitud*100;
		otraLatitud = (int)otraLatitud;
		otraLatitud = otraLatitud/100;

		double otraLongitud = pLongitud;
		otraLongitud = otraLongitud*100;
		otraLongitud = (int)otraLongitud;
		otraLongitud = otraLongitud/100;

		if(latitudTrun == otraLatitud&& longitudTrun == otraLongitud)
		{
			respuesta = true;
		}
		
		return respuesta;
	}
}
