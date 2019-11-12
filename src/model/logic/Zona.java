package model.logic;

import model.data_structures.ListaSencillamenteEncadenada;

public abstract class Zona
{
	protected String nombre;

	protected double perimetro;

	protected double area;

	protected int id;

	protected ListaSencillamenteEncadenada<Punto> coordenadas;

	public Zona(String pNombre, double pPerimetro, double pArea, int pId, ListaSencillamenteEncadenada<Punto>  pCoord)
	{
		nombre = pNombre;

		perimetro = pPerimetro;

		area = pArea;

		id = pId;

		coordenadas = pCoord;
	}

	public ListaSencillamenteEncadenada<Punto>  getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(ListaSencillamenteEncadenada<Punto>  coordenadas) {
		this.coordenadas = coordenadas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPerimetro() {
		return perimetro;
	}

	public void setPerimetro(double perimetro) {
		this.perimetro = perimetro;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



}
