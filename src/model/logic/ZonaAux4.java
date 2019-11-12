package model.logic;

import model.data_structures.ListaSencillamenteEncadenada;

public class ZonaAux4 extends Zona implements Comparable<ZonaAux4>
{

	public ZonaAux4(String pNombre, double pPerimetro, double pArea, int pId, ListaSencillamenteEncadenada<Punto> pCoord)
	{
		super(pNombre, pPerimetro, pArea, pId, pCoord);
	}
	
	@Override
	public int compareTo(ZonaAux4 o) 
	{
		return id - o.id;
	}

}
