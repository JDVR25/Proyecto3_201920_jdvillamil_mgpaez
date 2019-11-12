package model.logic;

import model.data_structures.ListaSencillamenteEncadenada;

public class ZonaAux1 extends Zona implements Comparable<ZonaAux1>
{

	public ZonaAux1(String pNombre, double pPerimetro, double pArea, int pId, ListaSencillamenteEncadenada<Punto> pCoord)
	{
		super(pNombre, pPerimetro, pArea, pId, pCoord);
	}

	@Override
	public int compareTo(ZonaAux1 o) 
	{
		return nombre.compareTo(o.nombre);
	}

}
